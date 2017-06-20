/*
 * Copyright(c) 2017 NTT Corporation.
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
