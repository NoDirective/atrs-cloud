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
