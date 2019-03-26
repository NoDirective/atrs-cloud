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

import java.io.File;

import jp.co.ntt.atrs.app.bean.MemberBean;

/**
 * MemberRegisterFormページのページオブジェクトクラス。
 */
public class MemberRegisterFormPage {

    /**
     * フォームに顧客情報を設定する
     * @param memberRegisterBean ユーザ情報Bean
     * @param imgPath 画像のファイルパス
     * @return MemberRegisterFormPage ユーザ情報登録ページ
     */
    public MemberRegisterFormPage setMemberInfo(MemberBean member,
            String imgPath) {

        File img = new File(imgPath);

        // 会員情報入力
        $(byId("memberForm.kanjiFamilyName")).setValue(member
                .getKanjiFamilyName());
        $(byId("memberForm.kanjiGivenName")).setValue(member
                .getKanjiGivenName());
        $(byId("memberForm.kanaFamilyName")).setValue(member
                .getKanaFamilyName());
        $(byId("memberForm.kanaGivenName")).setValue(member.getKanaGivenName());
        $(byName("memberForm.gender")).selectRadio(member.getGender()
                .toString());
        $(byId("memberForm.yearOfBirth")).selectOption(member.getYearOfBirth()
                .toString());
        $(byId("memberForm.monthOfBirth")).selectOption(member.getMonthOfBirth()
                .toString());
        $(byId("memberForm.dayOfBirth")).selectOption(member.getDayOfBirth()
                .toString());
        $(byId("memberForm.tel1")).setValue(member.getTel1());
        $(byId("memberForm.tel2")).setValue(member.getTel2());
        $(byId("memberForm.tel3")).setValue(member.getTel3());
        $(byId("memberForm.zipCode1")).setValue(member.getZipCode1());
        $(byId("memberForm.zipCode2")).setValue(member.getZipCode2());
        $(byId("memberForm.address")).setValue(member.getAddress());
        $(byId("memberForm.mail")).setValue(member.getMail());
        $(byId("memberForm.reEnterMail")).setValue(member.getReEnterMail());
        $(byName("memberForm.creditTypeCd")).selectRadio(member
                .getCreditTypeCd());
        $(byId("memberForm.creditNo")).setValue(member.getCreditNo());
        $(byId("memberForm.creditMonth")).selectOption(member.getCreditMonth());
        $(byId("memberForm.creditYear")).selectOption(member.getCreditYear());
        $(byId("memberForm.photo")).uploadFile(img);
        $(byId("registPassword")).setValue(member.getPassword());
        $(byId("reEnterPassword")).setValue(member.getReEnterPassword());
        return this;
    }

    /**
     * ユーザ情報登録確認ページを表示する。
     * @return MemberRegisterConfirmPage ユーザ情報登録確認ページ
     */
    public MemberRegisterConfirmPage toRegisterConfirmPage() {
        $(".forward").click();
        return new MemberRegisterConfirmPage();
    }
}
