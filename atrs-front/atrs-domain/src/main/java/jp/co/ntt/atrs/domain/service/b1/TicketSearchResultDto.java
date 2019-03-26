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
package jp.co.ntt.atrs.domain.service.b1;

import jp.co.ntt.atrs.domain.model.FareTypeCd;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 空席照会の検索結果を保持するクラス。
 * @author NTT 電電次郎
 */
public class TicketSearchResultDto {

    /**
     * 空席照会結果に含める運賃種別リスト。
     */
    private final List<FareTypeCd> fareTypeList;

    /**
     * 空席照会結果情報リスト。
     */
    private final Page<FlightVacantInfoDto> flightVacantInfoPage;

    /**
     * コンストラクタ。
     * @param fareTypeList 空席照会結果に含める運賃種別リスト
     * @param flightVacantInfoPage 空席照会結果情報リスト
     */
    TicketSearchResultDto(List<FareTypeCd> fareTypeList,
            Page<FlightVacantInfoDto> flightVacantInfoPage) {
        this.fareTypeList = fareTypeList;
        this.flightVacantInfoPage = flightVacantInfoPage;
    }

    /**
     * 空席照会結果に含める運賃種別リストを取得する。
     * @return 運賃種別リスト
     */
    public List<FareTypeCd> getFareTypeList() {
        return fareTypeList;
    }

    /**
     * 空席照会結果情報を取得する。
     * @return 空席照会結果情報Pageオブジェクト
     */
    public Page<FlightVacantInfoDto> getFlightVacantInfoPage() {
        return flightVacantInfoPage;
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
