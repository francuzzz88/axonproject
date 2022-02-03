package ru.petrov.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.petrov.userservice.model.User;

public interface UserRepository extends JpaRepository<User, String> {
}
