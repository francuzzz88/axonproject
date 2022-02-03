package ru.petrov.userservice.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import ru.petrov.userservice.model.User;

@Data
@Builder
public class UpdateUserCommand {
    @TargetAggregateIdentifier
    private String id;
    private User user;
}
