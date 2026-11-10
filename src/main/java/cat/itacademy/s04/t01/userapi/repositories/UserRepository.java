package cat.itacademy.s04.t01.userapi.repositories;

import cat.itacademy.s04.t01.userapi.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    User save (User user);
    List<User> findAll();
    Optional<User> findById(UUID id);
    List<User> searchByName (String name);
    boolean existByEmail (String email);
    void clear();

}
