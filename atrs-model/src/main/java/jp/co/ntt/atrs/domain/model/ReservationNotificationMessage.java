/*
 * Copyright(c) 2017 NTT Corporation.
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
