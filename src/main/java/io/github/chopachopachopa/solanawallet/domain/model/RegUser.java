package io.github.chopachopachopa.solanawallet.domain.model;

public record RegUser(
    String username,
    String password,
    String confirmPassword
) {
}
