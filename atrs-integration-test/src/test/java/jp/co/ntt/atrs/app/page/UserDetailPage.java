/*
 * Copyright(c) 2017 NTT Corporation.
 */
package jp.co.ntt.atrs.app.page;

import static com.codeborne.selenide.Selenide.$;

public class UserDetailPage {

    /**
     * ユーザ情報登録完了画面を表示する。
     * @return UserUpdatePage ユーザ情報登録完了ページ
     */
    public UserUpdatePage toUserUpdatePage() {
        $(".forwardx1_5").click();
        return new UserUpdatePage();
    }
}
