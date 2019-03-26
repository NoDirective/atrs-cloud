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

import static com.codeborne.selenide.Selenide.$;

/**
 * SelectFlightConfimページのページオブジェクトクラス。
 */
public class SelectFlightConfimPage {

    /**
     * 復路の空席照会結果ページを表示する。
     * @return FlightSearchResultPage 空席照会結果ページ
     */
    public FlightSearchResultPage toHomewardFrightSearchResultPage() {
        $(".backwardx1_5").click();
        return new FlightSearchResultPage();
    }

    /**
     * お客様情報入力ページを表示する。
     * @return ReserveFormPage お客様情報入力ページ
     */
    public ReserveFormPage toFlightReserveFormPageAsUser() {
        $(".forward").click();
        return new ReserveFormPage();
    }

    /**
     * 未ログイン時に一般予約を選択しお客様情報入力ページを表示する。
     * @return ReserveFormPage お客様情報入力ページ
     */
    public ReserveFormPage toFlightReserveFormPageAsThePublic() {
        $(".forward", 1).click();
        return new ReserveFormPage();
    }
}
