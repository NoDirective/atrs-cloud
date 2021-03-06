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
package jp.co.ntt.atrs.domain.common.masterdata;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import jp.co.ntt.atrs.domain.common.masterdata.helper.PeakTimeHelper;
import jp.co.ntt.atrs.domain.model.PeakTime;

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
        Assert.notNull(depDate, "depDate must not be null");
        List<PeakTime> peakTimeList = peakTimeHelper.findAll(CACHE_KEY);
        for (PeakTime peakTime : peakTimeList) {
            Interval peakTimeInterval = new Interval(new DateTime(peakTime
                    .getPeakStartDate())
                            .withTimeAtStartOfDay(), new DateTime(peakTime
                                    .getPeakEndDate()).withTimeAtStartOfDay()
                                            .plus(1));
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
