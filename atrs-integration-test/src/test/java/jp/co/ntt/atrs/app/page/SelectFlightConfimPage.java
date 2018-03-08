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
import static com.codeborne.selenide.Selectors.*;

public class SelectFlightConfimPage {

    /**
     * 復路の空席照会結果を表示する。
     * @return FlightSearchResultPage 空席照会結果ページ
     */
    public FlightSearchResultPage toHomewardFrightSearchResultPage() {
        $(".backwardx1_5").click();
        return new FlightSearchResultPage();
    }

    /**
     * お客様情報入力フォームを表示する。
     * @return FlightReserveFormPage お客様情報入力ページ
     */
    public FlightReserveFormPage toFlightReserveFormPageAsUser() {
        $(".forward").click();
        // 遷移が完了したことを確認してPageを返す。
        $(byId("content")).waitUntil(text("お客様情報入力"), 2000);
        return new FlightReserveFormPage();
    }

    /**
     * 未ログイン時に一般予約を選択し、お客様情報入力フォームを表示する。
     * @return FlightReserveFormPage お客様情報入力ページ
     */
    public FlightReserveFormPage toFlightReserveFormPageAsThePublic() {
        $(".forward", 1).click();
        // 遷移が完了したことを確認してPageを返す。
        $(byId("content")).waitUntil(text("お客様情報入力"), 2000);
        return new FlightReserveFormPage();
    }
}
