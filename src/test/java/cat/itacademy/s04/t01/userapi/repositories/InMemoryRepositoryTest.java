package cat.itacademy.s04.t01.userapi.repositories;

import cat.itacademy.s04.t01.userapi.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryRepositoryTest {
    private InMemoryRepository repository;

    @BeforeEach
    void setUp(){
        repository = new InMemoryRepository();
    }

    @Test
    void saveAndFindByIdReturnUser() {
        User user = new User(UUID.randomUUID(), "Ada", "ada@example.com");
        repository.save(user);

        assertTrue(repository.findById(user.id()).isPresent());
        assertEquals("Ada", repository.findById(user.id()).get().name());
    }

    @Test
    void findByIdReturnsEmptyWhenMissing(){
        assertTrue(repository.findById(UUID.randomUUID()).isEmpty());
    }

    @Test
    void findAllReturnsAllUsers(){
        repository.save(new User(UUID.randomUUID(), "Ada Perez", "ada@example.com"));
        repository.save(new User(UUID.randomUUID(), "John Marquez", "john@example.com"));

        List<User> result = repository.findAll();
        assertEquals(2,result.size());
    }

    @Test
    void searchByNameReturnsMatchUser(){
        repository.save(new User(UUID.randomUUID(), "Ada Perez", "ada@example.com"));
        repository.save(new User(UUID.randomUUID(), "John Marquez", "john@example.com"));

        List<User> result = repository.searchByName("Ada");
        assertEquals(1,result.size());
        assertEquals("Ada Perez", result.getFirst().name());
    }

    @Test
    void searchByNameIsCaseInsensitive() {
        repository.save(new User(UUID.randomUUID(), "Ada Lovelace", "ada@example.com"));

        assertEquals(1, repository.searchByName("ADA").size());
        assertEquals(1, repository.searchByName("ada").size());
        assertEquals(1, repository.searchByName("AdA").size());
    }

    @Test
    void existByEmailReturnsTrue(){
        repository.save(new User(UUID.randomUUID(), "Ada", "ada@example.com"));

        assertTrue(repository.existByEmail("ada@example.com"));
    }

    @Test
    void existByEmailReturnsFalseWhenMissing(){
        repository.save(new User(UUID.randomUUID(), "Ada", "ada@example.com"));

        assertFalse(repository.existByEmail("fake@fake.com"));
    }

}
