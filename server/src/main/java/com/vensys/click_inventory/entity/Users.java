package com.vensys.click_inventory.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")

public class Users {
    @Id
    @UuidGenerator
    private UUID id;


    @NotBlank
    @Size(max = 100)
    private String username;

    @NotBlank
    @Size(max = 100)
    private String fullname;

    @NotBlank
    @Size(max = 100)
    private String password;
}
