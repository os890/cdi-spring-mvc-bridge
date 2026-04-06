# CDI-Spring-MVC Adapter

A lightweight adapter that bridges CDI (Contexts and Dependency Injection) beans into Spring MVC's `DispatcherServlet`, enabling seamless use of CDI-managed services inside Spring MVC controllers.

## How It Works

The adapter extends the [DS-Spring-Bridge Add-on](https://github.com/os890/ds-spring-bridge-addon) to support Spring MVC's `DispatcherServlet`:

1. **`ApplicationContextAwareWebappAwareSpringContainerManager`** — A servlet listener (auto-registered via `web-fragment.xml` in Servlet 3.0+) that boots the bidirectional CDI-Spring bridge and caches the resulting Spring `ApplicationContext`.

2. **`CdiAwareDispatcherServlet`** — Drop-in replacement for Spring's `DispatcherServlet`. On initialisation it consumes the cached bridge context and copies all CDI-exported bean definitions into its own `WebApplicationContext`.

3. **`BeanFactoryAwareBeanFactoryPostProcessor`** — Transfers bean definitions and the `CdiSpringScope` from the bridge factory into the servlet-level factory via a thread-local handoff.

The result: CDI `@ApplicationScoped`, `@RequestScoped` (and other scoped) beans become available to Spring MVC controllers through standard `@Autowired` injection.

## Project Structure

| Module | Description |
|--------|-------------|
| `addon` | The adapter library (JAR) |
| `demo`  | Demo CDI beans and tests showing the CDI layer in action |

## Requirements

- Java 25+
- Maven 3.6.3+
- Apache DeltaSpike 2.0.0
- Spring Framework 6.2.5
- Jakarta EE 10+ (CDI 4.1, Servlet 6.0)

## Building

```bash
mvn clean verify
```

## Quality Plugins

The build enforces:

- **Compiler** — `-Xlint:all`, fail on warnings
- **Enforcer** — Java 25+, Maven 3.6.3+, dependency convergence, banned `javax.*`
- **Checkstyle** — Code style validation (imports, braces, whitespace)
- **Apache RAT** — License header verification
- **JaCoCo** — Code coverage reporting
- **Surefire** — Test execution (JUnit Jupiter 6)

## Testing

Tests use the [dynamic-cdi-test-bean-addon](https://github.com/os890/dynamic-cdi-test-bean-addon) with `@EnableTestBeans` for zero-boilerplate CDI SE testing. The addon boots an OpenWebBeans CDI SE container, discovers beans via classpath scanning, and auto-mocks any unsatisfied injection points.

## License

[Apache License, Version 2.0](LICENSE)
