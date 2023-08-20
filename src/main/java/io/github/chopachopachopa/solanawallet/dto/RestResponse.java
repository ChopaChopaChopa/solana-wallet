package io.github.chopachopachopa.solanawallet.dto;

public record RestResponse<T>(
    Status status,
    T data,
    String error
) {
    public enum Status {
        OK,
        ERROR
    }

    public static <T> RestResponse<T> ok(T data) {
        return new RestResponse<>(Status.OK, data, null);
    }

    public static <T> RestResponse<T> ok() {
        return ok(null);
    }
}