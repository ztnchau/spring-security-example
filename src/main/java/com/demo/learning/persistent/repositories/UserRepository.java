package com.demo.learning.persistent.repositories;

import com.demo.learning.persistent.entities.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

  User findByUsername(String username);


}
