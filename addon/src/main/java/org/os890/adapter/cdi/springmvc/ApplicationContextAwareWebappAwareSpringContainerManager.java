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
import org.os890.ds.addon.spring.impl.bidirectional.WebappAwareSpringContainerManager;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Extended {@link WebappAwareSpringContainerManager} that caches the
 * application context created during CDI bootstrap so that the
 * {@link CdiAwareDispatcherServlet} can consume it later.
 *
 * <p>In Servlet 3.0+ environments the listener is registered automatically
 * via {@code META-INF/web-fragment.xml}. For older containers configure it
 * as a listener in {@code web.xml}.</p>
 *
 * @see CdiAwareDispatcherServlet#findWebApplicationContext()
 */
@Exclude
//only configure it as listener (in web.xml) if the version of the servlet-container is < 3.0
public class ApplicationContextAwareWebappAwareSpringContainerManager extends WebappAwareSpringContainerManager
{
    private static ConfigurableApplicationContext previousApplicationContext;

    /**
     * Boots the Spring container and caches the resulting context for later
     * consumption by the {@link CdiAwareDispatcherServlet}.
     *
     * @param beanFactoryPostProcessors optional post-processors forwarded to
     *                                  the parent implementation
     * @return the started Spring application context
     */
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

    /**
     * Returns the previously cached application context and clears the
     * reference so it can only be consumed once.
     *
     * @return the cached {@link ConfigurableApplicationContext}, or
     *         {@code null} if none was cached or it has already been consumed
     */
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
