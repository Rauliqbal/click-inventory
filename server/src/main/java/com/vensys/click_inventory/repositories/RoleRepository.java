package com.vensys.click_inventory.repositories;

import com.vensys.click_inventory.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Roles, String> {
  Optional<Roles> findByName(String name);
  boolean existsByName(String name);
}
