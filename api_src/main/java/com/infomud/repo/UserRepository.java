package com.infomud.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.infomud.model.security.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findOneByUserName(String username);
}

