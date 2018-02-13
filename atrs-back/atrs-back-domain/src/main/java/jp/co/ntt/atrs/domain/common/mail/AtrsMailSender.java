/*
 * Copyright 2014-2017 NTT Corporation.
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
package jp.co.ntt.atrs.domain.common.mail;

import javax.inject.Inject;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * メール送信を行うユーティリティクラス。
 * @author NTT 電電太郎
 */
public class AtrsMailSender {

    /**
     * メール送信部品
     */
    @Inject
    JavaMailSender mailSender;

    /**
     * テンプレートファイルコンフィグ
     */
    @Inject
    Configuration freemarkerConfiguration;

    /**
     * エンコード
     */
    @Value("${atrs.mail.encoding:UTF-8}")
    String encoding;

    /**
     * 引数で受け取ったオブジェクトを元にメールを送信する。<br>
     * 以下は必須項目
     * <ul>
     * <li>送信元メールアドレス</li>
     * <li>宛先メールアドレス</li>
     * <li>件名</li>
     * <li>テンプレートファイル名</li>
     * <li>埋め込みオブジェクト</li>
     * </ul>
     * @param mail メールオブジェクト
     */
    public void sendMail(AtrsMail mail) {

        mailSender.send(new MimeMessagePreparator() {

            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {

                MimeMessageHelper helper;

                // 添付ファイル有りの場合はmultipartでHelperを生成する
                if (mail.getAttachment() != null) {
                    helper = new MimeMessageHelper(mimeMessage, true, encoding);
                    helper.addAttachment(mail.getAttachmentName(), mail
                            .getAttachment());
                } else {
                    helper = new MimeMessageHelper(mimeMessage, encoding);
                }

                // 必須項目の設定
                helper.setFrom(mail.getFrom(), mail.getFromName());
                helper.setTo(mail.getTo());
                helper.setSubject(mail.getSubject());

                // テンプレート解決
                Template template = freemarkerConfiguration.getTemplate(
                        mail.getTemplateFileName());
                String text = FreeMarkerTemplateUtils.processTemplateIntoString(
                        template, mail.getModel());

                // 本文設定
                helper.setText(text, mail.isHtmlMail());
            }
        });
    }
}
