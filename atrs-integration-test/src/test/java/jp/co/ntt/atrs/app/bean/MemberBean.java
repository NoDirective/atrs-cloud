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
package jp.co.ntt.atrs.app.bean;

import jp.co.ntt.atrs.domain.model.Gender;

/**
 * 会員情報フォーム。
 * @author NTT 電電花子
 */
public class MemberBean {

    /**
     * 顧客番号。
     */
    private String userId;

    /**
     * パスワード。
     */
    private String password;

    /**
     * 再入力パスワード。
     */
    private String reEnterPassword;

    /**
     * 新パスワード。
     */
    private String updatePassword;

    /**
     * 漢字姓。
     */
    private String kanjiFamilyName;

    /**
     * 漢字名。
     */
    private String kanjiGivenName;

    /**
     * カナ姓。
     */
    private String kanaFamilyName;

    /**
     * カナ名。
     */
    private String kanaGivenName;

    /**
     * 性別。
     */
    private Gender gender;

    /**
     * 生年月日（年）。
     */
    private Integer yearOfBirth;

    /**
     * 生年月日（月）。
     */
    private Integer monthOfBirth;

    /**
     * 生年月日（日）。
     */
    private Integer dayOfBirth;

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
     * 郵便番号（前半3桁）。
     */
    private String zipCode1;

    /**
     * 郵便番号（後半4桁）。
     */
    private String zipCode2;

    /**
     * 住所。
     */
    private String address;

    /**
     * eメールアドレス。
     */
    private String mail;

    /**
     * 再入力eメールアドレス。
     */
    private String reEnterMail;

    /**
     * クレジットカード種別。
     */
    private String creditTypeCd;

    /**
     * クレジットカード番号。
     */
    private String creditNo;

    /**
     * クレジットカード有効期限（月）。
     */
    private String creditMonth;

    /**
     * クレジットカード有効期限（年）。
     */
    private String creditYear;

    /**
     * <p>
     * 顧客番号を取得します。
     * </p>
     * @return 顧客番号
     */

    public String getUserId() {
        return userId;
    }

    /**
     * <p>
     * 顧客番号を設定します。
     * </p>
     * @param userId 顧客番号
     */

