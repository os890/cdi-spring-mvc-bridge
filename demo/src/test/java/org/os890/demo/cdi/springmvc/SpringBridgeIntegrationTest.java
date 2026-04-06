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

import jakarta.ejb.embeddable.EJBContainer;
import jakarta.inject.Inject;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.os890.demo.cdi.springmvc.spring.GreetingController;
import org.springframework.context.ApplicationContext;

/**
 * Integration test that boots TomEE embedded (CDI + EJB) together with
 * the CDI-Spring bridge and verifies that CDI beans are accessible from
 * Spring.
 *
 * <p>The {@link org.os890.ds.addon.spring.impl.SpringBridgeExtension}
 * picks up the CDI beans during deployment and registers them in a
 * Spring {@link ApplicationContext}. The Spring
 * {@link GreetingController} receives these CDI beans via
 * {@code @Autowired}, proving the bridge works end-to-end.</p>
 */
class SpringBridgeIntegrationTest
{
    private static EJBContainer container;

    @Inject
    private GreetingService greetingService;

    @Inject
    private WelcomeService welcomeService;

    @Inject
    private ApplicationContext springContext;

    /**
     * Starts the TomEE embedded container, which boots CDI and the
     * Spring bridge extension automatically.
     */
    @BeforeAll
    static void setUp()
    {
        container = EJBContainer.createEJBContainer();
    }

    /**
     * Injects CDI beans into this test instance.
     *
     * @throws Exception if JNDI binding fails
     */
    @BeforeEach
    void inject() throws Exception
    {
        container.getContext().bind("inject", this);
    }

    /**
     * Shuts down the embedded container.
     */
    @AfterAll
    static void tearDown()
    {
        if (container != null)
        {
            container.close();
        }
    }

    /**
     * Verifies that CDI beans are injected into the test.
     */
    @Test
    void cdiBeanInjection()
    {
        Assertions.assertNotNull(greetingService, "GreetingService should be injected");
        Assertions.assertEquals("Hello, World!", greetingService.greet("World"));
    }

    /**
     * Verifies that the Spring ApplicationContext is available via
     * the bridge's CDI producer.
     */
    @Test
    void springContextIsAvailable()
    {
        Assertions.assertNotNull(springContext, "Spring ApplicationContext should be injected via the bridge");
    }

    /**
     * Verifies that CDI beans are registered in the Spring context
     * and can be looked up as Spring beans.
     */
    @Test
    void cdiBeanAccessibleFromSpring()
    {
        Object bean = springContext.getBean("greetingController");
        Assertions.assertNotNull(bean, "GreetingController should exist in the Spring context");
        Assertions.assertInstanceOf(GreetingController.class, bean);
    }

    /**
     * Verifies that the Spring controller received CDI beans via
     * {@code @Autowired} and can delegate to them.
     */
    @Test
    void springControllerUsesCdiBean()
    {
        GreetingController controller = springContext.getBean(GreetingController.class);
        Assertions.assertEquals("Hello, Alice!", controller.greet("Alice"),
                "Spring controller should delegate to CDI GreetingService");
    }

    /**
     * Verifies that the composite WelcomeService — which itself
     * injects other CDI beans — works correctly through the bridge.
     */
    @Test
    void springControllerUsesCompositeCdiBean()
    {
        GreetingController controller = springContext.getBean(GreetingController.class);
        String welcome = controller.welcome("Bob");
        Assertions.assertTrue(welcome.startsWith("Hello, Bob!"),
                "Welcome should start with greeting");
        Assertions.assertTrue(welcome.contains("visit #"),
                "Welcome should contain visit number");
    }
}
