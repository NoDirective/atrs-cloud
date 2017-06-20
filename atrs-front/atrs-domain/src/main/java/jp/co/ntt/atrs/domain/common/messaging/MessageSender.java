/*
 * Copyright(c) 2017 NTT Corporation.
 */
package jp.co.ntt.atrs.domain.common.messaging;

import javax.inject.Inject;

import org.springframework.jms.core.JmsMessagingTemplate;

import jp.co.ntt.atrs.domain.model.AtrsMessage;

/**
 * メッセージを送信するクラス。
 * @author NTT 電電太郎
 */
public class MessageSender {

    /**
     * メッセージID採番用ユーティリティ
     */
    @Inject
    MessageIdGenerator messageIdGenerator;

    /**
     * JMS送信テンプレート
     */
    @Inject
    JmsMessagingTemplate jmsMessagingTemplate;

    /**
     * キューにメッセージを送信する。
     * @param queueName 送信先キュー名
     * @param message メッセージオブジェクト
     */
    public void send(String queueName, AtrsMessage message) {

        // メッセージIDを採番
        message.setMessageId(messageIdGenerator.generate());

        // キューにメッセージを送信
        jmsMessagingTemplate.convertAndSend(queueName, message);
    }
}
