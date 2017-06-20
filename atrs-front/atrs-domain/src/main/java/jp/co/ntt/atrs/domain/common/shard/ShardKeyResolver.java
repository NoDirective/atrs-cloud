/*
 * Copyright(c) 2017 NTT Corporation.
 */
package jp.co.ntt.atrs.domain.common.shard;

/**
 * シャードキーのリゾルバー。
 * @author NTT 電電花子
 */
public interface ShardKeyResolver {

    /**
     * スキーマキー名。
     */
    String SCHEMA_KEY_NAME = "schema";

    /**
     * シャードキーを解決する。
     * @param shardKey
     * @return
     */
    String resolveShardKey(String shardKey);
}
