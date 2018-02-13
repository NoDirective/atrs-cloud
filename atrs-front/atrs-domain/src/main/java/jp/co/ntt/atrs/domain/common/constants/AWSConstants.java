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
package jp.co.ntt.atrs.domain.common.constants;

/**
 * AWS関連定数クラス。
 * @author NTT 電電花子
 */
public enum AWSConstants {

    /**
     * S3プレフィックス。
     */
    S3_PROTOCOL_PREFIX("s3://");

    private final String constant;

    private AWSConstants(final String constant) {
        this.constant = constant;
    }

    public String getConstants() {
        return this.constant;
    }

}
