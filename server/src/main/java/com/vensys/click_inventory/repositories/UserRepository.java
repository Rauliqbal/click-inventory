package com.vensys.click_inventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends JpaRepository<User, String> {

}
