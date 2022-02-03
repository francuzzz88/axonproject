package ru.petrov.userservice.restapi;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.petrov.userservice.exeption.NotFoundException;
import ru.petrov.userservice.model.User;
import ru.petrov.userservice.query.GetAllUsersQuery;
import ru.petrov.userservice.query.GetUserByIdQuery;

import java.util.List;

@RestController
public class QueryUserController {

    private final QueryGateway queryGateway;

    public QueryUserController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping("/account")
    public ResponseEntity<List<User>> getAllUsers() {
        var query = GetAllUsersQuery.builder().build();

        List<User> users = queryGateway.query(query, ResponseTypes.multipleInstancesOf(User.class)).join();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) throws NotFoundException {
        var query = GetUserByIdQuery.builder().id(id).build();
        User user = queryGateway.query(query, ResponseTypes.instanceOf(User.class)).join();
        if (user == null) {
            throw new NotFoundException();
        }
        return ResponseEntity.ok(user);
    }

    @ExceptionHandler(value = NotFoundException.class)
    private ResponseEntity<String> exception(){
        return ResponseEntity.ok("User Not Found");
    }
}
