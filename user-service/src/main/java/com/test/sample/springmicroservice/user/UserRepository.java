package com.test.sample.springmicroservice.user;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User>{

    public Optional<User> findByUsername(String username);

    public Optional<User> findByEmail(String email);
}
