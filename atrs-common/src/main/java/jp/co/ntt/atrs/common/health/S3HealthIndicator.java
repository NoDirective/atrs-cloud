/*
 * Copyright(c) 2017 NTT Corporation.
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
     * @see org.springframework.boot.actuate.health.AbstractHealthIndicator#doHealthCheck(org.springframework.boot.actuate.health.Health.Builder)
     */
    @Override
    protected void doHealthCheck(Builder builder) throws Exception {
        if (this.amazonS3 == null || this.uri == null) {
            builder.up().withDetail("S3", "unknown");
            return;
        }
        try {
            String bucket = new AmazonS3URI(uri).getBucket();
            builder.up().withDetail("uri", uri).withDetail("location",
                    amazonS3.getBucketLocation(bucket));
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
