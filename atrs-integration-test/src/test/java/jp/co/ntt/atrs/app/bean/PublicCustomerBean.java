/*
 * Copyright 2014-2017 NTT Corporation.
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
package jp.co.ntt.atrs.app.bean;

import jp.co.ntt.atrs.domain.model.Gender;

/**
 * 会員情報フォーム。
 * @author NTT 電電花子
 */
public class PublicCustomerBean {

    /**
     * カナ姓。
     */
    private String kanaFamilyName;

    /**
     * カナ名。
     */
    private String kanaGivenName;

    /**
     * 年齢。
     */
    private String age;

    /**
     * 性別。
     */
    private Gender gender;

    /**
     * 市外局番。
     */
    private String tel1;

    /**
     * 市内局番。
     */
    private String tel2;

    /**
     * 加入者番号。
     */
    private String tel3;

    /**
     * eメールアドレス。
     */
    private String mail;

    /**
     * カナ姓2。
     */
    private String kanaFamilyName2;

    /**
     * カナ名2。
     */
    private String kanaGivenName2;

    /**
     * 年齢2。
     */
    private String age2;

    /**
     * 性別2。
     */
    private Gender gender2;

    /**
     * <p>
     * カナ姓を取得します。
     * </p>
     * @return カナ姓
     */

    public String getKanaFamilyName() {
        return kanaFamilyName;
    }

    /**
     * <p>
     * カナ姓を設定します。
     * </p>
     * @param kanaFamilyName カナ姓
     */

    public void setKanaFamilyName(String kanaFamilyName) {
        this.kanaFamilyName = kanaFamilyName;
    }

    /**
     * <p>
     * カナ名を取得します。
     * </p>
     * @return カナ名
     */

    public String getKanaGivenName() {
        return kanaGivenName;
    }

    /**
     * <p>
     * カナ名を設定します。<
     * </p>
     * @param kanaGivenName カナ名
     */

    public void setKanaGivenName(String kanaGivenName) {
        this.kanaGivenName = kanaGivenName;
    }

    /**
     * <p>
     * 年齢を取得します。
     * </p>
     * @return 年齢
     */

    public String getAge() {
        return age;
    }

    /**
     * <p>
     * 年齢を設定します。<
     * </p>
     * @param age 年齢
     */

    public void setAge(String age) {
        this.age = age;
    }

    /**
     * <p>
     * 性別を取得します。
     * </p>
     * @return 性別
     */

    public Gender getGender() {
        return gender;
    }

    /**
     * <p>
     * 性別を設定します。
     * </p>
     * @param gender 性別
     */

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * <p>
     * 市外局番を取得します。
     * </p>
     * @return 市外局番
     */

    public String getTel1() {
        return tel1;
    }

    /**
     * <p>
     * 市外局番を設定します。
     * </p>
     * @param tel1 市外局番
     */

    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }

    /**
     * <p>
     * 市内局番を取得します。
     * </p>
     * @return 市内局番
     */

    public String getTel2() {
        return tel2;
    }

    /**
     * <p>
     * 市内局番を設定します。
     * </p>
     * @param tel2 市内局番
     */

    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    /**
     * <p>
     * 加入者番号を取得します。
     * </p>
     * @return 加入者番号
     */

    public String getTel3() {
        return tel3;
    }

    /**
     * <p>
     * 加入者番号を設定します。
     * </p>
     * @param tel3 加入者番号
     */

    public void setTel3(String tel3) {
        this.tel3 = tel3;
    }

    /**
     * <p>
     * 郵便番号（前半3桁）を取得します。
     * </p>
     * @return 郵便番号（前半3桁）
     */

    /**
     * <p>
     * eメールアドレスを取得します。
     * </p>
     * @return eメールアドレス
     */

    public String getMail() {
        return mail;
    }

    /**
     * <p>
     * eメールアドレスを設定します。
     * </p>
     * @param mail eメールアドレス
     */

    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * <p>
     * カナ姓2を取得します。
     * </p>
     * @return カナ姓2
     */

    public String getKanaFamilyName2() {
        return kanaFamilyName2;
    }

    /**
     * <p>
     * カナ姓を設定します。
     * </p>
     * @param kanaFamilyName2 カナ姓2
     */

    public void setKanaFamilyName2(String kanaFamilyName2) {
        this.kanaFamilyName2 = kanaFamilyName2;
    }

    /**
     * <p>
     * カナ名2を取得します。
     * </p>
     * @return カナ名2
     */

    public String getKanaGivenName2() {
        return kanaGivenName2;
    }

    /**
     * <p>
     * カナ名2を設定します。<
     * </p>
     * @param kanaGivenName カナ名2
     */

    public void setKanaGivenName2(String kanaGivenName2) {
        this.kanaGivenName2 = kanaGivenName2;
    }

    /**
     * <p>
     * 年齢2を取得します。
     * </p>
     * @return 年齢2
     */

    public String getAge2() {
        return age2;
    }

    /**
     * <p>
     * 年齢2を設定します。<
     * </p>
     * @param age2 年齢2
     */

    public void setAge2(String age2) {
        this.age2 = age2;
    }

    /**
     * <p>
     * 性別を取得します。
     * </p>
     * @return 性別2
     */

    public Gender getGender2() {
        return gender2;
    }

    /**
     * <p>
     * 性別を設定します。
     * </p>
     * @param gender2 性別2
     */

    public void setGender2(Gender gender2) {
        this.gender2 = gender2;
    }
}
