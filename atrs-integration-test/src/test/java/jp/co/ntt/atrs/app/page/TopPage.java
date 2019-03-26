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
import static com.codeborne.selenide.Selectors.byLinkText;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

import org.springframework.test.context.ContextConfiguration;

import com.codeborne.selenide.SelenideElement;

/**
 * Topページのページオブジェクトクラス。
 */
@ContextConfiguration(locations = {
        "classpath:META-INF/spring/seleniumContext.xml" })
public class TopPage {

    private SelenideElement headerContent;

    /**
     * TopPageのコンストラクタ。
     */
    public TopPage() {
        this.headerContent = $(byId("header-content"));
    }

    /**
     * ログアウトボタンの要素を返却する。
     * @return SelenideElement
     */
    public SelenideElement getHeaderContent() {
        return headerContent;
    }

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
        return this;
    }

    /**
     * ログアウトする。
     * @return TopPage トップページ
     */
    public TopPage logout() {
        headerContent.$(".button").click();
        $(".logout_forward").click();
        return this;
    }

    /**
     * ログイン状態を返却する。
     * @return boolean ログイン状態
     */
    public boolean isLoggedIn() {
        if (headerContent.$(byText("ログアウト")).exists()) {
            return true;
        }
        return false;
    }

    /**
     * ユーザ情報登録ページを表示する。
     * @return MemberRegisterFormPage ユーザ情報登録ページ
     */
    public MemberRegisterFormPage toUserRegisterPage() {
        $(byLinkText("ユーザ情報登録")).click();
        return new MemberRegisterFormPage();
    }

    /**
     * ユーザ情報管理ページを表示する。
     * @return MemberDetailPage ユーザ情報管理ページ
     */
    public MemberDetailPage toUserDetailPage() {
        $(byLinkText("ユーザ情報管理")).click();
        return new MemberDetailPage();
    }

    /**
     * 空席照会結果ページを表示する。
     * @param airport 空港名
     * @return FlightSearchResultPage 空席照会結果ページ
     */
    public FlightSearchResultPage searchFlight(String airport) {
        $(byId("flightSearchCriteriaForm.arrAirportCd")).selectOption(airport);
        $(".forward").click();
        return new FlightSearchResultPage();
    }
}
