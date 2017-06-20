/*
 * Copyright(c) 2017 NTT Corporation.
 */
package jp.co.ntt.atrs.app.common.logging;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.MDC;

import jp.co.ntt.atrs.domain.model.AtrsMessage;

/**
 * メッセージIDをログ出力する為のインタセプタ。<br>
 * @author NTT 電電太郎
 */
public class MessageIdLoggingInterceptor implements MethodInterceptor {

    /**
     * MDCに設定する属性名
     */
    private String attributeName = "messageId";

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        // 引数からメッセージオブジェクトを取得
        Object[] arguments = invocation.getArguments();
        if (arguments != null && arguments.length != 0
                && arguments[0] instanceof AtrsMessage) {
            AtrsMessage message = (AtrsMessage) arguments[0];
            // メッセージIDをMDCにput
            String messageId = message.getMessageId();
            MDC.put(this.attributeName, messageId);
        }

        try {
            return invocation.proceed();
        } finally {
            MDC.remove(this.attributeName);
        }
    }

    /**
     * 属性名を設定する。
     * @param attributeName 属性名
     */
    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

}
