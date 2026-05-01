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
public class GroupCreatedEvent
{
    private UUID groupId;
    private UUID creatorId;
    private String groupName;
    private LocalDateTime timestamp;
}
