package br.com.cars.mocks;

import br.com.cars.domain.Car;
import br.com.cars.domain.User;
import br.com.cars.dto.request.CarRequest;
import br.com.cars.dto.request.SignInRequest;
import br.com.cars.dto.request.UserRequest;
import br.com.cars.dto.request.UserUpdateRequest;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MockUtils {

    public static UserRequest userRequestMock(){
        var request = new UserRequest();
        request.setFirstName("Test");
        request.setLastName("User");
        request.setEmail("test@test.com.br");
        request.setBirthday(LocalDate.of(2000, Month.APRIL, 5));
        request.setLogin("usertest");
        request.setPassword("123456");
        request.setPhone("11999999999");
        request.setCars(Collections.singletonList(carRequestMock()));
        return request;
    }

    public static User userMock(){
        var user = new User();
        user.setFirstName("Test");
        user.setLastName("User");
        user.setEmail("test@test.com.br");
        user.setBirthday(LocalDate.of(2000, Month.APRIL, 5));
        user.setLogin("usertest");
        user.setPassword("123456");
        user.setPhone("11999999999");
        return user;
    }

    public static CarRequest carRequestMock(){
        var carRequest = new CarRequest();
        carRequest.setYear(2023);
        carRequest.setModel("BMW");
        carRequest.setLicensePlate("TEST-1111");
        carRequest.setColor("Branco");
        return carRequest;
    }

    public static Car carMock(){
        var car = new Car();
        car.setUuid("CAR-123-145");
        car.setYear(2023);
        car.setModel("BMW");
        car.setLicensePlate("TEST-1111");
        car.setColor("Branco");
        return car;
    }

    public static UserUpdateRequest userUpdateRequestMock(){
        var request = new UserUpdateRequest();
        request.setFirstName("Test Update");
        request.setLastName("User");
        request.setEmail("test@test.com.br");
        request.setBirthday(LocalDate.of(2000, Month.APRIL, 5));
        request.setLogin("usertest");
        request.setPhone("11999999999");
        return request;
    }

    public static User userMockWithCar(){
        var user = new User();
        user.setFirstName("Test");
        user.setLastName("User");
        user.setEmail("test@test.com.br");
        user.setBirthday(LocalDate.of(2000, Month.APRIL, 5));
        user.setLogin("usertest");
        user.setPassword("123456");
        user.setPhone("11999999999");
        user.setCars(List.of(carMock()));
        return user;
    }

    public static SignInRequest signInRequestMock(){
        var request = new SignInRequest();
        request.setLogin("usertest");
        request.setPassword("123456");
        return request;
    }

}
