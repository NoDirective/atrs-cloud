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
package jp.co.ntt.atrs.app.common.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * ファイルアップロード必須チェック実行クラス
 * @author NTT 電電太郎
 */
public class UploadFileRequiredValidator
                                        implements
                                        ConstraintValidator<UploadFileRequired, MultipartFile> {

    /**
     * ファイルアップロード必須チェックアノテーション
     */
    private UploadFileRequired constraint;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(UploadFileRequired constraint) {
        this.constraint = constraint;
    }

    /**
     * ファイルアップロード必須チェックを行う
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
        return multipartFile != null
                && StringUtils.hasLength(multipartFile.getOriginalFilename());
    }

}
