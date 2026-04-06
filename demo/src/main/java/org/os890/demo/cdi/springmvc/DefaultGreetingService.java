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

/**
 * Default CDI implementation of {@link GreetingService}.
 *
 * <p>This {@link ApplicationScoped} bean is discovered by CDI and
 * exposed to the Spring context through the CDI-Spring bridge. A
 * Spring MVC controller can then {@code @Autowired} it as if it
 * were a regular Spring bean.</p>
 */
@ApplicationScoped
public class DefaultGreetingService implements GreetingService
{
    /**
     * Returns a simple greeting for the given name.
     *
     * @param name the name to greet (must not be {@code null})
     * @return a greeting in the form {@code "Hello, <name>!"}
     */
    @Override
    public String greet(String name)
    {
        return "Hello, " + name + "!";
    }
}
