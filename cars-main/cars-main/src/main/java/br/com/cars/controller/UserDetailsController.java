package br.com.cars.controller;

import br.com.cars.dto.request.UserRequest;
import br.com.cars.dto.response.UserDetailsResponse;
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
@RequestMapping("/api/me")
public class UserDetailsController {

    private ModelMapper mapper;

    @Autowired
    UserService userService;

    public UserDetailsController(){
        this.mapper = new ModelMapper();
    }

    @GetMapping(produces = "application/json")
    private ResponseEntity<UserDetailsResponse> getUserLoggedDetails(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok(userService.getUserDetails(token));
    }

}
