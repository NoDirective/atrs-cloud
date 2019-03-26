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
package jp.co.ntt.atrs.app.testcase;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.screenshot;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.github.bonigarcia.wdm.WebDriverManager;
import jp.co.ntt.atrs.app.bean.PublicCustomerBean;
import jp.co.ntt.atrs.app.page.ReserveCompletePage;
import jp.co.ntt.atrs.app.page.FlightSearchResultPage;
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

    @Value("${target.geckodriverVersion}")
    private String geckodriverVersion;

    @Before
    public void setUp() {

        // 生成するドライバのインスタンスを取得
        if (System.getProperty("webdriver.gecko.driver") == null) {
            WebDriverManager.firefoxdriver().version(geckodriverVersion)
                    .setup();
        }
    }

    @After
    public void tearDown() {

        // ログイン状態の場合ログアウトする
        TopPage topPage = open(applicationContextUrl, TopPage.class);
        if (topPage.isLoggedIn()) {
            topPage.logout();
        }
    }

    /**
     * 会員ユーザでフライト予約ができることを確認する。
     */
    @Test
    public void reserveRoundTripTicketByMemberTest() {

        // 事前準備:ページオブジェクトを生成する。
        TopPage topPage;
        FlightSearchResultPage flightSearchResultPage;
        ReserveCompletePage reserveCompletePage;

        // テスト実行:ログインしてフライト予約を行う。
        topPage = open(applicationContextUrl, TopPage.class).login("0000000001",
                "aaaaa11111");

        // サスペンド:ログインしていることを確認する。
        topPage.getHeaderContent().shouldHave(text("ログアウト"));
        flightSearchResultPage = topPage.searchFlight("大阪(伊丹)")
                .selectRoundTripFlightOutward()
                .toHomewardFrightSearchResultPage();

        // フライト情報から基準日を取得する。
        String depDateTD = flightSearchResultPage.getDepDateTd().getText()
                .replace(" ", "");

        flightSearchResultPage.moveToNextDay();

        // サスペンド:日付が変更されていることを確認する。
        flightSearchResultPage.getDepDateTd().shouldNotHave(text(depDateTD));
        reserveCompletePage = flightSearchResultPage
                .selectRoundTripFlightHomeward().toFlightReserveFormPageAsUser()
                .toFlightReserveConfirmPage().toFlightReserveCompletePage();

        // 証跡の取得
        screenshot("reserveRoundTripTicketByMemberTest");

        // アサート:予約フライト情報にヘッダ、往路、復路の3行が存在することを確認する。
        reserveCompletePage.getReserveflightlist().shouldHaveSize(3);
    }

    /**
     * 非会員ユーザでフライト予約ができることを確認する。
     */
    @Test
    public void reserveRoundTripTicketByThePublicTest() {

        // 事前準備:ページオブジェクトを生成する。
        FlightSearchResultPage flightSearchResultPage;
        ReserveCompletePage ReserveCompletePage;

        // 代表者と同乗者の情報を設定する。
        PublicCustomerBean customer = new PublicCustomerBean();
        customer.setKanaFamilyName("デンデン");
        customer.setKanaGivenName("ハナコ");
        customer.setAge("45");
        customer.setGender(Gender.F);
        customer.setTel1("050");
        customer.setTel2("9999");
        customer.setTel3("9999");
        customer.setMail("success@simulator.amazonses.com");
        customer.setKanaFamilyName2("デンデン");
        customer.setKanaGivenName2("タロウ");
        customer.setAge2("50");
        customer.setGender2(Gender.M);

        // テスト実行:ログインせずにフライト予約を行う。
        flightSearchResultPage = open(applicationContextUrl, TopPage.class)
                .searchFlight("大阪(伊丹)").selectRoundTripFlightOutward()
                .toHomewardFrightSearchResultPage();

        // フライト情報から基準日を取得する。
        String depDateTD = flightSearchResultPage.getDepDateTd().getText()
                .replace(" ", "");

        flightSearchResultPage.moveToNextDay();

        // サスペンド:日付が変更されていることを確認する。
        flightSearchResultPage.getDepDateTd().shouldNotHave(text(depDateTD));
        ReserveCompletePage = flightSearchResultPage
                .selectRoundTripFlightHomeward()
                .toFlightReserveFormPageAsThePublic().setCustomerInfo(customer)
                .toFlightReserveConfirmPage().toFlightReserveCompletePage();

        // 証跡の取得
        screenshot("reserveRoundTripTicketByThePublicTest");

        // アサート:予約フライト情報にヘッダ、往路、復路の3行が存在することを確認する。
        ReserveCompletePage.getReserveflightlist().shouldHaveSize(3);
    }
}
