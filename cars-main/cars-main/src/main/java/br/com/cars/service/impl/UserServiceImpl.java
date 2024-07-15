package br.com.cars.service.impl;

import br.com.cars.domain.Car;
import br.com.cars.domain.User;
import br.com.cars.dto.request.UserRequest;
import br.com.cars.dto.request.UserUpdateRequest;
import br.com.cars.dto.response.UserDetailsResponse;
import br.com.cars.dto.response.UserResponse;
import br.com.cars.exception.EmailAlreadyExistsException;
import br.com.cars.exception.LoginAlreadyExistsException;
import br.com.cars.exception.UserNotFoundException;
import br.com.cars.repository.UserRepository;
import br.com.cars.service.CarService;
import br.com.cars.service.UserService;
import br.com.cars.utils.TokenUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final ModelMapper mapper;

    @Autowired
    UserRepository repository;

    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    TokenUtils tokenUtils;

    @Autowired
    CarService carService;

    public UserServiceImpl(){
        mapper = new ModelMapper();
    }

    @Override
    public UserResponse createUser(UserRequest request) {
        validateRequest(request.getEmail(), request.getLogin());
        var user = mapper.map(request, User.class);

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));

        var userSaved = repository.save(user);

        if(!CollectionUtils.isEmpty(request.getCars())){
            carService.createCars(request.getCars(), userSaved);
        }

        return mapper.map(userSaved, UserResponse.class);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        var users = repository.findAll();
        var response = new ArrayList<UserResponse>();

        if(CollectionUtils.isEmpty(users)) {
            throw new UserNotFoundException();
        }

        users.forEach(user -> response.add(mapper.map(user, UserResponse.class)));
        return response;

    }

    @Override
    public User getUser(String uuid) {
        var user = repository.findByUuid(uuid);

        if(user.isEmpty()){
            throw new UserNotFoundException("User not found");
        }

        return user.get();
    }

    @Override
    public UserResponse updateUser(String uuid, UserUpdateRequest userUpdate) {
        var user = getUser(uuid);

        if (!user.getEmail().equals(userUpdate.getEmail()) || !user.getLogin().equals(userUpdate.getLogin())){
            validateRequest(userUpdate.getEmail(), userUpdate.getLogin());
        }

        BeanUtils.copyProperties(userUpdate, user);
        var userSaved = repository.save(user);

        return mapper.map(userSaved, UserResponse.class);
    }

    @Override
    public void deleteUser(String uuid) {
        var user = getUser(uuid);
        repository.delete(user);
    }

    @Override
    public UserDetailsResponse getUserDetails(String token) {
        var user = tokenUtils.getUserByToken(token);
        return mapper.map(user, UserDetailsResponse.class);
    }

    private void validateRequest(String email, String login) {
        if (repository.existsByEmail(email)){
            throw new EmailAlreadyExistsException();
        } else if(repository.existsByLogin(login)){
            throw new LoginAlreadyExistsException();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       return repository.findByLogin(username);
    }
}
