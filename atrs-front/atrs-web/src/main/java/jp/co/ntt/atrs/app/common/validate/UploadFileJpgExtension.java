/*
 * Copyright(c) 2017 NTT Corporation.
 */
package jp.co.ntt.atrs.app.common.validate;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * jpg拡張子チェックアノテーション
 * @author NTT 電電太郎
 */
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UploadFileJpgExtensionValidator.class)
public @interface UploadFileJpgExtension {
    String message() default "{file.upload.extension.jpg}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean check() default true;

    /**
     * 複数設定用リスト
     */
    @Target({ ElementType.METHOD, ElementType.FIELD,
            ElementType.ANNOTATION_TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        UploadFileJpgExtension[] value();
    }

}
