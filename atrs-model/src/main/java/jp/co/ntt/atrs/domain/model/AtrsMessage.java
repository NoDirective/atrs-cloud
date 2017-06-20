/*
 * Copyright(c) 2017 NTT Corporation.
 */
package jp.co.ntt.atrs.domain.model;

import java.io.Serializable;

/**
 * メッセージの抽象クラス。
 * @author NTT 電電太郎
 */
public abstract class AtrsMessage implements Serializable {

    /**
     * シリアルバージョンUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * メッセージID.
     */
    private String messageId;

    /**
     * メッセージIDを取得する。
     * @return メッセージID
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * メッセージIDを設定する。
     * @param messageId メッセージID
     */
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}
