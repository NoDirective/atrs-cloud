/*
 * Copyright(c) 2017 NTT Corporation.
 */
package jp.co.ntt.atrs.app.common.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * 空ファイルチェックアノテーション実装クラス
 * @author NTT 電電太郎
 */
public class UploadFileNotEmptyValidator
                                        implements
                                        ConstraintValidator<UploadFileNotEmpty, MultipartFile> {

    /**
     * 空ファイルチェックアノテーション
     */
    private UploadFileNotEmpty constraint;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(UploadFileNotEmpty constraint) {
        this.constraint = constraint;
    }

    /**
     * 空ファイルチェックを行う
     * @param multipartFile アップロードファイル
     * @param context バリデータコンテキスト
     * @return チェック結果
     */
    @Override
    public boolean isValid(MultipartFile multipartFile,
            ConstraintValidatorContext context) {
        if (!constraint.check()) {
            return true;
        }
        if (multipartFile == null
                || !StringUtils.hasLength(multipartFile.getOriginalFilename())) {
            return true;
        }
        return !multipartFile.isEmpty();
    }

}
