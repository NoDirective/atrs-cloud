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
