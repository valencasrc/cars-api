package br.com.cars.services;

import br.com.cars.domain.Car;
import br.com.cars.domain.User;
import br.com.cars.dto.request.UserRequest;
import br.com.cars.exception.EmailAlreadyExistsException;
import br.com.cars.exception.LicensePlateAlreadyExistsException;
import br.com.cars.exception.LoginAlreadyExistsException;
import br.com.cars.exception.UserNotFoundException;
import br.com.cars.repository.CarRepository;
import br.com.cars.repository.UserRepository;
import br.com.cars.service.CarService;
import br.com.cars.service.UserService;
import br.com.cars.service.impl.CarServiceImpl;
import br.com.cars.service.impl.UserServiceImpl;
import br.com.cars.utils.TokenUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static br.com.cars.mocks.MockUtils.carMock;
import static br.com.cars.mocks.MockUtils.carRequestMock;
import static br.com.cars.mocks.MockUtils.userMock;
import static br.com.cars.mocks.MockUtils.userMockWithCar;
import static br.com.cars.mocks.MockUtils.userRequestMock;
import static br.com.cars.mocks.MockUtils.userUpdateRequestMock;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

public class CarServiceImplTest {

    public static final String TOKEN = "HIUhiGI6YGyutgiohuHYgyuGYUgiuYG";
    @InjectMocks
    CarServiceImpl carService;

    @Mock
    CarRepository repository;

    @Mock
    TokenUtils tokenUtils;

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void shouldCreateCarByUser(){
        var user = userMock();
        var request = carRequestMock();
        var car = carMock();

        doReturn(user).when(tokenUtils).getUserByToken(TOKEN);
        doReturn(car).when(repository).save(any(Car.class));

        var response = carService.createUserCar(request, TOKEN);

        assertEquals(request.getModel(), response.getModel());
        assertEquals(request.getYear(), response.getYear());
        assertEquals(request.getLicensePlate(), response.getLicensePlate());
        assertEquals(request.getColor(), response.getColor());

    }

    @Test
    public void shouldThrowLicensePlateAlreadyExistsWhenCreateCar(){
        var request = carRequestMock();

        doReturn(true).when(repository).existsByLicensePlate(request.getLicensePlate());

        Exception exception = assertThrows(LicensePlateAlreadyExistsException.class, () -> {
            carService.createUserCar(request, TOKEN);
        });

        assertEquals(exception.getMessage(), "License plate already exists");

    }

    @Test
    public void shouldCreateCarsByUser(){
        var user = userMock();
        var request = carRequestMock();
        var request2 = carRequestMock();
        request2.setLicensePlate("124");

        var car = carMock();
        var car2 = carMock();
        car2.setLicensePlate("124");

        doReturn(user).when(tokenUtils).getUserByToken(TOKEN);
        doReturn(asList(car, car2)).when(repository).saveAll(anyList());

        var response = carService.createCars(asList(request, request2), user);

        assertNotNull(response);
    }

    @Test
    public void shouldGetAllCarsByUser(){
        var user = userMockWithCar();

        doReturn(user).when(tokenUtils).getUserByToken(TOKEN);
        doReturn(Collections.singletonList(user)).when(repository).findAll();

        var response = carService.getUserAllCars(TOKEN);

        assertNotNull(response);
    }

    @Test
    public void shouldGetCarByUser(){
        var uuid = "CAR-123-145";
        var user = userMockWithCar();

        doReturn(user).when(tokenUtils).getUserByToken(TOKEN);

        var response = carService.getUserCarByUuid(uuid, TOKEN);

        assertNotNull(response);
    }

    @Test
    public void shouldUpdateCarByUser(){
        var uuid = "CAR-123-145";
        var request = carRequestMock();
        var user = userMockWithCar();
        var car = carMock();

        doReturn(user).when(tokenUtils).getUserByToken(TOKEN);
        doReturn(car).when(repository).save(any(Car.class));

        var response = carService.updateUserCarByUuid(uuid, request, TOKEN);

        assertEquals(request.getModel(), response.getModel());
        assertEquals(request.getYear(), response.getYear());
        assertEquals(request.getLicensePlate(), response.getLicensePlate());
        assertEquals(request.getColor(), response.getColor());
    }

    @Test
    public void shouldDeleteCar(){
        var uuid = "CAR-123-145";
        var user = userMockWithCar();

        doReturn(user).when(tokenUtils).getUserByToken(TOKEN);

        carService.deleteUserCarByUuid(uuid, TOKEN);

        verify(repository).delete(any(Car.class));
    }
}
