package com.login.module.repository;


import com.login.module.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    Stream<User> findAllByName(String name);
}

