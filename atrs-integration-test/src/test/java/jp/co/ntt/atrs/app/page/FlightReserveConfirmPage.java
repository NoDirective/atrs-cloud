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
