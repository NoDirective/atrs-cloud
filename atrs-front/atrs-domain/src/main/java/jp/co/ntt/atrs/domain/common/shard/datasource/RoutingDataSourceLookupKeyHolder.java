/*
 * Copyright(c) 2017 NTT Corporation.
 */
package jp.co.ntt.atrs.domain.common.shard.datasource;

/**
 * データソースのキー(シャードキー)を保持するホルダ。
 * @author NTT 電電太郎
 */
public class RoutingDataSourceLookupKeyHolder {

    /**
     * データソースのキーを保持する{@link ThreadLocal}。
     */
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    /**
     * データソースのキーを設定する。
     * @param dataSourceKey データソースのキー
     */
    public void set(String dataSourceKey) {
        contextHolder.set(dataSourceKey);
    }

    /**
     * データソースのキーを取得する。
     * @return データソースのキー
     */
    public String get() {
        return contextHolder.get();
    }

    /**
     * 保持したデータソースのキーを削除する。
     */
    public void clear() {
        contextHolder.remove();
    }
}
