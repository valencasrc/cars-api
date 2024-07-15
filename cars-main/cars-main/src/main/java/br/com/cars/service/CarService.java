package br.com.cars.service;

import br.com.cars.domain.Car;
import br.com.cars.domain.User;
import br.com.cars.dto.request.CarRequest;
import br.com.cars.dto.response.CarResponse;

import java.util.List;

public interface CarService {

    CarResponse createUserCar(CarRequest request, String token);

    List<Car> createCars(List<CarRequest> request, User user);

    List<CarResponse> getUserAllCars(String token);

    Car getUserCarByUuid(String uuid, String token);

    CarResponse updateUserCarByUuid(String uuid, CarRequest carUpdate, String token);

    void deleteUserCarByUuid(String uuid, String token);
}
