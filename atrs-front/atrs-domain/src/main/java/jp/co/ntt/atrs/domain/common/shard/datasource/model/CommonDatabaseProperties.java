/*
 * Copyright(c) 2017 NTT Corporation.
 */
package jp.co.ntt.atrs.domain.common.shard.datasource.model;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 共通のデータベース情報を保持するクラス。
 * @author NTT 電電太郎
 */
@ConfigurationProperties(prefix = "database.common")
public class CommonDatabaseProperties {

    /**
     * データソース情報を格納する {@link Map}
     */
    private Map<String, String> dataSource = new HashMap<>();

    /**
     * データソース情報を格納する {@link Map}を取得する。
     *
     * @return dataSource
     */
    public Map<String, String> getDataSource() {
        return dataSource;
    }

    /**
     * データソース情報を格納する {@link Map}を設定する。
     *
     * @param dataSource セットする dataSource
     */
    public void setDataSource(Map<String, String> dataSource) {
        this.dataSource = dataSource;
    }

}
