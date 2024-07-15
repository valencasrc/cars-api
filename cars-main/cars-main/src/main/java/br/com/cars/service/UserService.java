package br.com.cars.service;

import br.com.cars.domain.User;
import br.com.cars.dto.request.UserRequest;
import br.com.cars.dto.request.UserUpdateRequest;
import br.com.cars.dto.response.UserDetailsResponse;
import br.com.cars.dto.response.UserResponse;
import br.com.cars.exception.EmailAlreadyExistsException;

import java.util.List;

public interface UserService {

    UserResponse createUser(UserRequest request);

    List<UserResponse> getAllUsers();

    User getUser(String uuid);

    UserResponse updateUser(String uuid, UserUpdateRequest userUpdate);

    void deleteUser(String uuid);

    UserDetailsResponse getUserDetails(String token);
}
