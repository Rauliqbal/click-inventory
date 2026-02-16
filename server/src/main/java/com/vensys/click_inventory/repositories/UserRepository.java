package com.vensys.click_inventory.repositories;

import com.vensys.click_inventory.entity.Roles;
import com.vensys.click_inventory.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, String> {
  boolean existsByUsername(String username);
  boolean existsByRole(Roles role);
}
