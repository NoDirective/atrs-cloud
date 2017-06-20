/*
 * Copyright(c) 2017 NTT Corporation.
 */
package jp.co.ntt.atrs.domain.repository.boardingclass;

import java.util.List;

import jp.co.ntt.atrs.domain.model.BoardingClass;
import jp.co.ntt.atrs.domain.model.BoardingClassCd;

/**
 * 搭乗クラステーブルにアクセスするリポジトリインターフェース。
 * @author NTT 電電太郎
 */
public interface BoardingClassRepository {
    /**
     * 全ての搭乗クラスを取得する。
     * @return 搭乗クラスリスト
     */
    List<BoardingClass> findAll();

    /**
     * Finds a BoadingClass specified in a given id.
     */
    BoardingClass findOne(BoardingClassCd boardingClassCd);
}
