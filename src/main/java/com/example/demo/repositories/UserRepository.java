package com.example.demo.repositories;


import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {
  List<User> findAll();

  Optional<User> findByUsername(String username);

  Optional<User> findByEmail(String email);

//  @Modifying
//  @Query("update User set role = :role where username = :username")
//  void updateUserRole(@Param("username") String username, @Param("role")Role role);

}


