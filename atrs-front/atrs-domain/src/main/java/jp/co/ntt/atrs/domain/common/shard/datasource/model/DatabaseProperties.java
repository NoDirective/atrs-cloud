/*
 * Copyright(c) 2017 NTT Corporation.
 */
package jp.co.ntt.atrs.domain.common.shard.datasource.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * データソース情報のリストを保持するクラス。
 * @author NTT 電電太郎
 */
@ConfigurationProperties(prefix = "database")
public class DatabaseProperties {

    /**
     * データソース情報を格納する {@link List}
     */
    private List<Map<String, String>> dataSources = new ArrayList<>();

    /**
     * データソース情報を格納する {@link List}を取得する。
     *
     * @return dataSources
     */
    public List<Map<String, String>> getDataSources() {
        return dataSources;
    }

    /**
     * データソース情報を格納する {@link List}を設定する。
     *
     * @param dataSources セットする dataSources
     */
    public void setDataSources(List<Map<String, String>> dataSources) {
        this.dataSources = dataSources;
    }

}
