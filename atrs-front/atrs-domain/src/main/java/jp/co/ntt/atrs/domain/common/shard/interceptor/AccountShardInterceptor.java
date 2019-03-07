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
package jp.co.ntt.atrs.domain.common.shard.interceptor;

import java.util.Optional;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.InitializingBean;

import jp.co.ntt.atrs.domain.common.shard.datasource.RoutingDataSourceLookupKeyHolder;
import jp.co.ntt.atrs.domain.common.shard.helper.ShardAccountHelper;
import jp.co.ntt.atrs.domain.common.shard.model.ShardingAccount;
import jp.co.ntt.atrs.domain.common.shard.repository.AccountShardKeyRepository;

/**
 * シャーディングのインタセプタ。
 * @author NTT 電電太郎
 */
public class AccountShardInterceptor implements MethodInterceptor,
                                     InitializingBean {

    /**
     * シャードアカウントのリポジトリー。
     */
    private AccountShardKeyRepository accountShardKeyRepository;

    /**
     * シャーディングアカウントのヘルパー。
     */
    private ShardAccountHelper shardAccountHelper;

    /**
     * データソースのキー(シャードキー)を保持するホルダ。
     */
    private RoutingDataSourceLookupKeyHolder dataSourceLookupKeyHolder;

    /**
     * コンストラクタ
     * @param accountShardKeyCacheableRepository
     * @param shardAccountHelper
     * @param dataSourceLookupKeyHolder
     */
    public AccountShardInterceptor(
            AccountShardKeyRepository accountShardKeyRepository,
            ShardAccountHelper shardAccountHelper,
            RoutingDataSourceLookupKeyHolder dataSourceLookupKeyHolder) {
        this.accountShardKeyRepository = accountShardKeyRepository;
        this.shardAccountHelper = shardAccountHelper;
        this.dataSourceLookupKeyHolder = dataSourceLookupKeyHolder;
    }

    /*
     * (非 Javadoc)
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {

    }

    /*
     * (非 Javadoc)
     * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        // ネスト処理に対応する為、一つ前のキーを保持
        String beforeKey = dataSourceLookupKeyHolder.get();

        String dataSourceKey = null;
        // シャードテーブルのキーを取得
        String account = shardAccountHelper.getAccountValue(invocation);
        if (null != account) {
            // リポジトリ問い合わせ
            Optional<ShardingAccount> shardingAccount = accountShardKeyRepository
                    .findById(account);
            if (shardingAccount != null) {
                // データソースキー取得
                dataSourceKey = shardingAccount.get().getDataSourceKey();
            }
        }
        dataSourceLookupKeyHolder.set(dataSourceKey);

        Object ret = null;
        try {
            ret = invocation.proceed();
        } finally {
            if (null != beforeKey) {
                // 一つ前のキーを設定
                dataSourceLookupKeyHolder.set(beforeKey);
            } else {
                // ホルダのクリア
                dataSourceLookupKeyHolder.clear();
            }
        }
        return ret;
    }
}
