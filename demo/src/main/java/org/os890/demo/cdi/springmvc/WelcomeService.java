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

package org.os890.demo.cdi.springmvc;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * Composite CDI service that combines {@link GreetingService} and
 * {@link CounterService} to produce numbered welcome messages.
 *
 * <p>This bean demonstrates CDI-to-CDI injection — both collaborators
 * are discovered and injected by the CDI container. Through the
 * CDI-Spring bridge the entire object graph becomes available to
 * Spring MVC controllers.</p>
 */
@ApplicationScoped
public class WelcomeService
{
    @Inject
    private GreetingService greetingService;

    @Inject
    private CounterService counterService;

    /**
     * Produces a numbered welcome message for the given visitor name.
     *
     * @param name the visitor name (must not be {@code null})
     * @return a welcome string that includes the visit count
     */
    public String welcome(String name)
    {
        int visitNumber = counterService.increment();
        return greetingService.greet(name) + " (visit #" + visitNumber + ")";
    }
}
