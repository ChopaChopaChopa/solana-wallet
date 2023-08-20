package io.github.chopachopachopa.solanawallet.dto;

import java.math.BigDecimal;

public record TransferDto(
    String fromAddress,
    String toAddress,
    BigDecimal amount,
    BigDecimal fee,
    String signature
) {
}
