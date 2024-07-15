package br.com.cars.controller;

import br.com.cars.dto.request.CarRequest;
import br.com.cars.dto.request.UserRequest;
import br.com.cars.dto.response.CarResponse;
import br.com.cars.dto.response.UserResponse;
import br.com.cars.service.CarService;
import br.com.cars.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    private ModelMapper mapper;

    @Autowired
    CarService carService;

    public CarController(){
        this.mapper = new ModelMapper();
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    private ResponseEntity<CarResponse> createUserCar(@Valid @RequestBody CarRequest request,
                                      @RequestHeader("Authorization") String token){
        var response = carService.createUserCar(request, token);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(produces = "application/json")
    private ResponseEntity<List<CarResponse>> getUserAllCars(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok(carService.getUserAllCars(token));
    }

    @GetMapping(path = "/{uuid}", produces = "application/json")
    private ResponseEntity<CarResponse> getUserCarByUuid(@PathVariable String uuid,
                                                          @RequestHeader("Authorization") String token){

        var car = carService.getUserCarByUuid(uuid, token);

        return ResponseEntity.ok(mapper.map(car, CarResponse.class));
    }

    @PutMapping(path = "/{uuid}", consumes = "application/json", produces = "application/json")
    private ResponseEntity<CarResponse> updateUserCarByUuid(@PathVariable String uuid,
                                                           @Valid @RequestBody CarRequest request,
                                                           @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(carService.updateUserCarByUuid(uuid, request, token));
    }

    @DeleteMapping(path = "/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    private void deleteUserCarByUuid(@PathVariable String uuid, @RequestHeader("Authorization") String token){
        carService.deleteUserCarByUuid(uuid, token);
    }

}
