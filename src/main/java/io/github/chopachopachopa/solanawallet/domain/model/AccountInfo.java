package io.github.chopachopachopa.solanawallet.domain.model;

import java.math.BigDecimal;

public record AccountInfo(
    String username,
    String address,
    BigDecimal usdBalance,
    BigDecimal balance
) {
}
