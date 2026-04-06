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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Verifies that {@link DefaultGreetingService} is discovered and
 * injected by CDI SE using {@link EnableTestBeans}.
 */
@EnableTestBeans
class GreetingServiceTest
{
    @Inject
    private GreetingService greetingService;

    /**
     * Checks that the CDI container resolves the greeting service.
     */
    @Test
    void greetingServiceIsInjected()
    {
        assertNotNull(greetingService, "GreetingService should be injected by CDI");
    }

    /**
     * Checks that the greeting message is correctly formatted.
     */
    @Test
    void greetReturnsExpectedMessage()
    {
        assertEquals("Hello, World!", greetingService.greet("World"));
    }

    /**
     * Checks greeting with a different name.
     */
    @Test
    void greetWithDifferentName()
    {
        assertEquals("Hello, Alice!", greetingService.greet("Alice"));
    }
}
