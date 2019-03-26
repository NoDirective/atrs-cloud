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

/**
 * 予約通知送受信オブジェクト。
 */
public class ReservationNotificationMessage extends AtrsMessage {

    /**
     * シリアルバージョンUID
     */
    private static final long serialVersionUID = -6473092822597921306L;

    /**
     * 予約情報
     */
    private Reservation reservation;

    /**
     * 予約情報を取得する。
     * @return 予約情報
     */
    public Reservation getReservation() {
        return reservation;
    }

    /**
     * 予約情報を設定する。
     * @param reservation 予約情報
     */
    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

}
