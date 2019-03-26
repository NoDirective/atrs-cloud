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

import static com.codeborne.selenide.Selenide.$;

/**
 * MemberUpdateConfirmページのページオブジェクトクラス。
 */
public class MemberUpdateConfirmPage {

    /**
     * ユーザ情報変更を行いユーザ情報管理ページを表示する。
     * @return MemberDetailPage ユーザ情報管理ページ
     */
    public MemberDetailPage updateUser() {
        $(".forward").click();
        return new MemberDetailPage();
    }
}
