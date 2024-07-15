package br.com.cars.service;

import br.com.cars.dto.request.SignInRequest;
import br.com.cars.dto.response.SignInResponse;

public interface SignInService {

    SignInResponse signIn(SignInRequest request);
}
