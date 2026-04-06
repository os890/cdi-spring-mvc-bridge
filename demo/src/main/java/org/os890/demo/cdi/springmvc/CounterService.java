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
import java.util.concurrent.atomic.AtomicInteger;

/**
 * CDI bean that counts invocations, demonstrating application-scoped
 * state shared between CDI and Spring via the bridge.
 *
 * <p>Because this bean is {@link ApplicationScoped}, the same counter
 * instance is available to both CDI injection points and Spring
 * {@code @Autowired} fields once the bridge is active.</p>
 */
@ApplicationScoped
public class CounterService
{
    private final AtomicInteger counter = new AtomicInteger();

    /**
     * Increments the counter and returns the new value.
     *
     * @return the counter value after incrementing
     */
    public int increment()
    {
        return counter.incrementAndGet();
    }

    /**
     * Returns the current counter value without modifying it.
     *
     * @return the current counter value
     */
    public int getValue()
    {
        return counter.get();
    }
}
