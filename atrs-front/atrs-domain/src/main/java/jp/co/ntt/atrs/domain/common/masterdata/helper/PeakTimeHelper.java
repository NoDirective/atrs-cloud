/*
 * Copyright(c) 2017 NTT Corporation.
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
