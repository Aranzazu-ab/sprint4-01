package cat.itacademy.s04.t01.userapi.controllers;

import cat.itacademy.s04.t01.userapi.exceptions.UserNotFoundException;
import cat.itacademy.s04.t01.userapi.model.UserRequest;
import cat.itacademy.s04.t01.userapi.model.UserResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

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
    public List<UserResponse> getUsers(
            @RequestParam(required = false) String name){
        if (name == null || name.isBlank()){
            return users;
        }

        return users.stream()
                .filter(u -> u.name().toLowerCase().contains(name.toLowerCase()))
                .toList();
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

    @GetMapping("/users/{id}")
    public UserResponse getUserbyID (@PathVariable UUID id){
        return users.stream()
                .filter(user -> user.id().equals(id))
                .findFirst()
                .orElseThrow(()-> new UserNotFoundException("User not found: "+ id));
    }




}
