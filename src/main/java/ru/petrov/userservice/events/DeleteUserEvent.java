package ru.petrov.userservice.events;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeleteUserEvent {
    private String id;
}
