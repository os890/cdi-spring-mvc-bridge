/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.os890.adapter.cdi.springmvc;

import org.os890.ds.addon.spring.impl.SpringBridgeExtension;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

//configure it instead of org.springframework.web.servlet.DispatcherServlet
public class CdiAwareDispatcherServlet extends DispatcherServlet
{
    private static final long serialVersionUID = -7868540235371022368L;

    @Override
    public Class<?> getContextClass()
    {
        return CustomXmlWebApplicationContext.class;
    }

    @Override
    protected WebApplicationContext findWebApplicationContext()
    {
        ConfigurableApplicationContext bridgeContext = ApplicationContextAwareWebappAwareSpringContainerManager.consumePreviousApplicationContext();
        ConfigurableListableBeanFactory bridgeFactory = bridgeContext.getBeanFactory();
        BeanFactoryAwareBeanFactoryPostProcessor.currentBeanFactory.set(bridgeFactory);

        WebApplicationContext result = null;
        try
        {
            result = createWebApplicationContext(null);
            return result;
        }
        finally
        {
            BeanFactoryAwareBeanFactoryPostProcessor.currentBeanFactory.set(null);
            BeanFactoryAwareBeanFactoryPostProcessor.currentBeanFactory.remove();

            if (result instanceof ConfigurableApplicationContext)
            {
                SpringBridgeExtension.updateSpringContext((ConfigurableApplicationContext)result);
            }
        }
    }
}
