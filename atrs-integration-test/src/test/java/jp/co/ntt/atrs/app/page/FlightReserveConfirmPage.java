/*
 * Copyright(c) 2017 NTT Corporation.
 */
package jp.co.ntt.atrs.app.page;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selectors.*;

public class FlightReserveConfirmPage {

    /**
     * フライト予約完了ページを表示する。
     * @return FlightReserveCompletePage フライト予約完了ページ
     */
    public FlightReserveCompletePage toFlightReserveCompletePage() {
        $(".forwardx1_5").click();
        // 遷移が完了したことを確認してPageを返す。
        $(byId("content")).waitUntil(text("予約申込完了"), 2000);
        return new FlightReserveCompletePage();
    }

}
