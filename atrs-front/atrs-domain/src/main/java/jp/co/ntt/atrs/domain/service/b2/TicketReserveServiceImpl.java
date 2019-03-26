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

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.exception.SystemException;

import jp.co.ntt.atrs.domain.common.exception.AtrsBusinessException;
import jp.co.ntt.atrs.domain.common.logging.LogMessages;
import jp.co.ntt.atrs.domain.common.shard.annotation.ShardWithAccount;
import jp.co.ntt.atrs.domain.common.util.FareUtil;
import jp.co.ntt.atrs.domain.model.FareType;
import jp.co.ntt.atrs.domain.model.FareTypeCd;
import jp.co.ntt.atrs.domain.model.Flight;
import jp.co.ntt.atrs.domain.model.Gender;
import jp.co.ntt.atrs.domain.model.Member;
import jp.co.ntt.atrs.domain.model.Passenger;
import jp.co.ntt.atrs.domain.model.Reservation;
import jp.co.ntt.atrs.domain.model.ReserveFlight;
import jp.co.ntt.atrs.domain.model.Route;
import jp.co.ntt.atrs.domain.repository.flight.FlightRepository;
import jp.co.ntt.atrs.domain.repository.member.MemberRepository;
import jp.co.ntt.atrs.domain.repository.reservation.ReservationRepository;
import jp.co.ntt.atrs.domain.service.b0.TicketSharedService;

/**
 * チケット予約のサービス実装クラス。
 * @author NTT 電電三郎
 */
@Service
public class TicketReserveServiceImpl implements TicketReserveService {

    /**
     * 予約代表者に必要な最小年齢。
     */
    @Value("${atrs.representativeMinAge}")
    int representativeMinAge;

    /**
     * 大人運賃が適用される最小年齢。
     */
    @Value("${atrs.adultPassengerMinAge}")
    int adultPassengerMinAge;

    /**
     * 大人運賃に対する小児運賃の比率(%)。
     */
    @Value("${atrs.childFareRate}")
    int childFareRate;

    /**
     * フライト情報リポジトリ。
     */
    @Inject
    FlightRepository flightRepository;

    /**
     * カード会員情報リポジトリ。
     */
    @Inject
    MemberRepository memberRepository;

    /**
     * チケット共通サービス。
     */
    @Inject
    TicketSharedService ticketSharedService;

