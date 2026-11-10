package cat.itacademy.s04.t01.userapi.service;

import cat.itacademy.s04.t01.userapi.dtos.UserRequest;
import cat.itacademy.s04.t01.userapi.dtos.UserResponse;
import cat.itacademy.s04.t01.userapi.exceptions.EmailAlreadyExistException;
import cat.itacademy.s04.t01.userapi.model.User;
import cat.itacademy.s04.t01.userapi.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertTrue;


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

    @Test
    void createUserSaveUserWhenEmailNotExists (){
        when(userRepository.existByEmail("john@example.com"))
                .thenReturn(false);

        when(userRepository.save(any(User.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        UserRequest userRequest = new UserRequest("John", "john@example.com");

        UserResponse userResponse = userServiceImp.createUser(userRequest);

        assertNotNull(userResponse.id());
        assertEquals("John", userResponse.name());
        assertEquals("john@example.com", userResponse.email());

        verify(userRepository).save(any(User.class));

    }
}

