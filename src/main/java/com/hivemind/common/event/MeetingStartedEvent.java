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
public class MeetingStartedEvent
{
    private UUID meetingId;
    private UUID groupId;
    private UUID hostId;
    private String title;
    private LocalDateTime timestamp;
}
