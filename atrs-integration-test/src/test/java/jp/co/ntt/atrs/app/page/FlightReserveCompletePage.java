/*
 * Copyright(c) 2017 NTT Corporation.
 */
package jp.co.ntt.atrs.app.page;

import static com.codeborne.selenide.Selenide.$;

public class FlightReserveCompletePage {

    /**
     * トップページを表示する。
     * @return TopPage トップページ
     */
    public TopPage toTopPage() {
        $(".forward").click();
        return new TopPage();
    }

}
