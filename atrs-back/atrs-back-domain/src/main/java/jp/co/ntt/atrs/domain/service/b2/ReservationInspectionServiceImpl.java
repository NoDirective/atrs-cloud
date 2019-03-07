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

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ntt.atrs.domain.common.messaging.DuplicateMessageChecker;
import jp.co.ntt.atrs.domain.model.Reservation;

/**
 * チケット予約審査サービス実装クラス。
 * @author NTT 電電太郎
 */
@Service
public class ReservationInspectionServiceImpl implements
                                              ReservationInspectionService {

    /**
     * 2重受信チェックユーティリティ
     */
    @Inject
    DuplicateMessageChecker duplicateMessageChecker;

    /**
     * チケット予約共通サービス
     */
    @Inject
    ReservationSharedService reservationSharedService;

    /**
     * チケット予約審査とユーザへの通知を行う
     * @param reservation 予約情報
     * @param messageId メッセージID
     */
    @Override
    @Transactional
    public void inspectAndNotify(Reservation reservation, String messageId) {

        // 2重受信チェックを行う
        duplicateMessageChecker.checkDuplicateMessage(messageId);

        // チケット予約審査を行う(ダミー処理)
        reservationSharedService.inspect(reservation);

        // チケット予約完了をユーザに通知する
        reservationSharedService.notify(reservation);
    }

}
