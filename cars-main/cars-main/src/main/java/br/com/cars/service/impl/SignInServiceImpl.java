package br.com.cars.service.impl;

import br.com.cars.domain.User;
import br.com.cars.dto.request.SignInRequest;
import br.com.cars.dto.response.SignInResponse;
import br.com.cars.exception.SignInInvalidException;
import br.com.cars.repository.UserRepository;
import br.com.cars.service.SignInService;
import br.com.cars.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static java.util.Objects.isNull;

@Service
public class SignInServiceImpl implements SignInService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenUtils tokenUtils;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    @Override
    public SignInResponse signIn(SignInRequest request) {
        validateSignIn(request);

        var usernamePassword = new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword());
        var auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenUtils.generateToken((User) auth.getPrincipal());

        return new SignInResponse(token);
    }

    private void validateSignIn(SignInRequest request) {
        var user = userRepository.findModelByLogin(request.getLogin());

        if (isNull(user)){
            throw new SignInInvalidException();
        }

        if(!bCryptPasswordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new SignInInvalidException();
        }

        updateSignInDate(user);
    }

    private void updateSignInDate(User user) {
        user.setLastSignIn(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        userRepository.save(user);
    }

}
