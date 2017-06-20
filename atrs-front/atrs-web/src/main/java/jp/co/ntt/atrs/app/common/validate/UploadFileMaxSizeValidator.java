/*
 * Copyright(c) 2017 NTT Corporation.
 */
package jp.co.ntt.atrs.app.common.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

/**
 * ファイル最大サイズチェックアノテーション実装クラス
 * @author NTT 電電太郎
 */
public class UploadFileMaxSizeValidator
                                       implements
                                       ConstraintValidator<UploadFileMaxSize, MultipartFile> {

    /**
     * ファイル最大サイズチェックアノテーション
     */
    private UploadFileMaxSize constraint;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(UploadFileMaxSize constraint) {
        this.constraint = constraint;
    }

    /**
     * 最大サイズチェックを行う
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
        if (constraint.value() < 0 || multipartFile == null) {
            return true;
        }
        return multipartFile.getSize() <= constraint.value();
    }

}
