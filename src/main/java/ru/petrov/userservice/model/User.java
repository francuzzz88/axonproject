package ru.petrov.userservice.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

    private String id;
    private String email;
    private String lastname;
    private String firstname;
}
