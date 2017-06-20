/*
 * Copyright(c) 2017 NTT Corporation.
 */
package jp.co.ntt.atrs.app.common.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.io.FilenameUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * jpg拡張子チェックアノテーション実装クラス
 * @author NTT 電電太郎
 */
public class UploadFileJpgExtensionValidator
                                            implements
                                            ConstraintValidator<UploadFileJpgExtension, MultipartFile> {

    /**
     * jpg拡張子チェックアノテーション
     */
    private UploadFileJpgExtension constraint;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(UploadFileJpgExtension constraint) {
        this.constraint = constraint;
    }

    /**
     * jpg拡張子チェックを行う
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
        return "jpg".equals(FilenameUtils.getExtension(multipartFile
                .getOriginalFilename()));
    }

}
