package br.com.cars.dto.response;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class UserDetailsResponse {
    private String uuid;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthday;
    private String login;
    private String phone;
    private List<CarResponse> cars;
    private LocalDateTime createdAt;
    private LocalDateTime lastSignIn;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<CarResponse> getCars() {
        return cars;
    }

    public void setCars(List<CarResponse> cars) {
        this.cars = cars;
    }

    public LocalDateTime getLastSignIn() {
        return lastSignIn;
    }

    public void setLastSignIn(LocalDateTime lastSignIn) {
        this.lastSignIn = lastSignIn;
    }
}
