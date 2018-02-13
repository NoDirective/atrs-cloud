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
package jp.co.ntt.atrs.app.testcase;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.screenshot;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import jp.co.ntt.atrs.app.bean.PublicCustomerBean;
import jp.co.ntt.atrs.app.page.TopPage;
import jp.co.ntt.atrs.domain.model.Gender;
import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:META-INF/spring/seleniumContext.xml" })
public class TicketReserveTest extends TestCase {

    @Value("${target.applicationContextUrl}")
    private String applicationContextUrl;

    @Value("${testdata.path}")
    private String testdataPath;

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
        // ログイン状態の場合ログアウトする。
        TopPage topPage = open(applicationContextUrl, TopPage.class);
        if (topPage.isLoggedIn()) {
            topPage.logout();
        }
    }

    @Test
    public void reserveRoundTripTicketByMemberTest() {

        // 事前準備
        String userId = "0000000001";
        String password = "aaaaa11111";

        // テスト実行
        open(applicationContextUrl, TopPage.class).login(userId, password)
                .searchFlight("大阪(伊丹)").selectRoundTripFlightOutward()
                .toHomewardFrightSearchResultPage().moveToNextDay()
                .selectRoundTripFlightHomeward().toFlightReserveFormPageAsUser()
                .toFlightReserveConfirmPage().toFlightReserveCompletePage();

        // 証跡の取得
        screenshot("reserveRoundTripTicketByMemberTest");

        // アサーション
        $(byId("content")).shouldHave(text("予約申込完了"));
        // 予約フライト情報にヘッダ、往路、復路の3行が存在することを確認
        $(byId("reserveflightlist")).$$("tr").shouldHaveSize(3);
    }

    @Test
    public void reserveRoundTripTicketByThePublicTest() {

        // 予約する一般お客様情報設定
        PublicCustomerBean customer = new PublicCustomerBean();
        // 代表者の設定
        customer.setKanaFamilyName("デンデン");
        customer.setKanaGivenName("ハナコ");
        customer.setAge("45");
        customer.setGender(Gender.F);
        customer.setTel1("050");
        customer.setTel2("9999");
        customer.setTel3("9999");
        customer.setMail("success@simulator.amazonses.com");
        // 同乗者の設定
        customer.setKanaFamilyName2("デンデン");
        customer.setKanaGivenName2("タロウ");
        customer.setAge2("50");
        customer.setGender2(Gender.M);

        // テスト実行
        open(applicationContextUrl, TopPage.class).searchFlight("大阪(伊丹)")
                .selectRoundTripFlightOutward()
                .toHomewardFrightSearchResultPage()
                .moveToNextDay()
                .selectRoundTripFlightHomeward()
                .toFlightReserveFormPageAsThePublic()
                .setCustomerInfo(customer)
                .toFlightReserveConfirmPage()
                .toFlightReserveCompletePage();

        // 証跡の取得
        screenshot("reserveRoundTripTicketByThePublicTest");

        // アサーション
        $(byId("content")).shouldHave(text("予約申込完了"));
        // 予約フライト情報にヘッダ、往路、復路の3行が存在することを確認
        $(byId("reserveflightlist")).$$("tr").shouldHaveSize(3);
    }
}
