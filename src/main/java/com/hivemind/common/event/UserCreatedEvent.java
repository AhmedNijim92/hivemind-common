package com.hivemind.common.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreatedEvent
{
    private UUID userId;
    private String mobileNumber;
    private String name;
    private String email;
    private LocalDateTime timestamp;
}
