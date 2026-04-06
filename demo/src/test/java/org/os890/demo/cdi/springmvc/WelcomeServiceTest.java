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

import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.os890.cdi.addon.dynamictestbean.EnableTestBeans;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Integration test for {@link WelcomeService}, verifying that the
 * CDI container correctly wires {@link GreetingService} and
 * {@link CounterService} into the composite service.
 *
 * <p>This test demonstrates the CDI bean graph that the CDI-Spring
 * bridge would expose to Spring MVC controllers at runtime.</p>
 */
@EnableTestBeans
class WelcomeServiceTest
{
    @Inject
    private WelcomeService welcomeService;

    /**
     * Checks that the composite service is injected with all
     * dependencies resolved.
     */
    @Test
    void welcomeServiceIsInjected()
    {
        assertNotNull(welcomeService, "WelcomeService should be injected by CDI");
    }

    /**
     * Checks that the welcome message contains the greeting and a
     * visit number.
     */
    @Test
    void welcomeMessageContainsGreetingAndVisitNumber()
    {
        String message = welcomeService.welcome("Bob");
        assertTrue(message.startsWith("Hello, Bob!"),
                "Message should start with greeting");
        assertTrue(message.contains("visit #"),
                "Message should contain visit number");
    }

    /**
     * Checks that consecutive welcomes produce incrementing visit
     * numbers.
     */
    @Test
    void consecutiveWelcomesIncrementVisitNumber()
    {
        String first = welcomeService.welcome("Eve");
        String second = welcomeService.welcome("Eve");
        assertNotNull(first);
        assertNotNull(second);
        // Both messages should be different because the counter increments
        assertTrue(!first.equals(second),
                "Consecutive welcomes should have different visit numbers");
    }
}
