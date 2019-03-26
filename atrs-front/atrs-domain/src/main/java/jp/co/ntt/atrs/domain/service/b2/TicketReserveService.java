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

import jp.co.ntt.atrs.domain.model.Flight;
import jp.co.ntt.atrs.domain.model.Member;
import jp.co.ntt.atrs.domain.model.Passenger;
import jp.co.ntt.atrs.domain.model.Reservation;

import org.terasoluna.gfw.common.exception.BusinessException;

import java.util.List;

/**
 * チケット予約のサービスインターフェース。
 * @author NTT 電電三郎
 */
public interface TicketReserveService {

    /**
     * 予約情報の業務ロジックチェックを行う。
     * @param reservation 予約情報
     * @throws BusinessException チェックに引っかかった場合
     */
    void validateReservation(Reservation reservation) throws BusinessException;

    /**
     * 予約チケットの合計金額を計算する。
     * @param flightList 予約するフライトのリスト
     * @param passengerList 搭乗者リスト
     * @return 予約チケットの合計金額
     */
    int calculateTotalFare(List<Flight> flightList,
            List<Passenger> passengerList);

    /**
     * 予約情報をDBに登録し、予約したチケット料金の支払期限を決定する。
     * @param reserveNo 予約番号
     * @param reservation 予約情報
     * @return 予約番号と予約したチケット料金の支払期限
     */
    TicketReserveDto registerReservation(String reserveNo,
            Reservation reservation);

    /**
     * 会員の予約情報をDBに登録し、予約番号を決定する。
     * @param reservation 予約情報
     * @return 予約番号
     */
    String registerMemberReservation(Reservation reservation);

    /**
     * DBに登録を予約情報を戻す。
     * @param reservation 予約情報
     */
    void restoreReservation(Reservation reservation);

    /**
     * お客様番号に該当するカード会員情報を検索する。
     * @param customerNo お客様番号
     * @return カード会員情報
     */
    Member findMember(String customerNo);

}
