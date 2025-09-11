package com.demo.repo;

import com.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);

  @Query("select u from User u left join fetch u.roles where u.username = :username")
  Optional<User> findWithRolesByUsername(@Param("username") String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);
}
