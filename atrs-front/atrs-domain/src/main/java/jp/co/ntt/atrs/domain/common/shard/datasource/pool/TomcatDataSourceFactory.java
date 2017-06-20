/*
 * Copyright(c) 2017 NTT Corporation.
 */
package jp.co.ntt.atrs.domain.common.shard.datasource.pool;

import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.terasoluna.gfw.common.exception.SystemException;

import jp.co.ntt.atrs.domain.common.logging.LogMessages;
import jp.co.ntt.atrs.domain.common.shard.datasource.DataSourceFactory;

/**
 * Tomcatデータソースのファクトリ。
 * @author NTT 電電花子
 */
public class TomcatDataSourceFactory implements DataSourceFactory {

    /**
     * Tomcatのデータソースファクトリ。
     */
    private org.apache.tomcat.jdbc.pool.DataSourceFactory factory = new org.apache.tomcat.jdbc.pool.DataSourceFactory();

    /*
     * (非 Javadoc)
     * @see jp.co.ntt.atrs.domain.common.shard.datasource.DataSourceFactory#create(java.util.Map, java.util.Map)
     */
    @Override
    public DataSource create(Map<String, String> dataSourceProperties,
            Map<String, String> commonDataSourceProperties) {
        DataSource ret = null;
        Properties properties = new Properties();
        if (!commonDataSourceProperties.isEmpty()) {
            // 共通データソース設定を反映
            properties.putAll(commonDataSourceProperties);
        }
        // 個別データソース設定を反映(共通データソース設定を上書き)
        properties.putAll(dataSourceProperties);
        try {
            ret = factory.createDataSource(properties);
        } catch (Exception e) {
            throw new SystemException(LogMessages.E_AR_A0_L9008.getCode(), LogMessages.E_AR_A0_L9008
                    .getMessage(), e);
        }
        return ret;
    }

}
