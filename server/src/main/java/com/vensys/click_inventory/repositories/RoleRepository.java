package com.vensys.click_inventory.repositories;

import com.vensys.click_inventory.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Roles, UUID> {
  Optional<Roles> findByName(String name);
  boolean existsByName(String name);
  boolean existsByNameAndIdNot(String name, UUID id);
}
