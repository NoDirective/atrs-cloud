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
package jp.co.ntt.atrs.app.c0;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;
import org.terasoluna.gfw.common.codelist.ExistInCodeList;

import jp.co.ntt.atrs.app.common.validate.UploadFileJpgExtension;
import jp.co.ntt.atrs.app.common.validate.UploadFileMaxSize;
import jp.co.ntt.atrs.app.common.validate.UploadFileNotEmpty;
import jp.co.ntt.atrs.app.common.validate.UploadFileRequired;
import jp.co.ntt.atrs.domain.common.validate.FixedLength;
import jp.co.ntt.atrs.domain.common.validate.FullWidth;
import jp.co.ntt.atrs.domain.common.validate.FullWidthKatakana;
import jp.co.ntt.atrs.domain.common.validate.HalfWidth;
import jp.co.ntt.atrs.domain.common.validate.HalfWidthNumber;
import jp.co.ntt.atrs.domain.model.Gender;

/**
 * 会員情報フォーム。
 * @author NTT 電電花子
 */
public class MemberForm {

    /**
     * ファイルアップロードチェックグループ。
     * @author NTT 電電花子
     */
    public static interface UploadFileCheck {
    };

    /**
     * ファイルアップロードチェック不要グループ。
     * @author NTT 電電花子
     */
    public static interface UploadFileUncheck {
    };

    /**
     * ファイルアップロード必須チェック不要グループ。
     * @author NTT 電電花子
     */
    public static interface UploadFileNotRequired {
    };

    /**
     * 漢字姓。
     */
    @NotNull
    @FullWidth
    @Size(min = 1, max = 10)
    private String kanjiFamilyName;

    /**
     * 漢字名。
     */
    @NotNull
    @FullWidth
    @Size(min = 1, max = 10)
    private String kanjiGivenName;

    /**
     * カナ姓。
     */
    @NotNull
    @FullWidthKatakana
    @Size(min = 1, max = 10)
    private String kanaFamilyName;

    /**
     * カナ名。
     */
    @NotNull
    @FullWidthKatakana
    @Size(min = 1, max = 10)
    private String kanaGivenName;

    /**
     * 性別。
     */
    @NotNull
    private Gender gender;

    /**
     * 生年月日（年）。
     */
    @NotNull
    @Min(1900)
    private Integer yearOfBirth;

    /**
     * 生年月日（月）。
     */
    @NotNull
    @Min(1)
    @Max(12)
    private Integer monthOfBirth;

    /**
     * 生年月日（日）。
     */
    @NotNull
    @Min(1)
    @Max(31)
    private Integer dayOfBirth;

    /**
     * 市外局番。
     */
    @NotNull
    @HalfWidthNumber
    @Size(min = 2, max = 5)
    private String tel1;

    /**
     * 市内局番。
     */
    @NotNull
    @HalfWidthNumber
    @Size(min = 1, max = 4)
    private String tel2;

    /**
     * 加入者番号。
     */
    @NotNull
    @HalfWidthNumber
    @FixedLength(4)
    private String tel3;

    /**
     * 郵便番号（前半3桁）。
     */
    @NotNull
    @HalfWidthNumber
    @FixedLength(3)
    private String zipCode1;

    /**
     * 郵便番号（後半4桁）。
     */
    @NotNull
    @HalfWidthNumber
    @FixedLength(4)
    private String zipCode2;

    /**
     * 住所。
     */
    @NotNull
    @FullWidth
    @Size(min = 1, max = 60)
    private String address;

    /**
     * eメールアドレス。
     */
    @NotNull
    @Email
    @Size(min = 1, max = 256)
    @HalfWidth
    private String mail;

    /**
     * 再入力eメールアドレス。
     */
    @NotNull
    @Email
    @Size(min = 1, max = 256)
    @HalfWidth
    private String reEnterMail;

    /**
     * クレジットカード種別。
     */
    @NotNull
    @ExistInCodeList(codeListId = "CL_CREDITTYPE")
    private String creditTypeCd;

    /**
     * クレジットカード番号。
     */
    @NotNull
    @FixedLength(16)
    @HalfWidthNumber
    private String creditNo;

    /**
     * クレジットカード有効期限（月）。
     */
    @NotNull
    @Min(1)
    @Max(12)
    private String creditMonth;

    /**
     * クレジットカード有効期限（年）。
     */
    @NotNull
    @Min(00)
    private String creditYear;

    /**
     * 顔写真。
     */
    @UploadFileRequired.List({
            @UploadFileRequired(check = true, groups = UploadFileCheck.class),
            @UploadFileRequired(check = false, groups = UploadFileUncheck.class),
            @UploadFileRequired(check = false, groups = UploadFileNotRequired.class) })
    @UploadFileNotEmpty.List({
            @UploadFileNotEmpty(check = true, groups = UploadFileCheck.class),
            @UploadFileNotEmpty(check = false, groups = UploadFileUncheck.class),
            @UploadFileNotEmpty(check = true, groups = UploadFileNotRequired.class) })
    @UploadFileMaxSize.List({
            @UploadFileMaxSize(check = true, groups = UploadFileCheck.class),
            @UploadFileMaxSize(check = false, groups = UploadFileUncheck.class),
            @UploadFileMaxSize(check = true, groups = UploadFileNotRequired.class) })
    @UploadFileJpgExtension.List({
            @UploadFileJpgExtension(check = true, groups = UploadFileCheck.class),
            @UploadFileJpgExtension(check = false, groups = UploadFileUncheck.class),
            @UploadFileJpgExtension(check = true, groups = UploadFileNotRequired.class) })
    private MultipartFile photo;

    /**
     * 顔写真(Base64エンコード)。
     */
    private String photoBase64;

    /**
     * 顔写真ファイル名。
     */
    private String photoFileName;

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

    /**
     * <p>
     * 顔写真を取得します。
     * </p>
     * @return photo 顔写真
     */

    public MultipartFile getPhoto() {
        return photo;
    }

    /**
     * <p>
     * 顔写真を設定します。
     * </p>
     * @param photo 顔写真
     */

    public void setPhoto(MultipartFile photo) {
        this.photo = photo;
    }

    /**
     * <p>
     * 顔写真(Base64エンコード)を取得します。
     * </p>
     * @return photoBase64 顔写真(Base64エンコード)
     */

    public String getPhotoBase64() {
        return photoBase64;
    }

    /**
     * <p>
     * 顔写真(Base64エンコード)を設定します。
     * </p>
     * @param photoBase64 顔写真(Base64エンコード)
     */

    public void setPhotoBase64(String photoBase64) {
        this.photoBase64 = photoBase64;
    }

    /**
     * <p>
     * 顔写真ファイル名を取得します。
     * </p>
     * @return photoFileName 顔写真ファイル名
     */

    public String getPhotoFileName() {
        return photoFileName;
    }

    /**
     * <p>
     * 顔写真ファイル名を設定します。
     * </p>
     * @param photoFileName 顔写真ファイル名
     */

    public void setPhotoFileName(String photoFileName) {
        this.photoFileName = photoFileName;
    }

}
