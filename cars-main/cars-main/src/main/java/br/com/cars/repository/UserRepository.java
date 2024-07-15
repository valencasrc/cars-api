package br.com.cars.repository;

import br.com.cars.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByEmail(String email);

    Boolean existsByLogin(String login);

    Boolean existsByPassword(String password);

    UserDetails findByLogin(String login);

    Optional<User> findByUuid(String uuid);

    User findModelByLogin(String login);
}
