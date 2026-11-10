package cat.itacademy.s04.t01.userapi.controllers;

import cat.itacademy.s04.t01.userapi.exceptions.UserNotFoundException;
import cat.itacademy.s04.t01.userapi.dtos.UserRequest;
import cat.itacademy.s04.t01.userapi.dtos.UserResponse;
import cat.itacademy.s04.t01.userapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<UserResponse> getAllUsers(
            @RequestParam(required = false) String name){
            return userService.getAllUsers(name);
    }

    @PostMapping("/users")
    public UserResponse createUser(@Valid @RequestBody UserRequest request){

        return userService.createUser(request);
    }

    @GetMapping("/users/{id}")
    public UserResponse getUserByID (@PathVariable UUID id){

        return userService.getUserById(id);
    }

}
