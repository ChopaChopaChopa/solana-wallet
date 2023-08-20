package io.github.chopachopachopa.solanawallet.domain.model;

import java.math.BigDecimal;

public record Transfer(
    String fromAddress,
    String toAddress,
    BigDecimal amount,
    BigDecimal fee,
    String signature
) {
}
