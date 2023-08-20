package io.github.chopachopachopa.solanawallet.exception;

import io.github.chopachopachopa.solanawallet.dto.RestResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public final class ExceptionGlobalHandler {
    private static final String ERROR_SEPARATOR = ", ";

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<RestResponse<Void>> handle(Exception ex) {
        String errorMessage;

        if (ex instanceof MethodArgumentNotValidException e) {
            errorMessage = extractValidationErrors(e);
        } else {
            errorMessage = ex.getMessage();
        }

        log.warn("Exception handled: {}", ex.toString());
        log.info("Exception stacktrace: ", ex);
        return ResponseEntity.ok(
            new RestResponse<>(RestResponse.Status.ERROR, null, errorMessage)
        );
    }

    private String extractValidationErrors(MethodArgumentNotValidException e) {
        final StringBuilder sb = new StringBuilder();

        val errorList = e.getBindingResult().getAllErrors();
        val errorListSize = errorList.size();

        for (int i = 0; i < errorListSize; i++) {
            val error = errorList.get(i);
            sb.append(error.getDefaultMessage());

            if (i < errorListSize - 1) {
                sb.append(ERROR_SEPARATOR);
            }
        }

        if (!sb.isEmpty()) {
            return sb.toString();
        }

        return ErrorCode.UNKNOWN.getMessage();
    }
}
