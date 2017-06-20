/*
 * Copyright(c) 2017 NTT Corporation.
 */
package jp.co.ntt.atrs.domain.common.messaging;

import javax.inject.Inject;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ntt.atrs.domain.model.AtrsMessage;
import jp.co.ntt.atrs.domain.repository.messaging.MessageIdRepository;

/**
 * メッセージの2重受信検出を行うクラス。
 * @author NTT 電電太郎
 */
public class DuplicateMessageChecker {

    /**
     * メッセージIDテーブルリポジトリ
     */
    @Inject
    MessageIdRepository repository;

    /**
     * メッセージ2重受信のチェックを行う。
     * @return {@code true}:2重受信<br>
     *         {@code false}:正常に受信
     */
    @Transactional
    public boolean isDuplicated(AtrsMessage message) {

        String messageId = message.getMessageId();

        // メッセージIDの重複をチェック
        // 一意性制約違反が発生した場合は2重受信
        try {
            repository.register(messageId);
        } catch (DuplicateKeyException e) {
            return true;
        }

        return false;
    }

}