    /**
     * 予約情報リポジトリ
     */
    @Inject
    ReservationRepository reservationRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public int calculateTotalFare(List<Flight> flightList,
            List<Passenger> passengerList) {

        Assert.notEmpty(flightList, "flightList must contain elements");
        Assert.notEmpty(passengerList, "passengerList must contain elements");

        // 小児搭乗者数（12歳未満の搭乗者数）
        int childNum = 0;
        // 小児搭乗者数をカウントする。
        for (Passenger passenger : passengerList) {
            // リスト要素の null チェック
            Assert.notNull(passenger, "passenger must not be null");
            if (passenger.getAge() < adultPassengerMinAge) {
                childNum++;
            }
        }

        // 12歳以上搭乗者数
        int adultNum = passengerList.size() - childNum;

        // 運賃から合計金額を計算する。
        // 合計金額 = 往路の合計金額 + 復路の合計金額
        // フライト単位の合計金額 = 運賃 × 搭乗者数(12歳以上) + (基本運賃 × 小児運賃の比率 - 運賃種別ごとの割引額) × 搭乗者数（12歳未満)
        // 運賃種別ごとの割引額 = 基本運賃 × 割引率

        // 合計金額にフライト単位の合計金額を加算する。
        int totalFare = 0;
        for (Flight flight : flightList) {
            // リスト要素の null チェック
            Assert.notNull(flight, "flight must not be null");

            Route route = flight.getFlightMaster().getRoute();
            int baseFare = ticketSharedService.calculateBasicFare(route
                    .getBasicFare(), flight.getBoardingClass()
                            .getBoardingClassCd(), flight.getDepartureDate());

            int discountRate = flight.getFareType().getDiscountRate();
            int boardingFare = ticketSharedService.calculateFare(baseFare,
                    discountRate);

            int fare = boardingFare * adultNum + baseFare * (childFareRate
                    - discountRate) / 100 * childNum;

            // 合計金額
            totalFare += fare;
        }

        // 合計金額の100円未満を切り上げる。
        totalFare = FareUtil.ceilFare(totalFare);

        // 合計金額を返却する。
        return totalFare;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public void validateReservation(
            Reservation reservation) throws BusinessException {
        Assert.notNull(reservation, "reservation must not be null");
        Assert.notEmpty(reservation.getReserveFlightList(),
                "reserveFlightList must contain elements");

        // 予約代表者の年齢が18歳以上であることを確認する。
        validateRepresentativeAge(reservation.getRepAge());

        // 予約フライト情報一覧を取得する。
        List<ReserveFlight> reserveFlightList = reservation
                .getReserveFlightList();

        // 運賃種別の適用可否を確認する。
        validateFareType(reserveFlightList);

        // 予約者代表者の情報をチェックする。
        validateRepresentativeMemberInfo(reservation);

        // 搭乗者情報とカード会員情報の照合を行う。
        validatePassengerMemberInfo(reserveFlightList);
    }

    /**
     * 搭乗者情報とカード会員情報の照合を行う。
     * @param reserveFlightList 予約フライト情報一覧
     * @throws AtrsBusinessException 照合失敗例外
     */
    private void validatePassengerMemberInfo(
            List<ReserveFlight> reserveFlightList) throws AtrsBusinessException {
        for (ReserveFlight reserveFlight : reserveFlightList) {

            Assert.notNull(reserveFlight, "reserveFlight must not be null");

            // 搭乗者情報がDBから取得したカード会員情報と同一であることを確認する。

            // 搭乗者情報一覧
            List<Passenger> passengerList = reserveFlight.getPassengerList();
            Assert.notEmpty(passengerList,
                    "passengerList must contain elements");

            int position = 1;
            for (Passenger passenger : passengerList) {
                Assert.notNull(passenger, "passenger must not be null");

                String passengerCustomerNo = passenger.getMember()
                        .getCustomerNo();

                // 搭乗者お客様番号が入力されているときのみ照合を行う。
                if (StringUtils.hasLength(passengerCustomerNo)) {

                    // 搭乗者のカード会員情報を取得する。
                    Member passengerMember = memberRepository.findOne(
                            passengerCustomerNo);

                    if (passengerMember == null) {
                        throw new AtrsBusinessException(TicketReserveErrorCode.E_AR_B2_2007, position);
                    }

                    // 取得した搭乗者のカード会員情報と搭乗者情報が同一であることを確認する。
                    if (!(passenger.getFamilyName().equals(passengerMember
                            .getKanaFamilyName()) && passenger.getGivenName()
                                    .equals(passengerMember.getKanaGivenName())
                            && passenger.getGender().equals(passengerMember
                                    .getGender()))) {
                        throw new AtrsBusinessException(TicketReserveErrorCode.E_AR_B2_2008, position);
                    }
                }
                position++;
            }

        }
    }

    /**
     * 予約代表者の情報をチェックする。
     * @param reservation 予約情報
     * @throws AtrsBusinessException チェック失敗例外
     */
    private void validateRepresentativeMemberInfo(
            Reservation reservation) throws AtrsBusinessException {
        // 予約代表者お客様番号を取得する。
        String repCustomerNo = reservation.getRepMember().getCustomerNo();

        // 予約代表者お客様番号が入力されているとき以下の処理を行う。
        if (StringUtils.hasLength(repCustomerNo)) {

            // 予約代表者のカード会員情報を取得する。
            Member repMember = memberRepository.findOne(repCustomerNo);

            // 取得した予約代表者のカード会員情報が null でないことを確認する。
            if (repMember == null) {
                throw new AtrsBusinessException(TicketReserveErrorCode.E_AR_B2_2004);
            }

            // 取得した予約代表者のカード会員情報と予約代表者情報が同一であることを確認する。
            if (!(reservation.getRepFamilyName().equals(repMember
                    .getKanaFamilyName()) && reservation.getRepGivenName()
                            .equals(repMember.getKanaGivenName()) && reservation
                                    .getRepGender().equals(repMember
                                            .getGender()))) {
                throw new AtrsBusinessException(TicketReserveErrorCode.E_AR_B2_2005);
            }
        }
    }

    /**
     * 運賃種別の適用可否を確認する。
     * @param reserveFlightList 予約フライト情報一覧
     * @throws AtrsBusinessException チェック失敗例外
     */
    private void validateFareType(
            List<ReserveFlight> reserveFlightList) throws AtrsBusinessException {
        for (ReserveFlight reserveFlight : reserveFlightList) {

            Assert.notNull(reserveFlight, "reserveFlight must not be null");

            // 運賃種別
            FareType fareType = reserveFlight.getFlight().getFareType();

            // 運賃種別コード
            FareTypeCd fareTypeCd = fareType.getFareTypeCd();

            // 搭乗者情報一覧
            List<Passenger> passengerList = reserveFlight.getPassengerList();
            Assert.notEmpty(passengerList,
                    "passengerList must contain elements");

            if (fareTypeCd == FareTypeCd.LD) {
                // 運賃種別がレディース割であり、且つ、男性の搭乗者がいる場合、業務例外をスローする。
                for (Passenger passenger : passengerList) {

                    Assert.notNull(passenger, "passenger must not be null");

                    if (passenger.getGender() == Gender.M) {
                        throw new AtrsBusinessException(TicketReserveErrorCode.E_AR_B2_2009);
                    }
                }
            } else if (fareTypeCd == FareTypeCd.GD) {
                // 運賃種別がグループ割であり、且つ、搭乗者数が利用可能最少人数未満の場合、業務例外をスローする。
                int passengerMinNum = fareType.getPassengerMinNum();
                if (passengerList.size() < passengerMinNum) {
                    throw new AtrsBusinessException(TicketReserveErrorCode.E_AR_B2_2012, fareType
                            .getFareTypeName(), passengerMinNum);
                }
            }
        }
    }

    /**
     * 予約代表者の年齢が予約代表者最小年齢以上であることをチェックする。
     * @param age 予約代表者の年齢
     */
    private void validateRepresentativeAge(int age) {
        if (age < representativeMinAge) {
            throw new AtrsBusinessException(TicketReserveErrorCode.E_AR_B2_2006, representativeMinAge);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @ShardWithAccount("reservation.repMember.customerNo")
    public String registerMemberReservation(Reservation reservation) {

        Assert.notNull(reservation, "reservation must not be null");

        int reservationInsertCount = reservationRepository.insert(reservation);
        if (reservationInsertCount != 1) {
            throw new SystemException(LogMessages.E_AR_A0_L9002
                    .getCode(), LogMessages.E_AR_A0_L9002.getMessage(
                            reservationInsertCount, 1));
        }
        // 予約番号を取得する
        String reserveNo = reservation.getReserveNo();

        // 予約フライト情報の登録および各予約フライト情報が持つ全搭乗者情報の登録を行う。
        for (ReserveFlight reserveFlight : reservation.getReserveFlightList()) {

            // 予約フライト情報をDBに登録するために予約番号を設定する。
            reserveFlight.setReserveNo(reserveNo);

            // 予約フライト情報を登録する。
            int reserveFlightInsertCount = reservationRepository
                    .insertReserveFlight(reserveFlight);
            if (reserveFlightInsertCount != 1) {
                throw new SystemException(LogMessages.E_AR_A0_L9002
                        .getCode(), LogMessages.E_AR_A0_L9002.getMessage(
                                reserveFlightInsertCount, 1));
            }

            // 全搭乗者情報を登録する。
            for (Passenger passenger : reserveFlight.getPassengerList()) {
                passenger.setReserveFlightNo(reserveFlight
                        .getReserveFlightNo());
                int passengerInsertCount = reservationRepository
                        .insertPassenger(passenger);
                if (passengerInsertCount != 1) {
                    throw new SystemException(LogMessages.E_AR_A0_L9002
                            .getCode(), LogMessages.E_AR_A0_L9002.getMessage(
                                    passengerInsertCount, 1));
                }
            }
        }
        return reserveNo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public TicketReserveDto registerReservation(String reserveNo,
            Reservation reservation) {

        Assert.notNull(reservation, "reservation must not be null");

        // 予約フライト情報一覧
        List<ReserveFlight> reserveFlightList = reservation
                .getReserveFlightList();
        Assert.notEmpty(reserveFlightList,
                "reserveFlightList must contain elements");

        // 予約フライト情報に対して空席数の確認および更新を行う。
        for (ReserveFlight reserveFlight : reserveFlightList) {
            Assert.notNull(reserveFlight, "reserveFlight must not be null");

            Flight flight = reserveFlight.getFlight();
            Assert.notNull(flight, "flight must not be null");

            // 搭乗日は運賃種別予約可能時期かをチェック
            if (!ticketSharedService.isAvailableFareType(flight.getFareType(),
                    flight.getDepartureDate())) {
                throw new AtrsBusinessException(TicketReserveErrorCode.E_AR_B2_2010);
            }

            // 空席数を更新するために、フライト情報を取得する（排他）。
            flight = flightRepository.findOneForUpdate(flight
                    .getDepartureDate(), flight.getFlightMaster()
                            .getFlightName(), flight.getBoardingClass(), flight
                                    .getFareType());
            int vacantNum = flight.getVacantNum();

            // 搭乗者数
            int passengerNum = reserveFlight.getPassengerList().size();

            // 取得した空席数が搭乗者数以上であることを確認する。
            // 空席数が搭乗者数未満の場合、業務例外をスローする。
            if (vacantNum < passengerNum) {
                throw new AtrsBusinessException(TicketReserveErrorCode.E_AR_B2_2011);
            }

            // 取得した空席数から搭乗者数を引いた数を、フライト情報の空席数に設定する。
            flight.setVacantNum(vacantNum - passengerNum);

            // 空席数を更新する。
            int flightUpdateCount = flightRepository.update(flight);
            if (flightUpdateCount != 1) {
                throw new SystemException(LogMessages.E_AR_A0_L9002
                        .getCode(), LogMessages.E_AR_A0_L9002.getMessage(
                                flightUpdateCount, 1));
            }
        }

        // 支払期限は往路の搭乗日とする。
        Date paymentDate = reserveFlightList.get(0).getFlight()
                .getDepartureDate();

        return new TicketReserveDto(reserveNo, paymentDate);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @ShardWithAccount("reservation.repMember.customerNo")
    public void restoreReservation(Reservation reservation) {
        // 予約フライト情報の登録および各予約フライト情報が持つ全搭乗者情報の登録を行う。
        for (ReserveFlight reserveFlight : reservation.getReserveFlightList()) {
            if (reserveFlight.getReserveFlightNo() != null) {
                // 全搭乗者情報を削除する。
                for (Passenger passenger : reserveFlight.getPassengerList()) {
                    if (passenger.getPassengerNo() != null) {
                        reservationRepository.deletePassenger(passenger);
                    }
                }
                // DBに登録した予約フライト情報を削除する。
                reservationRepository.deleteReserveFlight(reserveFlight);
            }
        }
        // 予約番号を削除する
        reservationRepository.delete(reservation);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Member findMember(String customerNo) {
        Assert.hasText(customerNo, "customerNo must not be empty");
        return memberRepository.findOne(customerNo);
    }

}
