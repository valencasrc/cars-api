package br.com.cars.services;

import br.com.cars.domain.User;
import br.com.cars.exception.EmailAlreadyExistsException;
import br.com.cars.exception.LoginAlreadyExistsException;
import br.com.cars.exception.UserNotFoundException;
import br.com.cars.repository.UserRepository;
import br.com.cars.service.CarService;
import br.com.cars.service.impl.UserServiceImpl;
import br.com.cars.utils.TokenUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static br.com.cars.mocks.MockUtils.carMock;
import static br.com.cars.mocks.MockUtils.userMock;
import static br.com.cars.mocks.MockUtils.userRequestMock;
import static br.com.cars.mocks.MockUtils.userUpdateRequestMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

public class UserServiceImplTest {

    public static final String ENCODED_PASSWORD = "HIUhiGI6YGyutgiohuHYgyuGYUgiuYG";
    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository repository;

    @Mock
    PasswordEncoder bCryptPasswordEncoder;

    @Mock
    TokenUtils tokenUtils;

    @Mock
    CarService carService;

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void shouldCreateUser(){
        var request = userRequestMock();
        var user = userMock();

        doReturn(false).when(repository).existsByEmail(request.getEmail());
        doReturn(false).when(repository).existsByLogin(request.getLogin());
        doReturn(ENCODED_PASSWORD).when(bCryptPasswordEncoder).encode(request.getPassword());
        doReturn(user).when(repository).save(any(User.class));
        doReturn(null).when(carService).createCars(request.getCars(), user);

        var response = userService.createUser(request);

        assertEquals(request.getFirstName(), response.getFirstName());
        assertEquals(request.getLastName(), response.getLastName());
        assertEquals(request.getEmail(), response.getEmail());
        assertEquals(request.getBirthday(), response.getBirthday());
        assertEquals(request.getPhone(), response.getPhone());
        assertEquals(request.getLogin(), response.getLogin());

    }

    @Test
    public void shouldThrowEmailAlreadyExistsWhenCreateUser(){
        var request = userRequestMock();

        doReturn(true).when(repository).existsByEmail(request.getEmail());

        Exception exception = assertThrows(EmailAlreadyExistsException.class, () -> {
            userService.createUser(request);
        });

        assertEquals(exception.getMessage(), "Email already exists");

    }

    @Test
    public void shouldThrowLoginAlreadyExistsWhenCreateUser(){
        var request = userRequestMock();

        doReturn(false).when(repository).existsByEmail(request.getEmail());
        doReturn(true).when(repository).existsByLogin(request.getLogin());

        Exception exception = assertThrows(LoginAlreadyExistsException.class, () -> {
            userService.createUser(request);
        });

        assertEquals(exception.getMessage(), "Login already exists");

    }

    @Test
    public void shouldGetAllUsers(){
        var user = userMock();

        doReturn(Collections.singletonList(user)).when(repository).findAll();

        var response = userService.getAllUsers();

        assertNotNull(response);
    }

    @Test
    public void shouldThrowExceptionWhenGetAllUsersEmpty(){
        doReturn(Collections.emptyList()).when(repository).findAll();

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            userService.getAllUsers();
        });

        assertEquals(exception.getMessage(), "User not found");
    }

    @Test
    public void shouldGetUser(){
        var uuid = "USR-123-145";
        var user = userMock();

        doReturn(Optional.of(user)).when(repository).findByUuid(uuid);

        var response = userService.getUser(uuid);

        assertNotNull(response);
    }

    @Test
    public void shouldThrowExceptionNotFoundWhenGetUser(){
        var uuid = "USR-123-145";
        doReturn(Optional.empty()).when(repository).findByUuid(uuid);

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            userService.getUser(uuid);
        });

        assertEquals(exception.getMessage(), "User not found");
    }

    @Test
    public void shouldUpdateUser(){
        var uuid = "USR-123-145";
        var request = userUpdateRequestMock();
        var user = userMock();

        doReturn(Optional.of(user)).when(repository).findByUuid(uuid);
        doReturn(user).when(repository).save(any(User.class));

        var response = userService.updateUser(uuid, request);

        assertEquals(request.getFirstName(), response.getFirstName());
        assertEquals(request.getLastName(), response.getLastName());
        assertEquals(request.getEmail(), response.getEmail());
        assertEquals(request.getBirthday(), response.getBirthday());
        assertEquals(request.getPhone(), response.getPhone());
        assertEquals(request.getLogin(), response.getLogin());
    }

    @Test
    public void shouldThrowExceptionNotFoundWhenUpdateUser(){
        var uuid = "USR-123-145";
        var request = userUpdateRequestMock();

        doReturn(Optional.empty()).when(repository).findByUuid(uuid);

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            userService.updateUser(uuid, request);
        });

        assertEquals(exception.getMessage(), "User not found");
    }

    @Test
    public void shouldDeleteUser(){
        var uuid = "USR-123-145";
        var user = userMock();

        doReturn(Optional.of(user)).when(repository).findByUuid(uuid);

        userService.deleteUser(uuid);

        verify(repository).delete(any(User.class));
    }

    @Test
    public void shouldThrowExceptionNotFoundWhenDeleteUser(){
        var uuid = "USR-123-145";

        doReturn(Optional.empty()).when(repository).findByUuid(uuid);

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            userService.deleteUser(uuid);
        });

        assertEquals(exception.getMessage(), "User not found");
    }
}
