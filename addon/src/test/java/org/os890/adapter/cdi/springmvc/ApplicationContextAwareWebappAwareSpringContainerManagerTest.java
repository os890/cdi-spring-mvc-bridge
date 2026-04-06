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

import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Tests the consume-once semantics of
 * {@link ApplicationContextAwareWebappAwareSpringContainerManager}.
 */
class ApplicationContextAwareWebappAwareSpringContainerManagerTest
{
    /**
     * Verifies that {@code consumePreviousApplicationContext()} returns
     * {@code null} when no context has been cached.
     */
    @Test
    void consumeReturnsNullWhenNothingCached()
    {
        ConfigurableApplicationContext result =
                ApplicationContextAwareWebappAwareSpringContainerManager.consumePreviousApplicationContext();
        assertNull(result, "Expected null when no context was cached");
    }

    /**
     * Verifies that consecutive calls return {@code null} after the first
     * consume clears the reference.
     */
    @Test
    void consumeClearsReferenceAfterFirstCall()
    {
        // first consume clears any leftover state
        ApplicationContextAwareWebappAwareSpringContainerManager.consumePreviousApplicationContext();

        // second consume must also return null
        ConfigurableApplicationContext result =
                ApplicationContextAwareWebappAwareSpringContainerManager.consumePreviousApplicationContext();
        assertNull(result, "Expected null after consuming cleared reference");
    }
}
