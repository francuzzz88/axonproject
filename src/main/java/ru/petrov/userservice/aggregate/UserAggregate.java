package ru.petrov.userservice.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import ru.petrov.userservice.command.DeleteUserCommand;
import ru.petrov.userservice.command.RegisterUserCommand;
import ru.petrov.userservice.command.UpdateUserCommand;
import ru.petrov.userservice.events.DeleteUserEvent;
import ru.petrov.userservice.events.RegisterUserEvent;
import ru.petrov.userservice.events.UpdateUserEvent;
import ru.petrov.userservice.model.User;

import java.util.UUID;

@Aggregate
public class UserAggregate {

    @AggregateIdentifier
    private String id;
    private User user;

    public UserAggregate() {
    }

    @CommandHandler
    public UserAggregate(RegisterUserCommand command) {
        var event = RegisterUserEvent.builder()
                .id(command.getId())
                .user(command.getUser())
                .build();
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(UpdateUserCommand command){
        var event = UpdateUserEvent.builder()
                .id(UUID.randomUUID().toString())
                .user(command.getUser())
                .build();
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(DeleteUserCommand command){
        var event = DeleteUserEvent.builder()
                .id(command.getId())
                .build();
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(RegisterUserEvent event){
        this.id = event.getId();
        this.user = event.getUser();
    }

    @EventSourcingHandler
    public void on(UpdateUserEvent event){
        this.user = event.getUser();
    }
    @EventSourcingHandler
    public void on(DeleteUserEvent event){
        AggregateLifecycle.markDeleted();
    }
}
