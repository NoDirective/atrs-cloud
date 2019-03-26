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
package jp.co.ntt.atrs.domain.common.codelist;

import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.terasoluna.gfw.common.codelist.AbstractCodeList;
import org.terasoluna.gfw.common.codelist.CodeList;
import org.terasoluna.gfw.common.codelist.ReloadableCodeList;

import jp.co.ntt.atrs.domain.common.jdbc.CodeListJdbcTemplateWrapper;

/**
 * Abstract implementation of reloadable {@link CodeList}
 * @author NTT 電電太郎
 */
public abstract class AbstractAtrsCodeList extends AbstractCodeList implements
                                           ReloadableCodeList,
                                           InitializingBean {

    /**
     * Lazy initialization flag
     */
    private boolean lazyInit = false;

    /**
     * Returns the codelist as an Immutable Thread-safe Map instance.
     * @return Map codelist in the form of an Immutable Thread-safe Map
     * @see org.terasoluna.gfw.common.codelist.CodeList#asMap()
     */
    @Override
    public final Map<String, String> asMap() {
        return retrieveMap();
    }

    /**
     * Flag that determines whether the codelist information needs to be eager fetched. <br>
     * @param lazyInit flag
     */
    public void setLazyInit(boolean lazyInit) {
        this.lazyInit = lazyInit;
    }

    /**
     * Reloads the codelist.
     * @see org.terasoluna.gfw.common.codelist.ReloadableCodeList#refresh()
     */
    @Override
    public final void refresh() {
        getCodeListJdbcTemplateWrapper().refresh(getCodeListId());
    }

    /**
     * This method is called after the properties of the codelist are set.
     * <p>
     * Checks the lazyInit flag to determine whether the <br>
     * codelist should be refreshed after the properties are set.<br>
     * If lazyInit flag is set to true, the codelist is not refreshed immediately. <br>
     * If it is set to false, it is refreshed (values re-loaded) immediately after the <br>
     * properties are loaded<br>
     * </p>
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() {
        if (!lazyInit) {
            refresh();
        }
    }

    /**
     * {@link CodeListJdbcTemplateWrapper}を取得する。
     * @return
     */
    protected abstract CodeListJdbcTemplateWrapper getCodeListJdbcTemplateWrapper();

    /**
     * Fetches the latest codelist information from the database and returns it as a map
     * @return Map codelist information
     */
    abstract protected Map<String, String> retrieveMap();
}
