package br.com.cars.controller;

import br.com.cars.dto.request.UserRequest;
import br.com.cars.dto.request.UserUpdateRequest;
import br.com.cars.dto.response.UserResponse;
import br.com.cars.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private ModelMapper mapper;

    @Autowired
    UserService userService;

    public UserController(){
        this.mapper = new ModelMapper();
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    private  ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest request){
        var response = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(produces = "application/json")
    private ResponseEntity<List<UserResponse>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping(path = "/{uuid}", produces = "application/json")
    private ResponseEntity<UserResponse> getUser(@PathVariable String uuid){

        var user = userService.getUser(uuid);

        return ResponseEntity.ok(mapper.map(user, UserResponse.class));
    }

    @PutMapping(path = "/{uuid}", consumes = "application/json", produces = "application/json")
    private ResponseEntity<UserResponse> updateUser(@PathVariable String uuid, @Valid @RequestBody UserUpdateRequest request){
        return ResponseEntity.ok(userService.updateUser(uuid, request));
    }

    @DeleteMapping(path = "/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    private void deleteUser(@PathVariable String uuid){
        userService.deleteUser(uuid);
    }

}
