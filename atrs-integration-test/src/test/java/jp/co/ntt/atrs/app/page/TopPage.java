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

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selectors.byLinkText;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(locations = {
        "classpath:META-INF/spring/seleniumContext.xml" })
public class TopPage {

    /**
     * 顧客番号とパスワードを使用してログインする。
     * @param userID 顧客番号
     * @param password パスワード
     * @return TopPage トップページ
     */
    public TopPage login(String userID, String password) {
        $(byId("headerCustomerNo")).setValue(userID);
        $(byId("headerPassword")).setValue(password);
        $(".button").click();
        // ログインが完了したことを確認してPageを返す。
        $(byId("header-content")).waitUntil(text("ログアウト"), 2000);
        return this;
    }

    /**
     * ログアウトする。
     * @return TopPage トップページ
     */
    public TopPage logout() {
        $(byId("header-content")).$(".button").click();
        $(".logout_forward").click();
        return this;
    }

    /**
     * ログイン状態を返却する。
     * @return boolean ログイン状態
     */
    public boolean isLoggedIn() {
        if ($(byId("header-content")).$(byText("ログアウト")).exists()) {
            return true;
        }
        return false;
    }

    /**
     * ユーザ情報登録画面を表示する。
     * @return UserRegisterPage ユーザ情報登録ページ
     */
    public UserRegisterPage toUserRegisterPage() {
        $(byLinkText("ユーザ情報登録")).click();
        return new UserRegisterPage();
    }

    /**
     * ユーザ情報管理画面を表示する。
     * @return UserDetailPage ユーザ情報管理ページ
     */
    public UserDetailPage toUserDetailPage() {
        $(byLinkText("ユーザ情報管理")).click();
        return new UserDetailPage();
    }

    /**
     * 空席状況を照会する。
     * @param airport 空港名
     * @return FlightSearchResultPage 空席照会結果ページ
     */
    public FlightSearchResultPage searchFlight(String airport) {
        $(byId("flightSearchCriteriaForm.arrAirportCd")).selectOption(airport);
        $(".forward").click();
        // 遷移が完了したことを確認してPageを返す。
        $(byId("content")).waitUntil(text("空席照会結果"), 2000);
        return new FlightSearchResultPage();
    }
}
