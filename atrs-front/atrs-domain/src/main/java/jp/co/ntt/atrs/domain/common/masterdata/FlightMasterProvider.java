/*
 * Copyright(c) 2017 NTT Corporation.
 */
package jp.co.ntt.atrs.domain.common.masterdata;

import jp.co.ntt.atrs.domain.common.masterdata.helper.FlightMasterHelper;
import jp.co.ntt.atrs.domain.model.FlightMaster;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.inject.Inject;

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
        Assert.hasText(flightName);
        return this.flightMasterHelper.findAll(CACHE_KEY).get(flightName);
    }

    /**
     * キャッシュのリフレッシュ
     */
    public void cacheRefresh() {
        flightMasterHelper.refresh(CACHE_KEY);
    }
}
