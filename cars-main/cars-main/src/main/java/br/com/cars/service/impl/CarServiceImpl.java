package br.com.cars.service.impl;

import br.com.cars.domain.Car;
import br.com.cars.domain.User;
import br.com.cars.dto.request.CarRequest;
import br.com.cars.dto.response.CarResponse;
import br.com.cars.exception.CarNotFoundException;
import br.com.cars.exception.LicensePlateAlreadyExistsException;
import br.com.cars.repository.CarRepository;
import br.com.cars.service.CarService;
import br.com.cars.utils.TokenUtils;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    private ModelMapper mapper;

    @Autowired
    CarRepository carRepository;

    @Autowired
    TokenUtils tokenUtils;

    public CarServiceImpl(){
        mapper = new ModelMapper();
    }

    @Override
    public CarResponse createUserCar(CarRequest request, String token) {
        validateCarRequest(request);

        var user = tokenUtils.getUserByToken(token);

        var car = mapper.map(request, Car.class);
        car.setUser(user);

        var savedCar = carRepository.save(car);

        return mapper.map(savedCar, CarResponse.class);
    }

    @Override
    public List<Car> createCars(List<CarRequest> request, User user) {
        var cars = new ArrayList<Car>();
        request.forEach(carReq -> {
            var car = mapper.map(carReq, Car.class);
            car.setUser(user);
            cars.add(car);
        });
        return carRepository.saveAll(cars);
    }

    @Override
    public List<CarResponse> getUserAllCars(String token) {
        var user = tokenUtils.getUserByToken(token);

        var response = new ArrayList<CarResponse>();

        user.getCars().forEach(car -> response.add(mapper.map(car, CarResponse.class)));

        return response;
    }

    @Override
    public Car getUserCarByUuid(String uuid, String token) {
        var user = tokenUtils.getUserByToken(token);

        if(CollectionUtils.isEmpty(user.getCars())){
            throw new CarNotFoundException();
        }

        var car = user.getCars().stream().filter(c -> c.getUuid().equals(uuid)).findFirst();

        if(car.isEmpty()){
            throw new CarNotFoundException();
        }

        return car.get();
    }

    @Override
    public CarResponse updateUserCarByUuid(String uuid, CarRequest carUpdate, String token) {
        var car = getUserCarByUuid(uuid, token);

        if (!car.getLicensePlate().equals(carUpdate.getLicensePlate())) {
            validateCarRequest(carUpdate);
        }

        BeanUtils.copyProperties(carUpdate, car);
        var carUpdated = carRepository.save(car);

        return mapper.map(carUpdated, CarResponse.class);
    }

    @Override
    @Transactional
    public void deleteUserCarByUuid(String uuid, String token) {
        var car = getUserCarByUuid(uuid, token);
        carRepository.delete(car);
    }

    private void validateCarRequest(CarRequest request) {
        if (carRepository.existsByLicensePlate(request.getLicensePlate())){
            throw new LicensePlateAlreadyExistsException();
        }
    }
}
