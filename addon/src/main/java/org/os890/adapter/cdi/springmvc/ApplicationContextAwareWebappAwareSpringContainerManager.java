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

import org.apache.deltaspike.core.api.exclude.Exclude;
import org.os890.ds.addon.spring.impl.bidirectional.WebappAwareSpringContainerManager;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.ConfigurableApplicationContext;

@Exclude
//only configure it as listener (in web.xml) if the version of the servlet-container is < 3.0
public class ApplicationContextAwareWebappAwareSpringContainerManager extends WebappAwareSpringContainerManager
{
    private static ConfigurableApplicationContext previousApplicationContext;

    @Override
    public ConfigurableApplicationContext bootContainer(BeanFactoryPostProcessor... beanFactoryPostProcessors)
    {
        try
        {
            return super.bootContainer(beanFactoryPostProcessors);
        }
        finally
        {
            previousApplicationContext = getStartedContainer();
        }
    }

    public static ConfigurableApplicationContext consumePreviousApplicationContext()
    {
        try
        {
            return previousApplicationContext;
        }
        finally
        {
            previousApplicationContext = null;
        }
    }
}
