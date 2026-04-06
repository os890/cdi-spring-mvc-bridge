/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.os890.adapter.cdi.springmvc;

import org.apache.deltaspike.core.api.exclude.Exclude;
import org.os890.ds.addon.spring.impl.CdiSpringScope;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

/**
 * {@link BeanFactoryPostProcessor} that copies bean definitions and the
 * {@link CdiSpringScope} from the CDI bridge bean factory into the
 * DispatcherServlet-level bean factory.
 *
 * <p>The source bean factory is passed through a {@link ThreadLocal} set by
 * {@link CdiAwareDispatcherServlet#findWebApplicationContext()} before the
 * context is refreshed.</p>
 *
 * @see CdiAwareDispatcherServlet
 * @see CustomXmlWebApplicationContext
 */
@Exclude
class BeanFactoryAwareBeanFactoryPostProcessor implements BeanFactoryPostProcessor
{
    static ThreadLocal<ConfigurableListableBeanFactory> currentBeanFactory =
            new ThreadLocal<ConfigurableListableBeanFactory>();

    /**
     * Copies all bean definitions and the CDI-Spring bridge scope from the
     * thread-local source factory into the given target factory.
     *
     * @param beanFactory the target bean factory of the DispatcherServlet context
     * @throws BeansException if a bean definition cannot be registered
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException
    {
        for (String beanName : currentBeanFactory.get().getBeanDefinitionNames())
        {
            ((BeanDefinitionRegistry) beanFactory).registerBeanDefinition(
                    beanName, currentBeanFactory.get().getBeanDefinition(beanName));
        }
        String bridgeScopeName = CdiSpringScope.class.getName();
        beanFactory.registerScope(bridgeScopeName, currentBeanFactory.get().getRegisteredScope(bridgeScopeName));
    }
}
