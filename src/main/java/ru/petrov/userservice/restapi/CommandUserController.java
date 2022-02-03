package ru.petrov.userservice.restapi;

import lombok.Builder;
import lombok.Data;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.petrov.userservice.command.DeleteUserCommand;
import ru.petrov.userservice.command.RegisterUserCommand;
import ru.petrov.userservice.command.UpdateUserCommand;
import ru.petrov.userservice.model.User;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
public class CommandUserController {

    private final CommandGateway commandGateway;

    public CommandUserController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/account")
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto){
        var id = UUID.randomUUID().toString();
        var newUser = User.builder()
                .id(id)
                .email(userDto.getEmail())
                .firstname(userDto.getFirstname())
                .lastname(userDto.getLastname())
                .build();
        var command = RegisterUserCommand.builder()
                .id(id)
                .user(newUser)
                .build();
        commandGateway.send(command);
        return ResponseEntity.ok(command.getUser());
    }

    @PutMapping("/account/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody UserDto userDto){
        var updateUser = User.builder()
                .id(id)
                .email(userDto.getEmail())
                .firstname(userDto.getFirstname())
                .lastname(userDto.getLastname())
                .build();
        UpdateUserCommand command = UpdateUserCommand.builder()
                .id(id)
                .user(updateUser)
                .build();
        commandGateway.send(command);
        return ResponseEntity.ok(command.getUser());
    }

    @DeleteMapping("/account/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id){
        var command = DeleteUserCommand.builder()
                .id(id)
                .build();
        commandGateway.send(command);
        return ResponseEntity.ok("User deleted");
    }

    @Builder
    @Data
    private static class UserDto {
        private String email;
        private String lastname;
        private String firstname;

    }
}
