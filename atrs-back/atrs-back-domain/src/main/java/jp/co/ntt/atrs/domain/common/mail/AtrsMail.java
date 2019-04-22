/*
 * Copyright(c) 2017 NTT Corporation.
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
 */
package jp.co.ntt.atrs.domain.common.mail;

import org.springframework.core.io.InputStreamSource;

/**
 * メールオブジェクト
 * @author NTT 電電太郎
 */
public class AtrsMail {

    /**
     * 送信元メールアドレス
     */
    private String from;

    /**
     * 送信者名
     */
    private String fromName;

    /**
     * 宛先メールアドレス
     */
    private String to;

    /**
     * 件名
     */
    private String subject;

    /**
     * テンプレートファイル名
     */
    private String templateFileName;

    /**
     * 埋め込みオブジェクト
     */
    private Object model;

    /**
     * HTMLメールフラグ
     */
    private boolean htmlMail = false;

    /**
     * 添付ファイル
     */
    private InputStreamSource attachment;

    /**
     * 添付ファイル名
     */
    private String attachmentName;

    /**
     * 送信元メールアドレスを取得する。
     * @return 送信元メールアドレス
     */
    public String getFrom() {
        return from;
    }

    /**
     * 送信元メールアドレスを設定する。
     * @param from 送信元メールアドレス
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * 送信者名を取得する。
     * @return 送信者名
     */
    public String getFromName() {
        return fromName;
    }

    /**
     * 送信者名を設定する。
     * @param fromName 送信者名
     */
    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    /**
     * 宛先メールアドレスを取得する。
     * @return 宛先メールアドレス
     */
    public String getTo() {
        return to;
    }

    /**
     * 宛先メールアドレスを設定する。
     * @param to 宛先メールアドレス
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * 件名を取得する。
     * @return 件名
     */
    public String getSubject() {
        return subject;
    }

    /**
     * 件名を設定する。
     * @param subject 件名
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * テンプレートファイル名を取得する。
     * @return テンプレートファイル名
     */
    public String getTemplateFileName() {
        return templateFileName;
    }

    /**
     * テンプレートファイル名を設定する。
     * @param templateFileName テンプレートファイル名
     */
    public void setTemplateFileName(String templateFileName) {
        this.templateFileName = templateFileName;
    }

    /**
     * テンプレートへの埋め込みオブジェクトを取得する。
     * @return 埋め込みオブジェクト
     */
    public Object getModel() {
        return model;
    }

    /**
     * テンプレートへの埋め込みオブジェクトを設定する。
     * @param model 埋め込みオブジェクト
     */
    public void setModel(Object model) {
        this.model = model;
    }

    /**
     * HTMLメールフラグを取得する。
     * @return HTMLメールフラグ
     */
    public boolean isHtmlMail() {
        return htmlMail;
    }

    /**
     * HTMLメールフラグを設定する。
     * @param htmlMail HTMLメールフラグ
     */
    public void setHtmlMail(boolean htmlMail) {
        this.htmlMail = htmlMail;
    }

    /**
     * 添付ファイルを取得する。
     * @return 添付ファイル
     */
    public InputStreamSource getAttachment() {
        return attachment;
    }

    /**
     * 添付ファイルを設定する。
     * @param attachment 添付ファイル
     */
    public void setAttachment(InputStreamSource attachment) {
        this.attachment = attachment;
    }

    /**
     * 添付ファイル名を取得する。
     * @return 添付ファイル名
     */
    public String getAttachmentName() {
        return attachmentName;
    }

    /**
     * 添付ファイル名を設定する。
     * @param attachmentName 添付ファイル名
     */
    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }
}
