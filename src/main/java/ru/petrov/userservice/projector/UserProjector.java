package ru.petrov.userservice.projector;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import ru.petrov.userservice.events.RegisterUserEvent;
import ru.petrov.userservice.repository.UserRepository;

@Component
public class UserProjector {
    private final UserRepository repository;

    public UserProjector(UserRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void handle(RegisterUserEvent event){
        repository.save(event.getUser());
    }
}
