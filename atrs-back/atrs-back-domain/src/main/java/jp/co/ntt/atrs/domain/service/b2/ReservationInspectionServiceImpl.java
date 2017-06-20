/*
 * Copyright(c) 2017 NTT Corporation.
 */
package jp.co.ntt.atrs.domain.service.b2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jp.co.ntt.atrs.domain.model.Reservation;

/**
 * チケット予約審査サービス実装クラス。
 * @author NTT 電電太郎
 */
@Service
public class ReservationInspectionServiceImpl implements
                                             ReservationInspectionService {

    /**
     * ロガー。
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(ReservationInspectionServiceImpl.class);

    /**
     * ダミー処理を行う時間。ミリ秒。
     */
    @Value("${reservation.inspection.processing.time}")
    private Integer processingTime;

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
