# Spring & Spring Boot

Spring is a rich ecosystem built with Java, designed for enterprise-level applications. Thanks to its extensive features, we can build various types of applications, such as web applications (traditional web apps and RESTful applications), microservices, batch applications that can handle large volumes of data, cloud applications, mobile backends, and more. Spring offers numerous projects, including Spring MVC, Spring Data (JPA, MongoDB, etc.), Spring Security, and Spring Microservices.

On the other hand, Spring Boot is an extension of Spring that focuses more on convention over configuration. While the Spring framework possesses a rich ecosystem, one challenge developers face is the configuration of the application. Spring Boot addresses this by enabling developers to focus more on business logic and problem-solving rather than configuration.

## Dependency Injection (DI) & Inversion of Control (IoC)

DI and IoC are fundamental concepts of the Spring framework. DI (Dependency Injection) is a design pattern where a class receives its dependencies (beans) from an external source rather than creating them internally. Spring handles the injection of necessary beans into a class when needed, promoting decoupling in the code.

### Types of Dependency Injection

1. **Injection by Constructor**: The recommended approach that ensures dependencies are injected at the moment the bean is created. This method simplifies unit and integration testing.

2. **Injection by Setter**: This method is used for optional dependencies that a bean may not require immediately upon instantiation but may need later.

3. **Injection by Field**: Common in Spring, this uses the `@Autowired` annotation. It is less visible than the other methods, which can complicate testing.

**Conclusion**: Constructor injection is generally the best practice for its safety and simplicity. Setter injection is preferred for optional dependencies, while field injection should be used sparingly due to its opacity.

## Beans

Beans are objects managed by the Spring framework. Spring oversees their construction, destruction, lifecycle, and DI.

### Declaring Beans

There are three primary methods for declaring beans in Spring:

1. **XML Configuration File**: Common in legacy projects, but no longer recommended due to the heavy configuration load.

    ```xml
    <bean id="memberSingleton" class="dev.yassiraitelghari.maska.domain.Member" scope="singleton"/>
    ```

2. **Component Scanning**: This is the preferred method, especially in Spring Boot, where beans can be declared by their type.

    ```xml
    <context:component-scan base-package="com.example.package" />
    ```

   > **Note**: This element is not necessary in Spring Boot, as it configures component scanning automatically.

3. **@Bean Annotation**: Create a configuration class to declare beans.

    ```java
    @Configuration
    class AppConfig {
        @Bean
        public UserService userService() {
            return new UserService();
        }
    }
    ```

### Bean Scopes

Bean scopes define the lifecycle and usage of beans. The six primary scopes are:

- **Singleton**: Only one instance of the bean is created. This is the default scope and is resource-efficient.
- **Prototype**: A new instance is created every time the bean is requested.
- **Request**: A new instance is created for each HTTP request.
- **Session**: A new instance is created for each HTTP session.
- **Application**: A single instance is created for the entire application.
- **WebSocket**: A new instance is created for each WebSocket request.

## Stereotype Annotations

Stereotype annotations define the role of a bean in Spring. The four main annotations are:

- **@Component**: A generic stereotype for any Spring-managed component.
- **@Repository**: A bean that interacts with the database.
- **@Service**: A bean that contains business logic.
- **@Controller**: A bean that handles HTTP requests.

These annotations help organize the code and allow Spring to handle beans differently based on their roles.

## Spring Data

Spring Data simplifies data access and management, supporting various modules like JPA, MongoDB, RDB, and NRDB. In Spring Boot, we primarily use Spring Data JPA, which offers predefined interfaces like `JpaRepository`, `CrudRepository`, and `Repository`.

- **Repository**: The base interface, empty but essential for Spring to recognize it.
- **CrudRepository**: Offers simple CRUD functionalities.
- **JpaRepository**: Extends `CrudRepository`, providing additional functionalities like pagination and sorting.

These components streamline data operations and allow customizable queries using derived queries or the `@Query` annotation.

## Spring MVC

Spring MVC is designed for building web applications and RESTful APIs, based on the Model-View-Controller (MVC) architecture pattern, which separates code into three primary modules:

- **View**: Represents the UI layer (e.g., JSP, HTML files).
- **Model**: Manages data communication with the database, often using the repository pattern.
- **Controller**: Connects the Model and View, handling HTTP requests and business logic.

The MVC pattern enhances code organization and robustness by separating concerns, making applications easier to maintain and test.

## Aspect-Oriented Programming (AOP)

Aspect-Oriented Programming enhances modularity by separating cross-cutting concerns from business logic. It is particularly useful for managing security, logging, and transaction management.

### Key Concepts

- **Aspect**: A module encapsulating a cross-cutting concern (e.g., a security aspect).
- **Join Point**: A specific execution point where an aspect can be applied (e.g., method calls).
- **Pointcut**: An expression defining which join points an aspect intercepts.
- **Advice**: The action taken by an aspect at a join point, including:
    - **Before**: Executes before the join point.
    - **After**: Executes after the join point.
    - **Around**: Wraps the join point, adding behavior before and after method execution.
- **Weaving**: Integrating aspects into application code, occurring at compile, load, or runtime.

### Benefits of AOP

- **Separation of Concerns**: Keeps business logic distinct from cross-cutting concerns.
- **Code Reusability**: Aspects can be reused across different application parts.
- **Improved Maintainability**: Changes to concerns are centralized in one location.
- **Enhanced Readability**: Core logic remains uncluttered.

### Sample Example: Role-Based Access Control

1. Define the Custom Annotation**

   Create a custom annotation `@RoleCheck`.

   ```java
   package com.example.demo.annotations;

   import java.lang.annotation.ElementType;
   import java.lang.annotation.Retention;
   import java.lang.annotation.RetentionPolicy;
   import java.lang.annotation.Target;

   @Target(ElementType.METHOD)
   @Retention(RetentionPolicy.RUNTIME)
   public @interface RoleCheck {
       String value(); // Specify the required role
   }

2. Create the Aspect for Role Checking

   Define an aspect for role validation.
   
```java

package com.example.demo.aspects;

import com.example.demo.annotations.RoleCheck;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class RoleCheckAspect {

    @Before("@annotation(roleCheck)")
    public void checkRole(RoleCheck roleCheck) {
        String requiredRole = roleCheck.value();
        String currentUserRole = getCurrentUserRole(); // Logic to retrieve current user's role

        if (!currentUserRole.equals(requiredRole)) {
            throw new SecurityException("User does not have the required role: " + requiredRole);
        }
    }

    private String getCurrentUserRole() {
        return "USER"; // Example role for demonstration
    }
}
```
3. Create a Sample Service

   Define a service with protected methods.
```java

package com.example.demo.services;

import com.example.demo.annotations.RoleCheck;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    @RoleCheck("ADMIN")
    public void enterAdminDashboard() {
        System.out.println("Access granted to Admin Dashboard");
    }

    @RoleCheck("USER")
    public void enterUserDashboard() {
        System.out.println("Access granted to User Dashboard");
    }
}
```
4. Using the Service

   Create a simple application runner to test the role checks.

```java
package com.example.demo;

import com.example.demo.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements CommandLineRunner {

    @Autowired
    private DashboardService dashboardService;

    @Override
    public void run(String... args) throws Exception {
        try {
            dashboardService.enterAdminDashboard(); // Attempting to access admin dashboard
        } catch (SecurityException e) {
            System.out.println(e.getMessage());
        }
    }
}
```