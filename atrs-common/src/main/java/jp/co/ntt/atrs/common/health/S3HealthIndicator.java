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
package jp.co.ntt.atrs.common.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3URI;

/**
 * S3ヘルスチェック用Indicator。
 * @author NTT 電電太郎
 */
@ConditionalOnProperty(value = "management.health.s3.enabled", matchIfMissing = true)
@ConfigurationProperties(prefix = "management.health.s3")
public class S3HealthIndicator extends AbstractHealthIndicator {

    @Autowired(required = false)
    AmazonS3 amazonS3;

    private String uri;

    /*
     * (非 Javadoc)
     * @see
     * org.springframework.boot.actuate.health.AbstractHealthIndicator#doHealthCheck(org.springframework.boot.actuate.health.
     * Health.Builder)
     */
    @Override
    protected void doHealthCheck(Builder builder) throws Exception {
        if (this.amazonS3 == null || this.uri == null) {
            builder.up().withDetail("S3", "unknown");
            return;
        }
        try {
            String bucket = new AmazonS3URI(uri).getBucket();
            builder.up().withDetail("uri", uri).withDetail("location", amazonS3
                    .getBucketLocation(bucket));
        } catch (Exception e) {
            builder.down(e);
        }

    }

    /**
     * uriを取得する。
     * @return
     */
    public String getUri() {
        return uri;
    }

    /**
     * uriを設定する。
     * @param uri
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

}
