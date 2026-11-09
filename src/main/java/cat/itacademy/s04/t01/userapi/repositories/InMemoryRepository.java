package cat.itacademy.s04.t01.userapi.repositories;

import cat.itacademy.s04.t01.userapi.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class InMemoryRepository implements UserRepository {
    private final List<User> users = new ArrayList<>();

    @Override
    public void save(User user) {
        users.add(user);

    }

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public Optional<User> findById(UUID id) {
        return users.stream()
                .filter(u ->u.id().equals(id))
                .findFirst();
    }

    @Override
    public List<User> searchByName(String name) {
        return users.stream()
                .filter(u->u.name().toLowerCase().contains(name.toLowerCase()))
                .toList();
    }

    @Override
    public boolean existByEmail(String email) {
        return users.stream()
                .anyMatch(u -> u.email().equalsIgnoreCase(email));
    }
}
