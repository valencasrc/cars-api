package br.com.cars.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class CarRequest {
    @NotNull(message = "Missing fields")
    private Integer year;
    @NotEmpty(message = "Missing fields")
    private String licensePlate;
    @NotEmpty(message = "Missing fields")
    private String model;
    @NotNull(message = "Missing fields")
    private String color;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
