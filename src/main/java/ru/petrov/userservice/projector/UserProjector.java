package ru.petrov.userservice.projector;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;
import ru.petrov.userservice.events.DeleteUserEvent;
import ru.petrov.userservice.events.RegisterUserEvent;
import ru.petrov.userservice.events.UpdateUserEvent;
import ru.petrov.userservice.model.User;
import ru.petrov.userservice.query.GetAllUsersQuery;
import ru.petrov.userservice.query.GetUserByIdQuery;
import ru.petrov.userservice.repository.UserRepository;

import java.util.List;
import java.util.Optional;

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

    @EventHandler
    public void handle(UpdateUserEvent event){
        repository.save(event.getUser());
    }

    @EventHandler
    public void handle(DeleteUserEvent event){
        repository.deleteById(event.getId());
    }

    @QueryHandler
    public List<User> getAllUsers(GetAllUsersQuery query){
        return repository.findAll();
    }

    @QueryHandler
    public User getUserById(GetUserByIdQuery query){
        Optional<User> user = repository.findById(query.getId());
        return user.orElse(null);
    }
}
