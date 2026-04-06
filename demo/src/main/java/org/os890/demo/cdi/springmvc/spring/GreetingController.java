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

package org.os890.demo.cdi.springmvc.spring;

import org.apache.deltaspike.core.api.exclude.Exclude;
import org.os890.demo.cdi.springmvc.GreetingService;
import org.os890.demo.cdi.springmvc.WelcomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * Spring-managed controller that uses CDI beans injected via the
 * CDI-Spring bridge.
 *
 * <p>The {@link GreetingService} and {@link WelcomeService} are CDI
 * {@code @ApplicationScoped} beans. Through the bridge they become
 * available to Spring's {@code @Autowired} injection, so this
 * controller can use them without any CDI-specific annotations.</p>
 *
 * <p>Annotated with {@link Exclude} to prevent CDI from also
 * managing this bean — it should only live in the Spring container.</p>
 */
@Exclude
@Component("greetingController")
public class GreetingController
{
    @Lazy
    @Autowired
    private GreetingService greetingService;

    @Lazy
    @Autowired
    private WelcomeService welcomeService;

    /**
     * Delegates to the CDI-managed {@link GreetingService}.
     *
     * @param name the name to greet
     * @return the greeting message
     */
    public String greet(String name)
    {
        return greetingService.greet(name);
    }

    /**
     * Delegates to the CDI-managed {@link WelcomeService}.
     *
     * @param name the visitor name
     * @return the welcome message with visit count
     */
    public String welcome(String name)
    {
        return welcomeService.welcome(name);
    }
}
