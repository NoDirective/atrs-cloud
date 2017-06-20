/*
 * Copyright(c) 2017 NTT Corporation.
 */
package jp.co.ntt.atrs.domain.service.b2;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jp.co.ntt.atrs.domain.common.mail.AtrsMailSender;
import jp.co.ntt.atrs.domain.model.Flight;
import jp.co.ntt.atrs.domain.model.Member;
import jp.co.ntt.atrs.domain.model.Passenger;
import jp.co.ntt.atrs.domain.model.Reservation;
import jp.co.ntt.atrs.domain.model.ReserveFlight;

/**
 * チケット予約通知サービス実装クラス。
 * @author NTT 電電太郎
 */
@Service
public class ReservationNotificationServiceImpl implements ReservationNotificationService {

    /**
     * ロガー。
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(ReservationNotificationServiceImpl.class);

    /**
     * メール送信ユーティリティ
     */
    @Inject
    AtrsMailSender mailSender;

    /**
     * 送信元メールアドレス
     */
    @Value("${reservation.notification.mail.from}")
    private String fromMailAddress;

    /**
     * チケット予約完了をクライアントへ通知する。
     * @param reservation 予約情報
     */
    @Override
    public void notify(Reservation reservation) {

        // 予約情報をオブジェクトから取得
        String fullName = reservation.getRepFamilyName() + " "
                + reservation.getRepGivenName();
        Member representative = reservation.getRepMember();
        String repMail = reservation.getRepMail();

        // 往路情報
        List<ReserveFlight> reserveFlightList = reservation
                .getReserveFlightList();
        Flight outward = reserveFlightList.get(0).getFlight();

        // 搭乗者情報
        List<Passenger> passengers = reservation.getReserveFlightList().get(0)
                .getPassengerList();

        // 予約情報をメール本文形式に変換
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        pw.println("[チケット]");
        pw.println("　■予約番号\t:　" + reservation.getReserveNo());
        pw.println(String.format("　■合計金額\t:　%1$,3d円", reservation
                .getTotalFare()));
        pw.println("　■支払期限\t:　"
                + new SimpleDateFormat("M月d日(E)").format(outward
                        .getDepartureDate()));
        pw.println();
        pw.println("[予約代表者情報]");
        pw.println("　■お客様番号\t:　" + representative.getCustomerNo());
        pw.println("　■代表者氏名\t:　" + fullName + " 様");
        pw.println(String.format("　■年齢\t:　%d歳", reservation.getRepAge()));
        pw.println("　■電話番号\t:　" + reservation.getRepTel());
        pw.println("　■メール\t:　" + repMail);
        pw.println();
        pw.println("[予約フライト情報]");
        pw.println("▽往路");
        pw.println("　■搭乗日\t:　"
                + new SimpleDateFormat("M月d日(E)").format(outward
                        .getDepartureDate()));
        pw.println("　■便名\t:　" + outward.getFlightMaster().getFlightName());
        pw.println("　■出発空港\t:　"
                + outward.getFlightMaster().getRoute().getDepartureAirport()
                        .getName());
        pw.println("　■出発時刻\t:　" + outward.getFlightMaster().getDepartureTime());
        pw.println("　■到着空港\t:　"
                + outward.getFlightMaster().getRoute().getArrivalAirport()
                        .getName());
        pw.println("　■到着時刻\t:　" + outward.getFlightMaster().getArrivalTime());
        pw.println("　■搭乗クラス\t:　"
                + outward.getBoardingClass().getBoardingClassName());
        pw.println("　■運賃種別\t:　" + outward.getFareType().getFareTypeName());
        pw.println(String.format("　■運賃\t:　%1$,3d円", outward.getFlightMaster()
                .getRoute().getBasicFare()));
        pw.println();

        // 復路が存在する場合
        if (reserveFlightList.size() > 1) {
            Flight homeward = reserveFlightList.get(1).getFlight();
            pw.println("▼復路");
            pw.println("　■搭乗日\t:　"
                    + new SimpleDateFormat("M月d日(E)").format(homeward
                            .getDepartureDate()));
            pw.println("　■便名\t:　" + homeward.getFlightMaster().getFlightName());
            pw.println("　■出発空港\t:　"
                    + homeward.getFlightMaster().getRoute()
                            .getDepartureAirport().getName());
            pw.println("　■出発時刻\t:　"
                    + homeward.getFlightMaster().getDepartureTime());
            pw.println("　■到着空港\t:　"
                    + homeward.getFlightMaster().getRoute().getArrivalAirport()
                            .getName());
            pw.println("　■到着時刻\t:　"
                    + homeward.getFlightMaster().getArrivalTime());
            pw.println("　■搭乗クラス\t:　"
                    + homeward.getBoardingClass().getBoardingClassName());
            pw.println("　■運賃種別\t:　" + homeward.getFareType().getFareTypeName());
            pw.println(String.format("　■運賃\t:　%1$,3d円", homeward
                    .getFlightMaster().getRoute().getBasicFare()));
            pw.println();
        }
        pw.println("[搭乗者情報]");
        int i = 1;
        for (Passenger passenger : passengers) {
            pw.println("▽搭乗者" + i);
            pw.println("　■氏名\t:　" + passenger.getFamilyName() + " "
                    + passenger.getGivenName() + " 様");
            pw.println(String.format("　■年齢\t:　%d歳", passenger.getAge()));
            i++;
        }

        pw.close();

        // 予約完了通知をメール送信
        mailSender.sendMail(fromMailAddress, repMail, "予約完了メール", fullName
                + " 様の予約処理が完了しました。\n"
                + "お支払期限までにご購入いただけない場合、すべてのフライトが自動的にキャンセルされます。\n\n"
                + sw.toString());
        LOGGER.info("通知メール送信完了 [予約番号=" + reservation.getReserveNo() + "]");
    }

}
