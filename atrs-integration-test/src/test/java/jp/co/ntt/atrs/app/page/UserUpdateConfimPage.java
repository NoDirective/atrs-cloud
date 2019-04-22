/*
 * Copyright(c) 2017 NTT Corporation.
 */
package jp.co.ntt.atrs.app.page;

import static com.codeborne.selenide.Selenide.$;

public class UserUpdateConfimPage {

    /**
     * ユーザ情報変更を行う。
     * @return UserDetailPage ユーザ情報詳細ページ
     */
    public UserDetailPage updateUser() {
        $(".forward").click();
        return new UserDetailPage();
    }
}
