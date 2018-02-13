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
package jp.co.ntt.atrs.app.listener;

import javax.inject.Inject;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.support.JmsHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import jp.co.ntt.atrs.domain.common.exception.DuplicateReceivingException;
import jp.co.ntt.atrs.domain.model.Reservation;
import jp.co.ntt.atrs.domain.service.b2.ReservationInspectionService;

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
     * キューからメッセージを受信し、フロントとの非同期処理を行う
     * @param message メッセージオブジェクト
     */
    @JmsListener(destination = "${reservation.notification.queue}", concurrency = "${reservation.notification.concurrency}")
    public void receive(Reservation reservation,
            @Header(JmsHeaders.MESSAGE_ID) String messageId) {

        try{
            // 予約審査を実施
            reservationInspectionService.inspectAndNotify(reservation,
                    messageId);
        }catch(DuplicateReceivingException e){
            // 2重受信の場合は処理をスキップする。
            return;
        }
    }

}
