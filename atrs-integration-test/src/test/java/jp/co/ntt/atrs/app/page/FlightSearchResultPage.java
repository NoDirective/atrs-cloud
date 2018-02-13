/*
 * Copyright 2014-2017 NTT Corporation.
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

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selenide.$;

public class FlightSearchResultPage {

    /**
     * テーブルデータ1行目の往復のフライト（往路）を選択する
     * @return SelectFlightConfimPage 選択フライト確認ページ
     */
    public SelectFlightConfimPage selectRoundTripFlightOutward() {
        $(byId("vacantflightlist")).$("tr", 1).$(byName("select"), 1).click();
        // 遷移が完了したことを確認してPageを返す。
        $(byId("content")).waitUntil(text("選択フライト確認"), 2000);
        return new SelectFlightConfimPage();
    }

    /**
     * テーブルデータ10行目の往復のフライト（復路）を選択する
     * @return SelectFlightConfimPage 選択フライト確認ページ
     */
    public SelectFlightConfimPage selectRoundTripFlightHomeward() {
        $(byId("vacantflightlist")).$("tr", 10).$(byName("select"), 1).click();
        // 遷移が完了したことを確認してPageを返す。
        $(byId("content")).waitUntil(text("空席照会結果"), 2000);
        return new SelectFlightConfimPage();
    }

    /**
     * フライト情報の搭乗日の翌日に移動する。
     * @return FlightSearchResultPage 空席照会結果ページ
     */
    public FlightSearchResultPage moveToNextDay() {
        // フライト情報から基準日を取得する
        String[] depDateTD = $(".dep_date_td").getText().split(" ", 0);
        String baseDate = depDateTD[0];
        $(byId("vacantsearch")).$(".util-btn").click();
        // 遷移が完了したことを確認してPageを返す。
        $(".dep_date_td").waitUntil(not(text(baseDate)), 2000);
        return this;
    }

}
