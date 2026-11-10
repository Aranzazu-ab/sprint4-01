package cat.itacademy.s04.t01.userapi.service;

import cat.itacademy.s04.t01.userapi.dtos.UserRequest;
import cat.itacademy.s04.t01.userapi.dtos.UserResponse;
import cat.itacademy.s04.t01.userapi.exceptions.EmailAlreadyExistException;
import cat.itacademy.s04.t01.userapi.exceptions.UserNotFoundException;
import cat.itacademy.s04.t01.userapi.model.User;
import cat.itacademy.s04.t01.userapi.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;

    public UserServiceImp(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserResponse createUser(UserRequest request) {

        if (userRepository.existByEmail(request.email())){
            throw new EmailAlreadyExistException("Email already exists: ");
        }

        User user = new User (UUID.randomUUID(), request.name(), request.email());

        User savedUser = userRepository.save(user);
        return new UserResponse(savedUser.id(), savedUser.name(), savedUser.email());
    }

    @Override
    public List<UserResponse> getAllUsers(String name) {
        List<User> users;
        if (name == null || name.isBlank()){
            users = userRepository.findAll();
        } else {
            users = userRepository.searchByName(name);
        }
        return users.stream()
                .map(u -> new UserResponse(u.id(),u.name(),u.email()))
                .toList();
    }

    @Override
    public UserResponse getUserById(UUID id) {
        User user =userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("User not found: "+ id));

        return new UserResponse(user.id(), user.name(), user.email());
    }
}
