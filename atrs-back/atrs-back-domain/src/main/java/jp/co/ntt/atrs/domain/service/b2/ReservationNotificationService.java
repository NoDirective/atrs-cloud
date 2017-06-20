/*
 * Copyright(c) 2017 NTT Corporation.
 */
package jp.co.ntt.atrs.domain.service.b2;

import jp.co.ntt.atrs.domain.model.Reservation;

/**
 * チケット予約通知サービスインタフェース。
 * @author NTT 電電太郎
 */
public interface ReservationNotificationService {

    /**
     * チケット予約完了をクライアントへ通知する。
     * @param reservation 予約情報
     */
    void notify(Reservation reservation);

}
