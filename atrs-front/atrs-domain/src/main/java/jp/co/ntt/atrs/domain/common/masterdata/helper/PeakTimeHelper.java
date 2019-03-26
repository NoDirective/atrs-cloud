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

import java.util.List;

import javax.inject.Inject;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ntt.atrs.domain.model.PeakTime;
import jp.co.ntt.atrs.domain.repository.peaktime.PeakTimeRepository;

/**
 * ピーク時期情報のヘルパクラス。
 * @author NTT 電電太郎
 */
@Component
@CacheConfig(cacheNames = "peakTimes")
public class PeakTimeHelper {

    /**
     * ピーク時期情報リポジトリ。
     */
    @Inject
    PeakTimeRepository peakTimeRepository;

    @Transactional(readOnly = true)
    @Cacheable(key = "'peakTimes/' + #key")
    public List<PeakTime> findAll(String key) {
        return peakTimeRepository.findAll();
    }

    /**
     * キャッシュのリフレッシュ
     * @param key
     */
    @CacheEvict(key = "'peakTimes/' + #key")
    public void refresh(String key) {

    }
}
