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
 * Verifies the {@link CounterService} CDI bean using
 * {@link EnableTestBeans}.
 */
@EnableTestBeans
class CounterServiceTest
{
    @Inject
    private CounterService counterService;

    /**
     * Checks that the counter service is injected.
     */
    @Test
    void counterServiceIsInjected()
    {
        assertNotNull(counterService, "CounterService should be injected by CDI");
    }

    /**
     * Checks that increment returns sequential values.
     */
    @Test
    void incrementReturnsSequentialValues()
    {
        int first = counterService.increment();
        int second = counterService.increment();
        assertEquals(first + 1, second, "Consecutive increments should differ by 1");
    }

    /**
     * Checks that getValue reflects the counter state.
     */
    @Test
    void getValueReflectsState()
    {
        int before = counterService.getValue();
        counterService.increment();
        assertEquals(before + 1, counterService.getValue(),
                "getValue should reflect the increment");
    }
}
