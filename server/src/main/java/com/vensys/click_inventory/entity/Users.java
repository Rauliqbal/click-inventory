package com.vensys.click_inventory.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
  private String email;

  @ManyToOne
  @JoinColumn(name = "role_id", referencedColumnName = "id")
  private Roles role;

  @NotBlank
  @Size(max = 100)
  private String password;
}
