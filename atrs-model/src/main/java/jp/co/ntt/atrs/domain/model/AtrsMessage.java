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
