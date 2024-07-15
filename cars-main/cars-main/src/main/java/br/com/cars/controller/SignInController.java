package br.com.cars.controller;

import br.com.cars.dto.request.SignInRequest;
import br.com.cars.dto.response.SignInResponse;
import br.com.cars.service.SignInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/signin")
public class SignInController {

    @Autowired
    SignInService signInService;

    @PostMapping
    private ResponseEntity<SignInResponse> signIn(@RequestBody SignInRequest request){
        var response = signInService.signIn(request);
        return ResponseEntity.ok(response);
    }

}
