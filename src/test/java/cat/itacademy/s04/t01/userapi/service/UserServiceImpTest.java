package cat.itacademy.s04.t01.userapi.service;

import cat.itacademy.s04.t01.userapi.dtos.UserRequest;
import cat.itacademy.s04.t01.userapi.exceptions.EmailAlreadyExistException;
import cat.itacademy.s04.t01.userapi.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceImpTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImp userServiceImp;

    @Test
    void createUserShouldThrowExceptionWhenEmailAlreadyExists(){
        when(userRepository.existByEmail("ada@example.com"))
                .thenReturn(true);

        UserRequest userRequest = new UserRequest("Ada", "ada@example.com");

        assertThrows(EmailAlreadyExistException.class,
                ()-> userServiceImp.createUser(userRequest));

        verify(userRepository, never()).save(any());
    }
}

