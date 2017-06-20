/*
 * Copyright(c) 2017 NTT Corporation.
 */
package jp.co.ntt.atrs.domain.common.masterdata;

import jp.co.ntt.atrs.domain.common.masterdata.helper.PeakTimeHelper;
import jp.co.ntt.atrs.domain.model.PeakTime;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

/**
 * ピーク時期情報を提供するクラス。
 * @author NTT 電電太郎
 */
@Component
public class PeakTimeProvider {

    private static final String CACHE_KEY = "peakTimes";

    /**
     * ピーク時期情報ヘルパ。
     */
    @Inject
    PeakTimeHelper peakTimeHelper;

    /**
     * 指定搭乗日に該当するピーク時期情報を取得する。
     * @param depDate 搭乗日
     * @return ピーク時期情報。該当するピーク時期情報が存在しない場合null。
     */
    public PeakTime getPeakTime(Date depDate) {
        Assert.notNull(depDate);
        List<PeakTime> peakTimeList = peakTimeHelper.findAll(CACHE_KEY);
        for (PeakTime peakTime : peakTimeList) {
            Interval peakTimeInterval = new Interval(new DateTime(peakTime
                    .getPeakStartDate()).withTimeAtStartOfDay(), new DateTime(peakTime
                    .getPeakEndDate()).withTimeAtStartOfDay().plus(1));
            // 搭乗日が該当するピーク時期積算比率を返却します
            if (peakTimeInterval.contains(depDate.getTime())) {
                return peakTime;
            }
        }
        return null;
    }

    /**
     * キャッシュのリフレッシュ
     */
    public void cacheRefresh() {
        peakTimeHelper.refresh(CACHE_KEY);
    }
}
