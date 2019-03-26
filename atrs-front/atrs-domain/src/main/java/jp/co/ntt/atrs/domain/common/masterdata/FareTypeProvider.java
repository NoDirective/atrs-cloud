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

import javax.inject.Inject;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import jp.co.ntt.atrs.domain.common.masterdata.helper.FareTypeHelper;
import jp.co.ntt.atrs.domain.model.FareType;
import jp.co.ntt.atrs.domain.model.FareTypeCd;

/**
 * 運賃種別情報を提供するクラス。
 * @author NTT 電電太郎
 */
@Component
public class FareTypeProvider {

    private static final String CACHE_KEY = "FareType";

    /**
     * 運賃種別情報ヘルパ。
     */
    @Inject
    FareTypeHelper fareTypeHelper;

    /**
     * 指定運賃種別コードに該当する運賃種別情報を取得。
     * @param fareTypeCd 運賃種別コード
     * @return 運賃種別情報。該当する運賃種別情報がない場合null。
     */
    public FareType getFareType(FareTypeCd fareTypeCd) {
        Assert.notNull(fareTypeCd, "fareTypeCd must not be null");
        return fareTypeHelper.findAll(CACHE_KEY).get(fareTypeCd);
    }

    /**
     * キャッシュのリフレッシュ
     */
    public void cacheRefresh() {
        fareTypeHelper.refresh(CACHE_KEY);
    }
}
