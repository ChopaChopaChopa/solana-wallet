package io.github.chopachopachopa.solanawallet.dto;

import io.github.chopachopachopa.solanawallet.util.validator.PasswordsEquals;
import jakarta.validation.constraints.NotBlank;


@PasswordsEquals(
    password = "password",
    confirmPassword = "confirmPassword"
)
public record RegUserDto(
    @NotBlank(message = "Invalid username")
    String username,
    @NotBlank(message = "Password is empty")
    String password,
    @NotBlank(message = "Confirm password is empty")
    String confirmPassword
) {
}
