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
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selenide.$;

import java.io.File;

import jp.co.ntt.atrs.app.bean.MemberBean;

public class UserUpdatePage {

    String kanjiFamilyName = "memberForm.kanjiFamilyName";

    String kanjiGivenName = "memberForm.kanjiGivenName";

    String kanaFamilyName = "memberForm.kanaFamilyName";

    String kanaGivenName = "memberForm.kanaGivenName";

    String gender = "memberForm.gender";

    String yearOfBirth = "memberForm.yearOfBirth";

    String monthOfBirth = "memberForm.monthOfBirth";

    String dayOfBirth = "memberForm.dayOfBirth";

    String tel1 = "memberForm.tel1";

    String tel2 = "memberForm.tel2";

    String tel3 = "memberForm.tel3";

    String zipCode1 = "memberForm.zipCode1";

    String zipCode2 = "memberForm.zipCode2";

    String address = "memberForm.address";

    String mail = "memberForm.mail";

    String reEnterMail = "memberForm.reEnterMail";

    String creditTypeCd = "memberForm.creditTypeCd";

    String creditNo = "memberForm.creditNo";

    String creditMonth = "memberForm.creditMonth";

    String creditYear = "memberForm.creditYear";

    String photo = "memberForm.photo";

    String nowPassword = "nowPassword";

    String updatePassword = "updatePassword";

    String reEnterPassword = "reEnterPassword";

    /**
     * フォームに顧客情報を設定する。
     * @param memberRegisterBean ユーザ情報Bean
     * @param imgPath 画像のファイルパス
     * @return UserUpdatePage ユーザ情報更新ページ
     */
    public UserUpdatePage setMemberInfo(MemberBean member, String imgPath) {

        File img = new File(imgPath);

        // 会員情報入力
        $(byId(kanjiFamilyName)).setValue(member.getKanjiFamilyName());
        $(byId(kanjiGivenName)).setValue(member.getKanjiGivenName());
        $(byId(kanaFamilyName)).setValue(member.getKanaFamilyName());
        $(byId(kanaGivenName)).setValue(member.getKanaGivenName());
        $(byName(gender)).selectRadio(member.getGender().toString());
        $(byId(yearOfBirth)).selectOption(member.getYearOfBirth().toString());
        $(byId(monthOfBirth)).selectOption(member.getMonthOfBirth().toString());
        $(byId(dayOfBirth)).selectOption(member.getDayOfBirth().toString());
        $(byId(tel1)).setValue(member.getTel1());
        $(byId(tel2)).setValue(member.getTel2());
        $(byId(tel3)).setValue(member.getTel3());
        $(byId(zipCode1)).setValue(member.getZipCode1());
        $(byId(zipCode2)).setValue(member.getZipCode2());
        $(byId(address)).setValue(member.getAddress());
        $(byId(mail)).setValue(member.getMail());
        $(byId(reEnterMail)).setValue(member.getReEnterMail());
        $(byName(creditTypeCd)).selectRadio(member.getCreditTypeCd());
        $(byId(creditNo)).setValue(member.getCreditNo());
        $(byId(creditMonth)).selectOption(member.getCreditMonth());
        $(byId(creditYear)).selectOption(member.getCreditYear());
        $(byId(photo)).uploadFile(img);

        if (member.getUpdatePassword() != null) {
            $(byId(nowPassword)).setValue(member.getPassword());
            $(byId(updatePassword)).setValue(member.getUpdatePassword());
            $(byId(reEnterPassword)).setValue(member.getReEnterPassword());
        }

        return this;
    }

    /**
     * ユーザ情報変更確認を行う。
     * @return UserUpdateConfimPage ユーザ情報変更管理ページ
     */
    public UserUpdateConfimPage toUpdateConfirmPage() {
        $(".forward").click();
        // 遷移が完了したことを確認してUserUpdateConfimPageを返す。
        $(byId("content")).waitUntil(text("変更情報確認"), 2000);
        return new UserUpdateConfimPage();
    }

}
