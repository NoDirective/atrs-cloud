/*
 * Copyright(c) 2017 NTT Corporation.
 */
package jp.co.ntt.atrs.domain.common.shard.datasource;

import java.util.Map;

import javax.sql.DataSource;

/**
 * データソースファクトリインタフェース。
 * @author NTT 電電花子
 */
public interface DataSourceFactory {

    /**
     * 引数の個別設定情報を優先して共通設定情報とマージし、{@link DataSource}を作成する。
     * @param dataSourceProperties 個別設定情報
     * @param commonDataSourceProperties 共通設定情報
     * @return {@link DataSource}
     */
    DataSource create(Map<String, String> dataSourceProperties,
            Map<String, String> commonDataSourceProperties);
}
