package cat.itacademy.s04.t01.userapi.controllers;

import cat.itacademy.s04.t01.userapi.model.UserRequest;
import cat.itacademy.s04.t01.userapi.model.UserResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class UserController {
    private final List<UserResponse> users = new ArrayList<>();

    void clearUsers(){
        users.clear();
    }

    @GetMapping("/users")
    public List<UserResponse> getUsers(){
        return users;
    }

    @PostMapping("/users")
    public UserResponse createUser(@Valid @RequestBody UserRequest request){
        UserResponse user = new UserResponse(
                UUID.randomUUID(),
                request.name(),
                request.email()
        );
        users.add(user);
        return user;
    }

}
