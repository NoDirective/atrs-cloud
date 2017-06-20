/*
 * Copyright(c) 2017 NTT Corporation.
 */
package jp.co.ntt.atrs.domain.common.messaging;

import java.util.UUID;

/**
 * メッセージを一意に特定するIDを生成するクラス。
 * @author NTT 電電太郎
 */
public class MessageIdGenerator {

    /**
     * メッセージIDを生成して返却する。
     * @return メッセージID
     */
    public String generate() {

        // プロトタイプの為、UUIDを使用する。重複する確率は無視できるレベル
        return UUID.randomUUID().toString();
    }
}
