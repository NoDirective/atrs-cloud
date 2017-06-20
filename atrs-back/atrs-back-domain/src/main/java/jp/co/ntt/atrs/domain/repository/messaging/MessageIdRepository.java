/*
 * Copyright(c) 2017 NTT Corporation.
 */
package jp.co.ntt.atrs.domain.repository.messaging;

import java.util.List;

/**
 * メッセージIDテーブルにアクセスするリポジトリインターフェース。
 * @author NTT 電電太郎
 */
public interface MessageIdRepository {
    /**
     * 全てのメッセージIDを取得する。
     * @return メッセージIDリスト
     */
    List<String> findAll();

    /**
     * メッセージIDをメッセージIDテーブルに登録する。
     * @param messageId メッセージID
     * @return 登録件数
     */
    int register(String messageId);

}
