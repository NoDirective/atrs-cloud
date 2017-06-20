/*
 * Copyright(c) 2017 NTT Corporation.
 */
package jp.co.ntt.atrs.app.common.health;

import javax.inject.Inject;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;

/**
 * DynamoDBヘルスチェック用Indicator。
 * @author NTT 電電太郎
 */
@Component
@ConditionalOnProperty(value = "management.health.dynamodb.enabled", matchIfMissing = true)
public class DynamodbHealthIndicator extends AbstractHealthIndicator {

    private AmazonDynamoDB amazonDynamoDB;

    @Inject
    public DynamodbHealthIndicator(AmazonDynamoDB amazonDynamoDB) {
        this.amazonDynamoDB = amazonDynamoDB;
    }

    protected void doHealthCheck(Builder builder) throws Exception {

        if (this.amazonDynamoDB == null) {
            builder.up().withDetail("amazonDynamoDB", "unknown");
            return;
        }
        try {
            ListTablesResult listTablesResult = amazonDynamoDB.listTables();
            builder.up().withDetail("amazonDynamoDB",
                    listTablesResult.getTableNames());
        } catch (Exception e) {
            builder.down(e);
        }
    }

}
