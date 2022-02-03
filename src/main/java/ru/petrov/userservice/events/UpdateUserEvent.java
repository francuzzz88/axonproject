package ru.petrov.userservice.events;

import lombok.Builder;
import lombok.Data;
import ru.petrov.userservice.model.User;

@Data
@Builder
public class UpdateUserEvent {
    private String id;
    private User user;
}
