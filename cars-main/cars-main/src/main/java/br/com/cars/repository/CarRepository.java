package br.com.cars.repository;

import br.com.cars.domain.Car;
import br.com.cars.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {

    Boolean existsByLicensePlate(String licensePlate);

}
