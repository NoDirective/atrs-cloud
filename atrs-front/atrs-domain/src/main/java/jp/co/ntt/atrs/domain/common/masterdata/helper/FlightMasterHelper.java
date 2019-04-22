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

import jp.co.ntt.atrs.domain.model.FlightMaster;
import jp.co.ntt.atrs.domain.repository.flight.FlightRepository;

/**
 * フライト基本情報のヘルパクラス。
 * @author NTT 電電太郎
 */
@Component
@CacheConfig(cacheNames = "FlightMaster")
public class FlightMasterHelper {

    /**
     * フライト基本情報リポジトリ。
     */
    @Inject
    FlightRepository flightRepository;

    /**
     * フライト基本情報をロードし、キャッシュする。
     * @param key
     * @return
     */
    @Transactional(readOnly = true)
    @Cacheable(key = "'flightMaster/' + #key")
    public Map<String, FlightMaster> findAll(String key) {
        Map<String, FlightMaster> flightMasterMap = new HashMap<>();
        List<FlightMaster> flightMasterList = flightRepository
                .findAllFlightMaster();
        for (FlightMaster flightMaster : flightMasterList) {
            flightMasterMap.put(flightMaster.getFlightName(), flightMaster);
        }
        return flightMasterMap;
    }

    /**
     * キャッシュのリフレッシュ
     * @param key
     */
    @CacheEvict(key = "'flightMaster/' + #key")
    public void refresh(String key) {

    }

}
