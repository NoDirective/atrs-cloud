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
package jp.co.ntt.atrs.app.common.boot;

import javax.servlet.Filter;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;

// FIXME Filter型のBeanが二重にサーブレットコンテナに登録されてしまう問題への暫定対処
// ( issue : http://stackoverflow.com/questions/28421966/prevent-spring-boot-from-registering-a-servlet-filter ) 
public class DefaultFiltersBeanFactoryPostProcessor implements
                                                    BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(
            ConfigurableListableBeanFactory bf) throws BeansException {
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) bf;

        String[] beanNames = beanFactory.getBeanNamesForType(Filter.class);
        for (String beanName : beanNames) {
            BeanDefinition definition = BeanDefinitionBuilder
                    .genericBeanDefinition(FilterRegistrationBean.class)
                    .setScope(BeanDefinition.SCOPE_SINGLETON)
                    .addConstructorArgReference(beanName)
                    .addConstructorArgValue(new ServletRegistrationBean[] {})
                    .addPropertyValue("enabled", false).getBeanDefinition();

            beanFactory.registerBeanDefinition(beanName
                    + "FilterRegistrationBean", definition);
        }
    }
}
