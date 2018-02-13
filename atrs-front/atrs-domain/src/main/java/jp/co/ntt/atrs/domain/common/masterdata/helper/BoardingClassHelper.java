/*
 * Copyright 2014-2017 NTT Corporation.
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

import jp.co.ntt.atrs.domain.model.BoardingClass;
import jp.co.ntt.atrs.domain.model.BoardingClassCd;
import jp.co.ntt.atrs.domain.repository.boardingclass.BoardingClassRepository;

/**
 * 搭乗クラス情報のヘルパクラス。
 * @author NTT 電電太郎
 */
@Component
@CacheConfig(cacheNames = "boardingClass")
public class BoardingClassHelper {

    /**
     * 搭乗クラス情報リポジトリ。
     */
    @Inject
    BoardingClassRepository boardingClassRepository;

    /**
     * 搭乗クラスコードに該当する搭乗クラス情報を取得する。
     * @param boardingClassCd 搭乗クラスコード
     * @return 搭乗クラス情報。該当する搭乗クラス情報がない場合はnull。
     */
    @Transactional(readOnly = true)
    @Cacheable(key = "'boardingClass/' + #key")
    public Map<BoardingClassCd, BoardingClass> findAll(String key) {
        Map<BoardingClassCd, BoardingClass> boardingClassCodeMap = new HashMap<>();
        List<BoardingClass> boardingClassList = boardingClassRepository
                .findAll();
        for (BoardingClass boardingClass : boardingClassList) {
            boardingClassCodeMap.put(boardingClass.getBoardingClassCd(),
                    boardingClass);
        }
        return boardingClassCodeMap;
    }

    /**
     * キャッシュのリフレッシュ
     * @param key
     */
    @CacheEvict(key = "'boardingClass/' + #key")
    public void refresh(String key) {

    }

}
