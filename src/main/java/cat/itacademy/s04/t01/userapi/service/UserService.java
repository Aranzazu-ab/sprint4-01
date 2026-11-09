package cat.itacademy.s04.t01.userapi.service;

import cat.itacademy.s04.t01.userapi.dtos.UserRequest;
import cat.itacademy.s04.t01.userapi.dtos.UserResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserResponse createUser(UserRequest request);

    List<UserResponse> getAllUsers(String name);

    UserResponse getUserById(UUID id);
}
