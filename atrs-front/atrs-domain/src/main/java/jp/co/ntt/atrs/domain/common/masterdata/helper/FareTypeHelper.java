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

import jp.co.ntt.atrs.domain.model.FareType;
import jp.co.ntt.atrs.domain.model.FareTypeCd;
import jp.co.ntt.atrs.domain.repository.faretype.FareTypeRepository;

/**
 * 運賃種別情報のヘルパクラス。
 * @author NTT 電電太郎
 */
@Component
@CacheConfig(cacheNames = "fareType")
public class FareTypeHelper {

    /**
     * 運賃種別情報リポジトリ。
     */
    @Inject
    FareTypeRepository fareTypeRepository;

    /**
     * 運賃種別情報をロードし、キャッシュする。
     * @param key
     * @return
     */
    @Transactional(readOnly = true)
    @Cacheable(key = "'fareType/' + #key")
    public Map<FareTypeCd, FareType> findAll(String key) {
        Map<FareTypeCd, FareType> fareTypeMap = new HashMap<>();
        List<FareType> fareTypeList = fareTypeRepository.findAll();
        for (FareType fareType : fareTypeList) {
            fareTypeMap.put(fareType.getFareTypeCd(), fareType);
        }
        return fareTypeMap;
    }

    /**
     * キャッシュのリフレッシュ
     * @param key
     */
    @CacheEvict(key = "'fareType/' + #key")
    public void refresh(String key) {

    }
}
