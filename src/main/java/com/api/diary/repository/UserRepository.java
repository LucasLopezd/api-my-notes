package com.api.diary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.diary.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    boolean existsByEmail(String email);

    boolean existsById(Long id);

    User findByEmail(String email);

    List<User> findByEnabledTrue();

    List<User> findByEnabledFalse();
}
