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
    public List<Map<String, Object>> queryForList(String sql,
            String codeListId) {
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