    public void setUserId(String userId) {
        this.userId = userId;
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
     * 新パスワードを取得します。
     * </p>
     * @return 新パスワード
     */

    public String getUpdatePassword() {
        return updatePassword;
    }

    /**
     * <p>
     * 新パスワードを設定します。
     * </p>
     * @param updatePassword 新パスワード
     */

    public void setupdatePassword(String updatePassword) {
        this.updatePassword = updatePassword;
    }

    /**
     * <p>
     * 漢字姓を取得します。
     * </p>
     * @return 漢字姓
     */

    public String getKanjiFamilyName() {
        return kanjiFamilyName;
    }

    /**
     * <p>
     * 漢字姓を設定します。
     * </p>
     * @param kanjiFamilyName 漢字姓
     */

    public void setKanjiFamilyName(String kanjiFamilyName) {
        this.kanjiFamilyName = kanjiFamilyName;
    }

    /**
     * <p>
     * 漢字名を取得します。
     * </p>
     * @return 漢字名
     */

    public String getKanjiGivenName() {
        return kanjiGivenName;
    }

    /**
     * <p>
     * 漢字名を設定します。
     * </p>
     * @param kanjiGivenName 漢字名
     */

    public void setKanjiGivenName(String kanjiGivenName) {
        this.kanjiGivenName = kanjiGivenName;
    }

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
     * 生年月日（年）を取得します。<
     * </p>
     * @return 生年月日（年）
     */

    public Integer getYearOfBirth() {
        return yearOfBirth;
    }

    /**
     * <p>
     * 生年月日（年）を設定します。
     * </p>
     * @param yearOfBirth 生年月日（年）
     */

    public void setYearOfBirth(Integer yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    /**
     * <p>
     * 生年月日（月）を取得します。
     * </p>
     * @return 生年月日（月）
     */

    public Integer getMonthOfBirth() {
        return monthOfBirth;
    }

    /**
     * <p>
     * 生年月日（月）を設定します。
     * </p>
     * @param monthOfBirth 生年月日（月）
     */

    public void setMonthOfBirth(Integer monthOfBirth) {
        this.monthOfBirth = monthOfBirth;
    }

    /**
     * <p>
     * 生年月日（日）を取得します。
     * </p>
     * @return 生年月日（日）
     */

    public Integer getDayOfBirth() {
        return dayOfBirth;
    }

    /**
     * <p>
     * 生年月日（日）を設定します。
     * </p>
     * @param dayOfBirth 生年月日（日）
     */

    public void setDayOfBirth(Integer dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
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

    public String getZipCode1() {
        return zipCode1;
    }

    /**
     * <p>
     * 郵便番号（前半3桁）を設定します。
     * </p>
     * @param zipCode1 郵便番号（前半3桁）
     */

    public void setZipCode1(String zipCode1) {
        this.zipCode1 = zipCode1;
    }

    /**
     * <p>
     * 郵便番号（後半4桁）を取得します。
     * </p>
     * @return 郵便番号（後半4桁）
     */

    public String getZipCode2() {
        return zipCode2;
    }

    /**
     * <p>
     * 郵便番号（後半4桁）を設定します。
     * </p>
     * @param zipCode2 郵便番号（後半4桁）
     */

    public void setZipCode2(String zipCode2) {
        this.zipCode2 = zipCode2;
    }

    /**
     * <p>
     * 住所を取得します。
     * </p>
     * @return 住所
     */

    public String getAddress() {
        return address;
    }

    /**
     * <p>
     * 住所を設定します。
     * </p>
     * @param address 住所
     */

    public void setAddress(String address) {
        this.address = address;
    }

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
     * 再入力eメールアドレスを取得します。
     * </p>
     * @return 再入力eメールアドレス
     */

    public String getReEnterMail() {
        return reEnterMail;
    }

    /**
     * <p>
     * 再入力eメールアドレスを設定します。
     * </p>
     * @param reEnterMail 再入力eメールアドレス
     */

    public void setReEnterMail(String reEnterMail) {
        this.reEnterMail = reEnterMail;
    }

    /**
     * <p>
     * クレジットカード種別を取得します。
     * </p>
     * @return クレジットカード種別
     */

    public String getCreditTypeCd() {
        return creditTypeCd;
    }

    /**
     * <p>
     * クレジットカード種別を設定します。
     * </p>
     * @param creditTypeCd クレジットカード種別
     */

    public void setCreditTypeCd(String creditTypeCd) {
        this.creditTypeCd = creditTypeCd;
    }

    /**
     * <p>
     * クレジットカード番号を取得します。
     * </p>
     * @return クレジットカード番号
     */

    public String getCreditNo() {
        return creditNo;
    }

    /**
     * <p>
     * クレジットカード番号を設定します。
     * </p>
     * @param creditNo クレジットカード番号
     */

    public void setCreditNo(String creditNo) {
        this.creditNo = creditNo;
    }

    /**
     * <p>
     * クレジットカード有効期限（月）を取得します。
     * </p>
     * @return クレジットカード有効期限（月）
     */

    public String getCreditMonth() {
        return creditMonth;
    }

    /**
     * <p>
     * クレジットカード有効期限（月）を設定します。
     * </p>
     * @param creditMonth クレジットカード有効期限（月）
     */

    public void setCreditMonth(String creditMonth) {
        this.creditMonth = creditMonth;
    }

    /**
     * <p>
     * クレジットカード有効期限（年）を取得します。
     * </p>
     * @return クレジットカード有効期限（年）
     */

    public String getCreditYear() {
        return creditYear;
    }

    /**
     * <p>
     * クレジットカード有効期限（年）を設定します。
     * </p>
     * @param creditYear クレジットカード有効期限（年）
     */

    public void setCreditYear(String creditYear) {
        this.creditYear = creditYear;
    }


}
