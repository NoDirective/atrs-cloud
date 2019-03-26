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
package jp.co.ntt.atrs.domain.common.masterdata;

import javax.inject.Inject;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import jp.co.ntt.atrs.domain.common.masterdata.helper.FlightMasterHelper;
import jp.co.ntt.atrs.domain.model.FlightMaster;

/**
 * フライト基本情報を提供するクラス。
 * @author NTT 電電太郎
 */
@Component
public class FlightMasterProvider {

    private static final String CACHE_KEY = "flightMaster";

    /**
     * フライト基本情報ヘルパ。
     */
    @Inject
    FlightMasterHelper flightMasterHelper;

    /**
     * 指定便名に該当するフライト基本情報を取得する。
     * @param flightName 便名
     * @return フライト基本情報。該当するフライト基本情報がない場合はnull。
     */
    public FlightMaster getFlightMaster(String flightName) {
        Assert.hasText(flightName, "flightName must not be empty");
        return this.flightMasterHelper.findAll(CACHE_KEY).get(flightName);
    }

    /**
     * キャッシュのリフレッシュ
     */
    public void cacheRefresh() {
        flightMasterHelper.refresh(CACHE_KEY);
    }
}
