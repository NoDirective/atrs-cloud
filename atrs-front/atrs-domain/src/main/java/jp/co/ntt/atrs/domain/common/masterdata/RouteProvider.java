/*
 * Copyright(c) 2017 NTT Corporation.
 */
package jp.co.ntt.atrs.domain.common.masterdata;

import javax.inject.Inject;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import jp.co.ntt.atrs.domain.common.masterdata.helper.RouteHelper;
import jp.co.ntt.atrs.domain.model.Route;

/**
 * 区間情報を提供するクラス。
 * @author NTT 電電太郎
 */
@Component
public class RouteProvider {

    private static final String CACHE_KEY = "route";

    /**
     * 区間情報ヘルパ。
     */
    @Inject
    RouteHelper routeHelper;

    /**
     * 出発空港コード、到着空港コードに該当する区間情報を取得する。
     * @param departureAirportCd 出発空港コード
     * @param arrivalAirportCd 到着空港コード
     * @return 区間情報。該当する区間情報が見つからない場合はnull。
     */
    public Route getRouteByAirportCd(String departureAirportCd,
            String arrivalAirportCd) {

        Assert.hasText(departureAirportCd);
        Assert.hasText(arrivalAirportCd);

        String searchKey = routeHelper.makeCacheKey(departureAirportCd,
                arrivalAirportCd);
        return routeHelper.findAll(CACHE_KEY).get(searchKey);
    }

    /**
     * キャッシュのリフレッシュ
     */
    public void cacheRefresh() {
        routeHelper.refresh(CACHE_KEY);
    }
}
