/*
 * Copyright(c) 2017 NTT Corporation.
 */
/**
 *
 */
package jp.co.ntt.atrs.domain.common.shard.model;

import java.io.Serializable;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/**
 * DynamoDBのシャーディングアカウントモデル。
 * @author NTT 電電太郎
 */
@DynamoDBTable(tableName = "ShardAccount")
public class ShardingAccount implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /** ユーザID */
    @DynamoDBHashKey(attributeName = "user_id")
    private String userId;

    /** データソースのキー */
    @DynamoDBAttribute(attributeName = "data_source_key")
    private String dataSourceKey;

    /**
     * ユーザIDを取得する。
     *
     * @return userId
     */

    public String getUserId() {
        return userId;
    }

    /**
     * ユーザIDを設定する。
     *
     * @param userId セットする userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * データソースのキーを取得する。
     *
     * @return dataSourceKey
     */
    public String getDataSourceKey() {
        return dataSourceKey;
    }

    /**
     * データソースのキーを設定する。
     *
     * @param dataSourceKey セットする dataSourceKey
     */
    public void setDataSourceKey(String dataSourceKey) {
        this.dataSourceKey = dataSourceKey;
    }

    /*
     * (非 Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result
                + (dataSourceKey != null ? dataSourceKey.hashCode() : 0);
        return result;
    }

    /*
     * (非 Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof ShardingAccount)) {
            return false;
        }

        ShardingAccount sa = (ShardingAccount) obj;
        if (userId != null) {
            if (!userId.equals(sa.userId)) {
                return false;
            } else {
                if (dataSourceKey == null) {
                    return sa.dataSourceKey == null;
                } else {
                    return dataSourceKey.equals(sa.dataSourceKey);
                }
            }
        } else {
            if (sa.userId != null) {
                return false;
            } else {
                if (dataSourceKey == null) {
                    return sa.dataSourceKey == null;
                } else {
                    return dataSourceKey.equals(sa.dataSourceKey);
                }
            }
        }
    }

}
