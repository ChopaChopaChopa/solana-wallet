package io.github.chopachopachopa.solanawallet.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND("User not found"),
    USER_ALREADY_REGISTERED("User already registered"),
    UNKNOWN("Unknown error"),
    CREDENTIALS("Invalid login or password"),
    BALANCE("Failed to get balance"),
    TRANSFER("Failed to send funds"),
    TRANSFER_LIST("Failed to get list of transfers");

    private final String message;
}
