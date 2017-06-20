/*
 * Copyright(c) 2017 NTT Corporation.
 */
package jp.co.ntt.atrs.domain.common.mail;

import javax.inject.Inject;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * メールを送信するクラス。
 * @author NTT 電電太郎
 */
public class AtrsMailSender {

    /**
     * メール送信ユーティリティ
     */
    @Inject
    JavaMailSender mailSender;

    /**
     * メールを送信する。
     * @param from 送信元メールアドレス
     * @param to 宛先メールアドレス
     * @param subject 件名
     * @param text 本文
     */
    public void sendMail(String from, String to, String subject, String text) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);
        this.mailSender.send(simpleMailMessage);

    }
}
