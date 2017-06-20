/*
 * Copyright(c) 2017 NTT Corporation.
 */
package jp.co.ntt.atrs.domain.common.shard.repository;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import jp.co.ntt.atrs.domain.common.shard.model.ShardingAccount;

/**
 * 　シャードアカウントのリポジトリー。
 * @author NTT 電電太郎
 */
@CacheConfig(cacheNames = "shardids")
@EnableScan
public interface AccountShardKeyRepository
                                          extends
                                          CrudRepository<ShardingAccount, String> {

    /*
     * (非 Javadoc)
     * @see org.springframework.data.repository.CrudRepository#findOne(java.io.Serializable)
     */
    @Override
    @Cacheable(key = "'shardid/' + #a0")
    ShardingAccount findOne(String id);

}
