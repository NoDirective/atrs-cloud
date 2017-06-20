/*
 * Copyright(c) 2017 NTT Corporation.
 */
package jp.co.ntt.atrs.domain.service.b2;

import jp.co.ntt.atrs.domain.model.Reservation;

/**
 * チケット予約審査サービスインタフェース。
 * @author NTT 電電太郎
 */
public interface ReservationInspectionService {

    /**
     * チケット予約審査を行う。
     * @param reservation 予約情報
     */
    public void inspect(Reservation reservation);

}
