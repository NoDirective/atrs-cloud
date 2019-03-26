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
import jp.co.ntt.atrs.app.bean.MemberBean;
import jp.co.ntt.atrs.app.page.TopPage;
import jp.co.ntt.atrs.app.page.MemberDetailPage;
import jp.co.ntt.atrs.app.page.MemberRegisterCompletePage;
import jp.co.ntt.atrs.app.page.MemberRegisterConfirmPage;
import jp.co.ntt.atrs.domain.model.Gender;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:META-INF/spring/seleniumContext.xml" })
public class UserRegisterTest {

    @Value("${target.applicationContextUrl}")
    private String applicationContextUrl;

    @Value("${testdata.path}")
    private String testdataPath;

    private MemberBean member;

    @Value("${target.geckodriverVersion}")
    private String geckodriverVersion;

    @Before
    public void setUp() {

        // 生成するドライバのインスタンスを取得
        if (System.getProperty("webdriver.gecko.driver") == null) {
            WebDriverManager.firefoxdriver().version(geckodriverVersion)
                    .setup();
        }

        // 登録する初期ユーザ情報を設定する
        member = new MemberBean();
        member.setKanjiFamilyName("電電");
        member.setKanjiGivenName("太郎");
        member.setKanaFamilyName("デンデン");
        member.setKanaGivenName("タロウ");
        member.setGender(Gender.M);
        member.setYearOfBirth(1950);
        member.setMonthOfBirth(10);
        member.setDayOfBirth(10);
        member.setTel1("050");
        member.setTel2("9999");
        member.setTel3("9999");
        member.setZipCode1("100");
        member.setZipCode2("8116");
        member.setAddress("東京都千代田区大手町一丁目");
        member.setMail("success@simulator.amazonses.com");
        member.setReEnterMail("success@simulator.amazonses.com");
        member.setCreditTypeCd("MTR");
        member.setCreditNo("01234567890123456");
        member.setCreditMonth("10");
        member.setCreditYear("22");
        member.setPassword("aaaaa11111");
        member.setReEnterPassword("aaaaa11111");
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
     * ユーザ情報の登録と変更ができることを確認する。
     */
    @Test
    public void registerAndUpdateUserTest() {

        // 事前準備:ページオブジェクトを生成する。
        TopPage topPage;
        MemberRegisterConfirmPage memberRegisterConfirmPage;
        MemberDetailPage memberDetailPage;
        MemberRegisterCompletePage memberRegisterCompletePage;

        // ユーザ情報に必要な画像ファイルのパスを設定する。
        String imgPathW = testdataPath + "white.jpg";
        String imgPathB = testdataPath + "black.jpg";

        // テスト実行:ユーザ情報登録を行う。
        memberRegisterConfirmPage = open(applicationContextUrl, TopPage.class)
                .toUserRegisterPage().setMemberInfo(member, imgPathW)
                .toRegisterConfirmPage();

        // サスペンド:登録ボタンが表示されていることを確認する。
        memberRegisterConfirmPage.getForward().shouldHave(text("登録"));
        memberRegisterCompletePage = memberRegisterConfirmPage.registerUser();

        // 証跡の取得
        screenshot("registerAndUpdateUserTest_Registered");

        // アサート:ユーザ情報登録が完了したことを確認する。
        memberRegisterCompletePage.getContent().shouldHave(text("登録完了"));

        // ユーザ情報登録完了ページから登録したユーザIDを取得する。
        String[] resultMsg = memberRegisterCompletePage.getGuide().getText()
                .split(" ", 0);
        member.setUserId(resultMsg[7]);

        // 変更前の電話番号を取得する。
        String oldTel = member.getTel1() + "-" + member.getTel2() + "-" + member
                .getTel3();

        // 変更するユーザ情報を設定する。
        member.setTel1("070");
        member.setTel2("1234");
        member.setTel3("5678");

        // 変更後の電話番号を取得する。
        String newTel = member.getTel1() + "-" + member.getTel2() + "-" + member
                .getTel3();

        // テスト実行:ユーザ情報変更を行う。
        topPage = open(applicationContextUrl, TopPage.class).login(member
                .getUserId(), member.getPassword());

        // サスペンド:ログインしていることを確認する。
        topPage.getHeaderContent().shouldHave(text("ログアウト"));
        memberDetailPage = topPage.toUserDetailPage();

        // ユーザ情報更新前の証跡の取得
        screenshot("registerAndUpdateUserTest_beforeUpdate");

        // 更新
        memberDetailPage = memberDetailPage.toUserUpdatePage().setMemberInfo(
                member, imgPathB).toUpdateConfirmPage().updateUser();

        // ユーザ情報更新後の証跡の取得
        screenshot("registerAndUpdateUserTest_afterUpdate");

        // アサート:ユーザ情報変更が完了したことを確認する。
        memberDetailPage.getContent().shouldHave(text("ユーザ情報管理"));

        // アサート:変更前の電話番号ではないことを確認する。
        memberDetailPage.getContent().shouldNotHave(text(oldTel));

        // アサート:変更後の電話番号であることを確認する。
        memberDetailPage.getContent().shouldHave(text(newTel));
    }
}
