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
package jp.co.ntt.atrs.domain.repository.reservation;

import jp.co.ntt.atrs.domain.model.Passenger;
import jp.co.ntt.atrs.domain.model.Reservation;
import jp.co.ntt.atrs.domain.model.ReserveFlight;

/**
 * 予約テーブルにアクセスするリポジトリインターフェース。
 * @author NTT 電電太郎
 */
public interface ReservationRepository {

    /**
     * 予約情報を登録する。
     * @param reservation 予約情報
     * @return 登録件数
     */
    int insert(Reservation reservation);

    /**
     * 予約情報を削除する。
     * @param reservation 予約情報
     * @return 削除件数
     */
    int delete(Reservation reservation);

    /**
     * 予約フライト情報を登録する。
     * @param reserveFlight 予約フライト情報
     * @return 登録件数
     */
    int insertReserveFlight(ReserveFlight reserveFlight);

    /**
     * 予約フライト情報を削除する。
     * @param reserveFlight 予約フライト情報
     * @return 削除件数
     */
    int deleteReserveFlight(ReserveFlight reserveFlight);

    /**
     * 搭乗者情報を登録する。
     * @param passenger 搭乗者情報
     * @return 登録件数
     */
    int insertPassenger(Passenger passenger);

    /**
     * 搭乗者情報を削除する。
     * @param passenger 搭乗者情報
     * @return 削除件数
     */
    int deletePassenger(Passenger passenger);

}
