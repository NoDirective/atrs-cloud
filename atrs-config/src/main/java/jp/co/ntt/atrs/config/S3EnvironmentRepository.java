/*
 * Copyright(c) 2017 NTT Corporation.
 */
package jp.co.ntt.atrs.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.config.server.environment.AbstractScmEnvironmentRepository;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.cloud.config.server.environment.SearchPathLocator;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.Assert;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3URI;
import com.amazonaws.services.s3.transfer.MultipleFileDownload;
import com.amazonaws.services.s3.transfer.TransferManager;

@ConfigurationProperties("spring.cloud.config.server.s3")
public class S3EnvironmentRepository extends AbstractScmEnvironmentRepository
                                                                             implements
                                                                             EnvironmentRepository,
                                                                             SearchPathLocator,
                                                                             InitializingBean {

    private static Log logger = LogFactory
            .getLog(S3EnvironmentRepository.class);

    public S3EnvironmentRepository(ConfigurableEnvironment environment) {
        super(environment);
    }

    @Override
    public synchronized Locations getLocations(String application,
            String profile, String label) {

        AmazonS3Client amazonS3Client = null;
        TransferManager tm = null;
        try {
            String bucketName = new AmazonS3URI(getUri()).getBucket();
            amazonS3Client = new AmazonS3Client();
            tm = new TransferManager(amazonS3Client);
            logger.info("local temp dir:" + getBasedir().getAbsolutePath());
            MultipleFileDownload download = tm.downloadDirectory(bucketName,
                    null, getBasedir());
            download.waitForCompletion();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Throwable t) {
            throw new IllegalStateException("Cannot download s3", t);
        } finally {
            if (tm != null) {
                tm.shutdownNow();
            }
            if (amazonS3Client != null) {
                amazonS3Client.shutdown();
            }

        }

        return new Locations(application, profile, label, null, getSearchLocations(
                getWorkingDirectory(), application, profile, label));
    }

    @Override
    public void afterPropertiesSet() {
        Assert.state(getUri() != null,
                "You need to configure a uri for the s3 bucket (e.g. 's3://bucket/')");
        // S3 URIを検証するためにインスタンス化
        new AmazonS3URI(getUri());
    }


}
