/*
 * Copyright(c) 2017 NTT Corporation.
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
