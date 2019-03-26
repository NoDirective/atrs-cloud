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
package jp.co.ntt.atrs.app.b2;

import jp.co.ntt.atrs.app.b0.FlightSearchCriteriaForm;
import jp.co.ntt.atrs.app.b0.SelectFlightForm;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

/**
 * チケット予約用選択フライト情報フォーム。
 * <p>
 * 以下のフォームを内包する。
 * </p>
 * <ul>
 * <li>往路の空席照会条件フォーム</li>
 * <li>選択フライト情報フォームのリスト</li>
 * </ul>
 * @author NTT 電電次郎
 */
public class TicketReserveSelectFlightForm implements Serializable {

    /**
     * シリアルバージョンUID。
     */
    private static final long serialVersionUID = -3687142840637894483L;

    /**
     * 往路の空席照会条件フォーム。
     */
    @Valid
    private FlightSearchCriteriaForm outwardLineSearchCriteriaForm;

    /**
     * 選択フライト情報フォームのリスト。
     */
    @Valid
    private List<SelectFlightForm> selectFlightFormList;

    /**
     * コンストラクタ。
     */
    public TicketReserveSelectFlightForm() {
        super();
        outwardLineSearchCriteriaForm = new FlightSearchCriteriaForm();
        selectFlightFormList = new ArrayList<>();
    }

    /**
     * 往路の空席照会条件フォーム を取得する。
     * @return 往路の空席照会条件フォーム
     */
    public FlightSearchCriteriaForm getOutwardLineSearchCriteriaForm() {
        return outwardLineSearchCriteriaForm;
    }

    /**
     * 往路の空席照会条件フォームを設定する。
     * @param outwardLineSearchCriteriaForm 往路の空席照会条件フォーム
     */
    public void setOutwardLineSearchCriteriaForm(
            FlightSearchCriteriaForm outwardLineSearchCriteriaForm) {
        this.outwardLineSearchCriteriaForm = outwardLineSearchCriteriaForm;
    }

    /**
     * 選択フライト情報フォームのリストを取得する。
     * @return 選択したフライトの情報リスト
     */
    public List<SelectFlightForm> getSelectFlightFormList() {
        return selectFlightFormList;
    }

    /**
     * 選択フライト情報フォームのリストを設定する。
     * @param selectFlightFormList 選択フライト情報フォームのリスト
     */
    public void setSelectFlightFormList(
            List<SelectFlightForm> selectFlightFormList) {
        this.selectFlightFormList = selectFlightFormList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.MULTI_LINE_STYLE);
    }
}
