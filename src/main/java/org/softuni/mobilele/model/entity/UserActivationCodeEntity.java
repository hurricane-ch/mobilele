package org.softuni.mobilele.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_activation_codes")
public class UserActivationCodeEntity extends BaseEntity {

  private String activationCode;

  private Instant created;

  @ManyToOne
  private UserEntity user;

}
