/*
 * Copyright(c) 2017 NTT Corporation.
 */
package jp.co.ntt.atrs.app.listener;

import javax.inject.Inject;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ntt.atrs.domain.common.messaging.DuplicateMessageChecker;
import jp.co.ntt.atrs.domain.model.Reservation;
import jp.co.ntt.atrs.domain.model.ReservationNotificationMessage;
import jp.co.ntt.atrs.domain.service.b2.ReservationInspectionService;
import jp.co.ntt.atrs.domain.service.b2.ReservationNotificationService;

/**
 * チケット予約情報のメッセージを受信するクラス。
 * @author NTT 電電太郎
 */
@Component
public class TicketReserveMessageListener {

    /**
     * 予約審査サービス
     */
    @Inject
    ReservationInspectionService reservationInspectionService;

    /**
     * 予約完了通知サービス
     */
    @Inject
    ReservationNotificationService reservationNotificationService;

    /**
     * 2重受信チェックユーティリティ
     */
    @Inject
    DuplicateMessageChecker duplicateMessageChecker;

    /**
     * キューからメッセージを受信し、フロントとの非同期処理を行う
     * @param message メッセージオブジェクト
     */
    @JmsListener(destination = "${reservation.notification.queue}", concurrency = "${reservation.notification.concurrency}")
    @Transactional
    public void receive(ReservationNotificationMessage message) {

        // 2重受信の場合は処理をスキップする。
        if (duplicateMessageChecker.isDuplicated(message)) {
            return;
        }

        Reservation reservation = message.getReservation();

        // 予約審査を実施
        reservationInspectionService.inspect(reservation);

        // 予約結果をユーザに通知
        reservationNotificationService.notify(reservation);
    }

}
