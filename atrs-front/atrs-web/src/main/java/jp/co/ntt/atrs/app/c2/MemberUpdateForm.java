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
package jp.co.ntt.atrs.app.c2;

import jp.co.ntt.atrs.app.c0.MemberForm;
import jp.co.ntt.atrs.domain.common.validate.HalfWidth;

import javax.validation.Valid;
import javax.validation.constraints.Size;

/**
 * 会員情報変更フォーム。
 * @author NTT 電電花子
 */
public class MemberUpdateForm {

    /**
     * 会員情報フォーム。
     */
    @Valid
    private MemberForm memberForm;

    /**
     * パスワード。
     */
    @Size(min = 8, max = 20)
    @HalfWidth
    private String password;

    /**
     * 再入力パスワード。
     */
    @Size(min = 8, max = 20)
    @HalfWidth
    private String reEnterPassword;

    /**
     * 現在のパスワード。
     */
    @Size(min = 8, max = 20)
    @HalfWidth
    private String nowPassword;

    /**
     * <p>
     * カード会員情報を取得します。
     * </p>
     * @return カード会員情報
     */
    public MemberForm getMemberForm() {
        return memberForm;
    }

    /**
     * <p>
     * カード会員情報を設定します。
     * </p>
     * @param memberForm カード会員情報
     */
    public void setMemberForm(MemberForm memberForm) {
        this.memberForm = memberForm;
    }

    /**
     * <p>
     * パスワードを取得します。
     * </p>
     * @return パスワード
     */

    public String getPassword() {
        return password;
    }

    /**
     * <p>
     * パスワードを設定します。
     * </p>
     * @param password パスワード
     */

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * <p>
     * 再入力パスワードを取得します。
     * </p>
     * @return 再入力パスワード
     */

    public String getReEnterPassword() {
        return reEnterPassword;
    }

    /**
     * <p>
     * 再入力パスワードを設定します。
     * </p>
     * @param reEnterPassword 再入力パスワード
     */

    public void setReEnterPassword(String reEnterPassword) {
        this.reEnterPassword = reEnterPassword;
    }

    /**
     * <p>
     * 現在のパスワードを取得します。
     * </p>
     * @return 確認用パスワード
     */
    public String getNowPassword() {
        return nowPassword;
    }

    /**
     * <p>
     * 現在のパスワードを設定します。
     * </p>
     * @param nowPassword 確認用パスワード
     */
    public void setNowPassword(String nowPassword) {
        this.nowPassword = nowPassword;
    }

}
