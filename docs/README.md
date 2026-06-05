# HiveMind Common Library

> Shared DTOs and Kafka Events for HiveMind Microservices

## Overview

The hivemind-common module is a shared Java library that provides common DTOs, event classes, and validation rules used across all HiveMind microservices. It is installed to the local Maven repository and declared as a dependency by each service.

## Module Info

| Property | Value |
|----------|-------|
| Group ID | `com.hivemind` |
| Artifact ID | `hivemind-common` |
| Version | 1.0.0 |
| Packaging | JAR |
| Spring Boot | 3.3.5 |
| Java | 17 |

## Installation

```bash
cd microservices/hivemind-common
mvn clean install -DskipTests
```

This installs the JAR to your local `~/.m2/repository`. All other services reference it as:

```xml
<dependency>
    <groupId>com.hivemind</groupId>
    <artifactId>hivemind-common</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Package Structure

```
com.hivemind.common
├── dto
│   ├── ApiResponse.java
│   └── UserDto.java
└── event
    ├── UserCreatedEvent.java
    ├── PostCreatedEvent.java
    ├── GroupCreatedEvent.java
    └── MeetingStartedEvent.java
```

## Classes

### DTOs

#### ApiResponse

Generic response wrapper used by all services for simple message responses.

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
    private String message;
}
```

**Usage:** `return ResponseEntity.ok(new ApiResponse("Operation successful"));`

#### UserDto

User registration DTO with Jakarta validation constraints.

```java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^\\+[1-9]\\d{1,14}$", message = "Invalid mobile number format")
    private String mobileNumber;

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid email format")
    private String email;
}
```

**Validation rules:**
- `mobileNumber`: Required, E.164 format (e.g., `+46701234567`)
- `name`: Required, non-blank
- `email`: Optional, valid email format if provided

### Kafka Events

All events use Lombok `@Data`, `@Builder`, `@NoArgsConstructor`, `@AllArgsConstructor`.

#### UserCreatedEvent

Published by: `auth-service`
Consumed by: `user-service`, `notification-service`

```java
public class UserCreatedEvent {
    private UUID userId;
    private String mobileNumber;
    private String name;
    private String email;
    private LocalDateTime timestamp;
}
```

Topic: `user-created-topic`

#### PostCreatedEvent

Published by: `post-service`
Consumed by: `notification-service`

```java
public class PostCreatedEvent {
    private UUID postId;
    private UUID groupId;
    private UUID authorId;
    private String content;
    private LocalDateTime timestamp;
}
```

Topic: `post-created-topic`

#### GroupCreatedEvent

Published by: `group-service`
Consumed by: `notification-service`

```java
public class GroupCreatedEvent {
    private UUID groupId;
    private UUID creatorId;
    private String groupName;
    private LocalDateTime timestamp;
}
```

Topic: `group-created-topic`

#### MeetingStartedEvent

Published by: `meeting-service`
Consumed by: `notification-service`

```java
public class MeetingStartedEvent {
    private UUID meetingId;
    private UUID groupId;
    private UUID hostId;
    private String title;
    private LocalDateTime timestamp;
}
```

Topic: `meeting-started-topic`

## Dependencies

- spring-boot-starter-validation (for `@NotBlank`, `@Email`, `@Pattern`)
- lombok (for `@Data`, `@Builder`, etc.)

## Kafka Event Flow Diagram

```
auth-service ──[UserCreatedEvent]──→ user-created-topic
                                         ├──→ user-service (creates profile)
                                         └──→ notification-service

post-service ──[PostCreatedEvent]──→ post-created-topic
                                         └──→ notification-service

group-service ──[GroupCreatedEvent]──→ group-created-topic
                                          └──→ notification-service

meeting-service ──[MeetingStartedEvent]──→ meeting-started-topic
                                              └──→ notification-service
```

## Adding New Events

1. Create the event class in `com.hivemind.common.event`
2. Run `mvn clean install` on hivemind-common
3. Add Kafka producer in the publishing service
4. Add Kafka consumer in the subscribing service
5. Configure the topic name in both services' `application.yml`
