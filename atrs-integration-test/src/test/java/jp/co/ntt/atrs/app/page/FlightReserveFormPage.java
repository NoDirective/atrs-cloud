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
package jp.co.ntt.atrs.app.page;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

import jp.co.ntt.atrs.app.bean.PublicCustomerBean;

import static com.codeborne.selenide.Selectors.*;

public class FlightReserveFormPage {

    /**
     * お申込み内容確認ページを表示する。
     * @return FlightReserveFormPage お申込み内容確認ページ
     */
    public FlightReserveConfirmPage toFlightReserveConfirmPage() {
        $(".forward").click();
        // 遷移が完了したことを確認してPageを返す。
        $(byId("content")).waitUntil(text("お申込み内容確認"), 2000);
        return new FlightReserveConfirmPage();
    }

    /**
     * お客様情報を入力する。
     * @return FlightReserveFormPage お客様情報入力ページ
     */
    public FlightReserveFormPage setCustomerInfo(PublicCustomerBean customer) {

        // 搭乗者1の設定
        $(byId("reservationForm.passengerFormList0.familyName")).setValue(
                customer.getKanaFamilyName());
        $(byId("reservationForm.passengerFormList0.givenName")).setValue(
                customer.getKanaGivenName());
        $(byId("reservationForm.passengerFormList0.age")).setValue(customer
                .getAge());
        $(byName("reservationForm.passengerFormList[0].gender")).selectRadio(
                customer.getGender().toString());

        // 搭乗者2の設定
        $(byId("reservationForm.passengerFormList1.familyName")).setValue(
                customer.getKanaFamilyName2());
        $(byId("reservationForm.passengerFormList1.givenName")).setValue(
                customer.getKanaGivenName2());
        $(byId("reservationForm.passengerFormList1.age")).setValue(customer
                .getAge2());
        $(byName("reservationForm.passengerFormList[1].gender")).selectRadio(
                customer.getGender2().toString());

        // 予約代表者の設定
        $(byId("reservationForm.repFamilyName")).setValue(customer
                .getKanaFamilyName());
        $(byId("reservationForm.repGivenName")).setValue(customer
                .getKanaGivenName());
        $(byId("reservationForm.repAge")).setValue(customer.getAge());
        $(byName("reservationForm.repGender")).selectRadio(customer.getGender()
                .toString());
        $(byId("reservationForm.repTel1")).setValue(customer.getTel1());
        $(byId("reservationForm.repTel2")).setValue(customer.getTel2());
        $(byId("reservationForm.repTel3")).setValue(customer.getTel3());
        $(byId("rep_mail")).setValue(customer.getMail());
        return this;
    }
}
