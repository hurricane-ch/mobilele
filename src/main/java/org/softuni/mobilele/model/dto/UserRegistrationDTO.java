package org.softuni.mobilele.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.softuni.mobilele.model.validation.PasswordMatch;
import org.softuni.mobilele.model.validation.UniqueUserEmail;

@PasswordMatch(password = "password", confirmPassword = "confirmPassword")
public record UserRegistrationDTO(@NotEmpty String firstName,
                                  @NotEmpty String lastName,
                                  @NotNull @Email @UniqueUserEmail String email,
                                  String password,
                                  String confirmPassword) {
}
