/*
 * Copyright(c) 2017 NTT Corporation.
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
 */
package jp.co.ntt.atrs.domain.common.masterdata.helper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ntt.atrs.domain.model.Route;
import jp.co.ntt.atrs.domain.repository.route.RouteRepository;

/**
 * 区間情報のヘルパクラス。
 * @author NTT 電電太郎
 */
@Component
@CacheConfig(cacheNames = "route")
public class RouteHelper {

    /**
     * 区間情報リポジトリ。
     */
    @Inject
    RouteRepository routeRepository;

    /**
     * 区間情報をロードし、キャッシュする。
     * @param key
     * @return
     */
    @Transactional(readOnly = true)
    @Cacheable(key = "'route/' + #key")
    public Map<String, Route> findAll(String key) {
        Map<String, Route> routeMap = new HashMap<>();
        List<Route> routeList = routeRepository.findAll();
        for (Route route : routeList) {
            String cacheKey = makeCacheKey(route.getDepartureAirport()
                    .getCode(), route.getArrivalAirport().getCode());
            routeMap.put(cacheKey, route);
        }
        return routeMap;
    }

    /**
     * キャッシュのリフレッシュ
     * @param key
     */
    @CacheEvict(key = "'route/' + #key")
    public void refresh(String key) {

    }

    /**
     * 区間情報マップにキャッシュするためのキー値を生成する。
     * <p>
     * キー値は、"[出発空港コード]-[到着空港コード]"形式の文字列となる。
     * </p>
     * @param departureAirportCd 出発空港コード
     * @param arrivalAirportCd 到着空港コード
     * @return 区間情報マップにキャッシュするためのキー値
     */
    public String makeCacheKey(String departureAirportCd,
            String arrivalAirportCd) {
        return departureAirportCd + "-" + arrivalAirportCd;
    }
}
