/*
 * Copyright 2014-2017 NTT Corporation.
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
package jp.co.ntt.atrs.domain.common.codelist;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

import jp.co.ntt.atrs.domain.common.jdbc.CodeListJdbcTemplateWrapper;

/**
 * Initializes codelist information from a database using JDBC.
 * <p>
 * The results of {@link #querySql} are stored in the codelist. The column name of 'value' of codelist is set by
 * {@link #valueColumn} and 'label' by {@link #labelColumn}.<br>
 * Each row is put to the codelist unless value or label of it is <code>null</code>.
 * </p>
 * @author NTT 電電太郎
 */
public class AtrsCodeList extends AbstractAtrsCodeList {

    /**
     * Database access information
     */
    private CodeListJdbcTemplateWrapper codeListJdbcTemplateWrapper;

    /**
     * SQL Query to access the database
     */
    private String querySql;

    /**
     * property that holds the name of the column of the database holding the value part of the codelist
     */
    private String valueColumn;

    /**
     * property that holds the name of the column of the database holding the label part of the codelist
     */
    private String labelColumn;

    /**
     * Retrieves the codelist from the database and returns it as a Map<br>
     * Each row is put to the codelist unless value or label of it is <code>null</code>.
     * @return Map latest codelist information
     * @see org.terasoluna.gfw.common.codelist.AbstractReloadableCodeList#retrieveMap()
     */
    @Override
    protected Map<String, String> retrieveMap() {
        List<Map<String, Object>> rows = codeListJdbcTemplateWrapper
                .queryForList(querySql, getCodeListId());
        LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
        for (Map<String, Object> row : rows) {
            Object key = row.get(valueColumn);
            Object value = row.get(labelColumn);
            if (key != null && value != null) {
                result.put(key.toString(), value.toString());
            }
        }
        return result;
    }

    /**
     * Sets JdbcTemplate
     * @param jdbcTemplate JdbcTemplate instance for fetching code list records
     */
    public void setCodeListJdbcTemplateWrapper(
            CodeListJdbcTemplateWrapper codeListJdbcTemplateWrapper) {
        this.codeListJdbcTemplateWrapper = codeListJdbcTemplateWrapper;
    }

    /**
     * This method is called after the codelist is initialized Checks whether the values of querySql, valueColumn, labelColumn
     * and jdbcTemplate properties are set
     * @see org.terasoluna.gfw.common.codelist.AbstractReloadableCodeList#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() {
        Assert.hasLength(this.querySql, "querySql is empty");
        Assert.hasLength(valueColumn, "valueColumn is empty");
        Assert.hasLength(labelColumn, "labelColumn is empty");
        Assert.notNull(this.codeListJdbcTemplateWrapper,
                "codeListJdbcTemplateWrapper is null");
        super.afterPropertiesSet();
    }

    /**
     * Setter method for labelColumn
     * @param labelColumn column name for fetch a code label
     */
    public void setLabelColumn(String labelColumn) {
        this.labelColumn = labelColumn;
    }

    /**
     * Setter method for valueColumn
     * @param valueColumn column name for fetch code value
     */
    public void setValueColumn(String valueColumn) {
        this.valueColumn = valueColumn;
    }

    /**
     * Setter method for querySql
     * @param querySql sql for fetching code list records from database
     */
    public void setQuerySql(String querySql) {
        this.querySql = querySql;
    }

    /*
     * (非 Javadoc)
     * @see jp.co.ntt.atrs.domain.common.codelist.AbstractAtrsCodeList#getCodeListJdbcTemplateWrapper()
     */
    @Override
    protected CodeListJdbcTemplateWrapper getCodeListJdbcTemplateWrapper() {
        return this.codeListJdbcTemplateWrapper;
    }
}
