/*
 * Copyright(c) 2017 NTT Corporation.
 */
package jp.co.ntt.atrs.app.testcase;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.page;
import static com.codeborne.selenide.Selenide.screenshot;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import jp.co.ntt.atrs.app.bean.MemberBean;
import jp.co.ntt.atrs.app.page.TopPage;
import jp.co.ntt.atrs.app.page.UserDetailPage;
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

    @Before
    public void setUp() {

        // 登録する初期ユーザ情報を設定する。
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
        // ログイン状態の場合ログアウトする。
        TopPage topPage = open(applicationContextUrl, TopPage.class);
        if (topPage.isLoggedIn()) {
            topPage.logout();
        }
    }

    @Test
    public void registerAndUpdateUserTest() {

        // 事前準備

        // ユーザ情報に必要な画像ファイルのパスを設定する。
        String imgPathW = testdataPath + "white.jpg";
        String imgPathB = testdataPath + "black.jpg";

        // ユーザ登録
        // テスト実行
        open(applicationContextUrl, TopPage.class).toUserRegisterPage()
                .setMemberInfo(member, imgPathW).toRegisterConfirmPage()
                .registerUser();

        // 証跡の取得
        screenshot("registerAndUpdateUserTest" + "_Registered");

        // アサーション
        $(byId("content")).shouldHave(text("登録完了"));

        // 会員登録完了画面から登録したユーザIDを取得する
        String resultMsg = $("p.guide").getText();
        String[] splitedMsg = resultMsg.split(" ", 0);
        String newUserId = splitedMsg[7];
        member.setUserId(newUserId);

        // ユーザ情報更新
        // 変更前の電話番号取得
        String oldTel = member.getTel1() + "-" + member.getTel2() + "-" + member
                .getTel3();
        // 変更するユーザ情報を設定する。
        member.setTel1("070");
        member.setTel2("1234");
        member.setTel3("5678");

        // テスト実行
        open(applicationContextUrl, TopPage.class).login(member.getUserId(),
                member.getPassword()).toUserDetailPage();
        // ユーザ情報更新前の証跡の取得
        screenshot("registerAndUpdateUserTest" + "_beforeUpdate");
        // ユーザ更新
        page(UserDetailPage.class).toUserUpdatePage().setMemberInfo(member,
                imgPathB).toUpdateConfirmPage().updateUser();

        // ユーザ情報更新後の証跡の取得
        screenshot("registerAndUpdateUserTest" + "_afterUpdate");

        // アサーション
        $(byId("content")).shouldHave(text("ユーザ情報管理"));
        $(byId("content")).shouldNotHave(text(oldTel));
        $(byId("content")).shouldHave(text("070-1234-5678"));

    }
}
