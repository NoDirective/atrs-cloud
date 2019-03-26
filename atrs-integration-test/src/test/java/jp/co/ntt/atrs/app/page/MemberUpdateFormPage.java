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
import static com.codeborne.selenide.Selenide.$;

import java.io.File;

import jp.co.ntt.atrs.app.bean.MemberBean;

/**
 * MemberUpdateFormページのページオブジェクトクラス。
 */
public class MemberUpdateFormPage {

    /**
     * フォームに顧客情報を設定する。
     * @param memberRegisterBean ユーザ情報Bean
     * @param imgPath 画像のファイルパス
     * @return MemberUpdateFormPage ユーザ情報更新ページ
     */
    public MemberUpdateFormPage setMemberInfo(MemberBean member,
            String imgPath) {

        File img = new File(imgPath);

        // 会員情報入力
        $(byId("memberForm.tel1")).setValue(member.getTel1());
        $(byId("memberForm.tel2")).setValue(member.getTel2());
        $(byId("memberForm.tel3")).setValue(member.getTel3());
        $(byId("memberForm.reEnterMail")).setValue(member.getReEnterMail());
        $(byId("memberForm.photo")).uploadFile(img);

        if (member.getUpdatePassword() != null) {
            $(byId("nowPassword")).setValue(member.getPassword());
            $(byId("updatePassword")).setValue(member.getUpdatePassword());
            $(byId("reEnterPassword")).setValue(member.getReEnterPassword());
        }
        return this;
    }

    /**
     * ユーザ情報変更確認ページを表示する。
     * @return MemberUpdateConfirmPage ユーザ情報変更確認ページ
     */
    public MemberUpdateConfirmPage toUpdateConfirmPage() {
        $(".forward").click();
        return new MemberUpdateConfirmPage();
    }

}
