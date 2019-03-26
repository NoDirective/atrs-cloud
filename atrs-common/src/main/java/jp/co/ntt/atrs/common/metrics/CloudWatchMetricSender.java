/*
 * Copyright 2014-2018 NTT Corporation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package jp.co.ntt.atrs.common.metrics;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

import javax.inject.Inject;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;
import com.amazonaws.services.cloudwatch.model.Dimension;
import com.amazonaws.services.cloudwatch.model.MetricDatum;
import com.amazonaws.services.cloudwatch.model.PutMetricDataRequest;
import com.amazonaws.services.cloudwatch.model.StandardUnit;
import com.amazonaws.util.EC2MetadataUtils;
import com.amazonaws.util.EC2MetadataUtils.InstanceInfo;

/**
 * Cloud Watch MetricsのSender。 Cloud Watch Metricsから取得できないHeap情報を対象
 * @author NTT 電電太郎
 */

@ConfigurationProperties(prefix = "custom.metric")
public class CloudWatchMetricSender implements InitializingBean {

    @Inject
    AWSCredentialsProvider awsCredentialsProvider;

    @Value("${cloud.aws.cloudwatch.region}")
    String region;

    @Value("${cloud.aws.cloudwatch.namespace:}")
    String namespace;

    /**
     * autoScalingGroupName名。<br>
     * デフォルトは、<code>spring.application.name</code>を使用する。変更する場合は、<code>custom.metric.auto-scaling-group-name<code>で設定可能。
     */
    @Value("${spring.application.name:autoScalingGroupName}")
    String autoScalingGroupName;

    AmazonCloudWatch amazonCloudWatch;

    String instanceId;

    @Scheduled(fixedRate = 5000)
    public void sendCloudWatch() {
        MemoryMXBean mBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapUsage = mBean.getHeapMemoryUsage();
        Dimension InstanceIdDimension = new Dimension().withName("instanceId")
                .withValue(instanceId);

        Dimension AutoScalingGroupNameDimension = new Dimension().withName(
                "AutoScalingGroupName").withValue(autoScalingGroupName);
        PutMetricDataRequest request = new PutMetricDataRequest().withNamespace(
                namespace).withMetricData(
                        // Used
                        new MetricDatum().withDimensions(InstanceIdDimension,
                                AutoScalingGroupNameDimension).withMetricName(
                                        "HeapMemory.Used").withUnit(
                                                StandardUnit.Bytes.toString())
                                .withValue((double) heapUsage.getUsed()),
                        // Max
                        new MetricDatum().withDimensions(InstanceIdDimension,
                                AutoScalingGroupNameDimension).withMetricName(
                                        "HeapMemory.Max").withUnit(
                                                StandardUnit.Bytes.toString())
                                .withValue((double) heapUsage.getMax()),
                        // Committed
                        new MetricDatum().withDimensions(InstanceIdDimension,
                                AutoScalingGroupNameDimension).withMetricName(
                                        "HeapMemory.Committed").withUnit(
                                                StandardUnit.Bytes.toString())
                                .withValue((double) heapUsage.getCommitted()),
                        // Utilization
                        new MetricDatum().withDimensions(InstanceIdDimension,
                                AutoScalingGroupNameDimension).withMetricName(
                                        "HeapMemory.Utilization").withUnit(
                                                StandardUnit.Percent.toString())
                                .withValue(100 * ((double) heapUsage.getUsed()
                                        / (double) heapUsage.getMax()))

        );

        amazonCloudWatch.putMetricData(request);
    }

    private void resolveInstanceIdWithLocalHostAddress() {
        try {
            this.instanceId = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e1) {
            this.instanceId = "localhost";
        }
    }

    public String getAutoScalingGroupName() {
        return autoScalingGroupName;
    }

    public void setAutoScalingGroupName(String autoScalingGroupName) {
        this.autoScalingGroupName = autoScalingGroupName;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (StringUtils.isEmpty(region)) {
            this.amazonCloudWatch = AmazonCloudWatchClientBuilder
                    .defaultClient();
        } else {
            this.amazonCloudWatch = AmazonCloudWatchClientBuilder.standard()
                    .withRegion(region).build();
        }

        try {

            InstanceInfo instanceInfo = EC2MetadataUtils.getInstanceInfo();
            if (Objects.isNull(instanceInfo)) {
                resolveInstanceIdWithLocalHostAddress();
            } else {
                this.instanceId = instanceInfo.getInstanceId();
            }

        } catch (AmazonClientException e) {
            resolveInstanceIdWithLocalHostAddress();
        }

    }

}
