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
            builder.up().withDetail("amazonDynamoDB", listTablesResult
                    .getTableNames());
        } catch (Exception e) {
            builder.down(e);
        }
    }

}
