package br.com.cars.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public class UserRequest {
    @NotEmpty(message = "Missing fields")
    private String firstName;
    @NotEmpty(message = "Missing fields")
    private String lastName;
    @NotEmpty(message = "Missing fields")
    private String email;
    @NotNull(message = "Missing fields")
    private LocalDate birthday;
    @NotEmpty(message = "Missing fields")
    private String login;
    @NotEmpty(message = "Missing fields")
    private String password;
    @NotEmpty(message = "Missing fields")
    private String phone;
    private List<CarRequest> cars;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<CarRequest> getCars() {
        return cars;
    }

    public void setCars(List<CarRequest> cars) {
        this.cars = cars;
    }
}
