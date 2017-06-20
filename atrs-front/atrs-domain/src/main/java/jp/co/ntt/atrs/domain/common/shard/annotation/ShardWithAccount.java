/*
 * Copyright(c) 2017 NTT Corporation.
 */
package jp.co.ntt.atrs.domain.common.shard.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * シャーディングされたDBにアクセスするメソッドへ付与するアノテーション。
 * @author NTT 電電太郎
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ShardWithAccount {

    /**
     * この値は、メソッド引数に設定されたシャードアカウントIDのプロパティを指定する。
     * @return
     */
    String value() default "";
}
