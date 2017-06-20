/*
 * Copyright(c) 2017 NTT Corporation.
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
        Assert.notNull(fareTypeCd);
        return fareTypeHelper.findAll(CACHE_KEY).get(fareTypeCd);
    }

    /**
     * キャッシュのリフレッシュ
     */
    public void cacheRefresh() {
        fareTypeHelper.refresh(CACHE_KEY);
    }
}
