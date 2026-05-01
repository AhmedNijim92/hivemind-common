# hivemind-common

Shared DTOs and Kafka event classes used across all HiveMind microservices.

## Contents
- `ApiResponse` — Generic response wrapper
- `UserDto` — User registration DTO
- `UserCreatedEvent` — Kafka event for user creation
- `PostCreatedEvent` — Kafka event for post creation
- `GroupCreatedEvent` — Kafka event for group creation
- `MeetingStartedEvent` — Kafka event for meeting start

## Usage

Add as a dependency in your service's `pom.xml`:
```xml
<dependency>
    <groupId>com.hivemind</groupId>
    <artifactId>hivemind-common</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Build
```bash
mvn clean install
```
