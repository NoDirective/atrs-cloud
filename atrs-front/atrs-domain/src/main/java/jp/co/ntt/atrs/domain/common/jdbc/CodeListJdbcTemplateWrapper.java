/*
 * Copyright(c) 2017 NTT Corporation.
 */
package jp.co.ntt.atrs.domain.common.jdbc;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * コードリストをキャッシュをするJdbcTemplate
 * @author NTT 電電太郎
 */
@CacheConfig(cacheNames = "codelists")
public class CodeListJdbcTemplateWrapper {

    private JdbcTemplate jdbcTemplate;

    /**
     * logger
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Cacheable(key = "'codelist/' + #codeListId")
    public List<Map<String, Object>> queryForList(String sql, String codeListId) {
        if (logger.isDebugEnabled()) {
            logger.debug("add cache codelists codeListId={}", codeListId);
        }
        return this.jdbcTemplate.queryForList(sql);
    }

    @CacheEvict(key = "'codelist/' + #codeListId")
    public void refresh(String codeListId) {
        if (logger.isDebugEnabled()) {
            logger.debug("remove cache codelists codeListId={}", codeListId);
        }
    }

    /**
     * @return jdbcTemplate
     */
    public JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    /**
     * @param jdbcTemplate セットする jdbcTemplate
     */
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
