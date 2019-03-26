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

import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;

/**
 * FlightSearchResultページのページオブジェクトクラス。
 */
public class FlightSearchResultPage {

    private SelenideElement depDateTd;

    /**
     * FlightSearchResultPageのコンストラクタ。
     */
    public FlightSearchResultPage() {
        this.depDateTd = $(".dep_date_td");
    }

    /**
     * 基準日を含む要素を返却する。
     * @return SelenideElement
     */
    public SelenideElement getDepDateTd() {
        return depDateTd;
    }

    /**
     * テーブルデータ1行目の往復のフライト（往路）を選択し選択フライト確認ページを表示する。
     * @return SelectFlightConfimPage 選択フライト確認ページ
     */
    public SelectFlightConfimPage selectRoundTripFlightOutward() {
        $(byId("vacantflightlist")).$("tr", 1).$(byName("select"), 1).click();
        return new SelectFlightConfimPage();
    }

    /**
     * テーブルデータ10行目の往復のフライト（復路）を選択し選択フライト確認ページを表示する。
     * @return SelectFlightConfimPage 選択フライト確認ページ
     */
    public SelectFlightConfimPage selectRoundTripFlightHomeward() {
        $(byId("vacantflightlist")).$("tr", 10).$(byName("select"), 1).click();
        return new SelectFlightConfimPage();
    }

    /**
     * フライト情報の搭乗日の翌日に移動する。
     * @return FlightSearchResultPage 空席照会結果ページ
     */
    public FlightSearchResultPage moveToNextDay() {
        $(byId("vacantsearch")).$(".util-btn").click();
        return this;
    }

}
