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
package jp.co.ntt.atrs.domain.service.b2;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jp.co.ntt.atrs.domain.common.mail.AtrsMail;
import jp.co.ntt.atrs.domain.common.mail.AtrsMailSender;
import jp.co.ntt.atrs.domain.model.Reservation;

/**
 * チケット予約共通サービス実装クラス。
 * @author NTT 電電太郎
 */
@Service
public class ReservationSharedServiceImpl implements ReservationSharedService {

    /**
     * ロガー。
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(
            ReservationSharedServiceImpl.class);

    /**
     * メール送信部品
     */
    @Inject
    AtrsMailSender mailSender;

    /**
     * 送信元メールアドレス
     */
    @Value("${reservation.notification.mail.from}")
    String fromMailAddress;

    /**
     * テンプレートファイル名
     */
    @Value("${reservation.notification.mail.templateFile}")
    String templateName;

    /**
     * ダミー処理を行う時間。ミリ秒。
     */
    @Value("${reservation.inspection.processing.time}")
    Integer processingTime;

    /**
     * チケット予約完了をクライアントへ通知する。
     * @param reservation 予約情報
     */
    @Override
    public void notify(Reservation reservation) {

        AtrsMail mail = new AtrsMail();
        mail.setFrom(fromMailAddress);
        mail.setFromName("ATRS予約係");
        mail.setTo(reservation.getRepMail());
        mail.setSubject("予約完了メール");
        mail.setTemplateFileName(templateName);
        mail.setModel(reservation);

        mailSender.sendMail(mail);

        LOGGER.info("通知メール送信完了 [予約番号=" + reservation.getReserveNo() + "]");
    }

    /**
     * チケット予約審査を行う。
     * @param reservation 予約情報
     */
    @Override
    public void inspect(Reservation reservation) {

        // 負荷試験を想定し、ダミー処理で素数計算を行う。
        long start = System.currentTimeMillis();
        LOGGER.debug("=== 予約審査(ダミー)処理開始 ===");

        int i = 1;
        while (true) {
            for (int j = 2; j < i; j++) {
                if (i % j == 0) {
                    break;
                }
            }

            // 設定時間を超えたらダミー処理を終了する
            long now = System.currentTimeMillis();
            if (now - start > processingTime) {
                break;
            }
            i++;
        }
        LOGGER.debug("=== 予約審査(ダミー)処理終了 ===");
    }

}
