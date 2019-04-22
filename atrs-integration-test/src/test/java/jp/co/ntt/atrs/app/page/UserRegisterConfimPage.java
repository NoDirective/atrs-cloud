/*
 * Copyright(c) 2017 NTT Corporation.
 */
package jp.co.ntt.atrs.app.page;

import static com.codeborne.selenide.Selenide.$;

public class UserRegisterConfimPage {

    /**
     * ユーザ情報登録を行う。
     * @return UserRegisterCompletePage ユーザ情報登録完了ページ
     */
    public UserRegisterCompletePage registerUser() {
        $(".forward").click();
        return new UserRegisterCompletePage();
    }
}
