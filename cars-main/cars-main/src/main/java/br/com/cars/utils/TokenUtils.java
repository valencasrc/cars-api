package br.com.cars.utils;

import br.com.cars.domain.User;
import br.com.cars.exception.InvalidTokenException;
import br.com.cars.exception.UserNotFoundException;
import br.com.cars.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static java.util.Objects.nonNull;

@Service
public class TokenUtils {
    public static final String JWT_ISSUER = "user.api";
    @Value("${user.jwt.secret}")
    private String secret;

    @Autowired
    UserRepository userRepository;

    public String generateToken(User user){
        try{

            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer(JWT_ISSUER)
                    .withSubject(user.getLogin())
                    .withExpiresAt(LocalDateTime.now().plusMinutes(10).toInstant(ZoneOffset.of("-03:00")))
                    .sign(algorithm);

        } catch (JWTCreationException e){
            throw e;
        }
    }

    public String validateToken(String token){

        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer(JWT_ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException e){
            throw new InvalidTokenException();
        }
    }

    public User getUserByToken(String token){
           var login = validateToken(token.replace("Bearer ", ""));

           if(nonNull(login))  return userRepository.findModelByLogin(login);
           else throw new UserNotFoundException();
    }

}
