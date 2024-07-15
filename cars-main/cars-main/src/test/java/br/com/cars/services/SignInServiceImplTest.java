package br.com.cars.services;

import br.com.cars.domain.Car;
import br.com.cars.domain.User;
import br.com.cars.exception.LicensePlateAlreadyExistsException;
import br.com.cars.exception.SignInInvalidException;
import br.com.cars.repository.CarRepository;
import br.com.cars.repository.UserRepository;
import br.com.cars.service.impl.CarServiceImpl;
import br.com.cars.service.impl.SignInServiceImpl;
import br.com.cars.utils.TokenUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

import static br.com.cars.mocks.MockUtils.carMock;
import static br.com.cars.mocks.MockUtils.carRequestMock;
import static br.com.cars.mocks.MockUtils.signInRequestMock;
import static br.com.cars.mocks.MockUtils.userMock;
import static br.com.cars.mocks.MockUtils.userMockWithCar;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

public class SignInServiceImplTest {

    public static final String TOKEN = "HIUhiGI6YGyutgiohuHYgyuGYUgiuYG";

    @InjectMocks
    SignInServiceImpl signInService;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    TokenUtils tokenUtils;

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void shouldSignInUser(){
        var request = signInRequestMock();
        var user = userMock();

        doReturn(user).when(userRepository).findModelByLogin(request.getLogin());
        doReturn(true).when(bCryptPasswordEncoder).matches(request.getPassword(), user.getPassword());
        doReturn(new UsernamePasswordAuthenticationToken(user, request.getPassword())).when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        doReturn(TOKEN).when(tokenUtils).generateToken(any(User.class));

        var response = signInService.signIn(request);

        assertEquals(TOKEN, response.getToken());

    }

    @Test
    public void shouldThrowSignInInvalidExceptionWhenSignUserWithLoginInvalid(){
        var request = signInRequestMock();

        doReturn(null).when(userRepository).findModelByLogin(request.getLogin());

        Exception exception = assertThrows(SignInInvalidException.class, () -> {
            signInService.signIn(request);
        });

        assertEquals(exception.getMessage(), "Invalid login or password");

    }

    @Test
    public void shouldThrowSignInInvalidExceptionWhenSignUserWithPasswordInvalid(){
        var request = signInRequestMock();
        var user = userMock();

        doReturn(user).when(userRepository).findModelByLogin(request.getLogin());
        doReturn(false).when(bCryptPasswordEncoder).matches(request.getPassword(), user.getPassword());

        Exception exception = assertThrows(SignInInvalidException.class, () -> {
            signInService.signIn(request);
        });

        assertEquals(exception.getMessage(), "Invalid login or password");

    }
}
