/*
 * Copyright(c) 2017 NTT Corporation.
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
import org.springframework.cloud.aws.autoconfigure.actuate.CloudWatchMetricProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClient;
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

    /**
     * autoScalingGroupName名。<br>
     * デフォルトは、<code>spring.application.name</code>を使用する。変更する場合は、<code>custom.metric.auto-scaling-group-name<code>で設定可能。
     */
    @Value("${spring.application.name:autoScalingGroupName}")
    String autoScalingGroupName;

    @Inject
    CloudWatchMetricProperties cloudWatchMetricProperties;

    AmazonCloudWatchClient amazonCloudWatchClient;

    String instanceId;


    @Scheduled(fixedRate = 5000)
    public void sendCloudWatch() {
        MemoryMXBean mBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapUsage = mBean.getHeapMemoryUsage();
        Dimension InstanceIdDimension = new Dimension().withName("instanceId")
                .withValue(instanceId);

        Dimension AutoScalingGroupNameDimension = new Dimension().withName(
                "AutoScalingGroupName").withValue(autoScalingGroupName);
        PutMetricDataRequest request = new PutMetricDataRequest()
                .withNamespace(cloudWatchMetricProperties.getNamespace())
                .withMetricData(
                // Used
                        new MetricDatum().withDimensions(InstanceIdDimension,
                                AutoScalingGroupNameDimension).withMetricName(
                                "HeapMemory.Used").withUnit(
                                StandardUnit.Bytes.toString()).withValue(
                                (double) heapUsage.getUsed()),
                        // Max
                        new MetricDatum().withDimensions(InstanceIdDimension,
                                AutoScalingGroupNameDimension).withMetricName(
                                "HeapMemory.Max").withUnit(
                                StandardUnit.Bytes.toString()).withValue(
                                (double) heapUsage.getMax()),
                        // Committed
                        new MetricDatum().withDimensions(InstanceIdDimension,
                                AutoScalingGroupNameDimension).withMetricName(
                                "HeapMemory.Committed").withUnit(
                                StandardUnit.Bytes.toString()).withValue(
                                (double) heapUsage.getCommitted()),
                        // Utilization
                        new MetricDatum()
                                .withDimensions(InstanceIdDimension,
                                        AutoScalingGroupNameDimension)
                                .withMetricName("HeapMemory.Utilization")
                                .withUnit(StandardUnit.Percent.toString())
                                .withValue(
                                        100 * ((double) heapUsage.getUsed() / (double) heapUsage
                                                .getMax()))

                );

        amazonCloudWatchClient.putMetricData(request);
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
        this.amazonCloudWatchClient = new AmazonCloudWatchClient(awsCredentialsProvider);
        if (!StringUtils.isEmpty(region)) {
            this.amazonCloudWatchClient.setRegion(Region.getRegion(Regions
                    .fromName(region)));
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
