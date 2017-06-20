/*
 * Copyright(c) 2017 NTT Corporation.
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
    public void postProcessBeanFactory(ConfigurableListableBeanFactory bf) throws BeansException {
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
