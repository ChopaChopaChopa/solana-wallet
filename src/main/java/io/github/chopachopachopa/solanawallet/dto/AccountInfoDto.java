package io.github.chopachopachopa.solanawallet.dto;

import java.math.BigDecimal;

public record AccountInfoDto(
    String username,
    String address,
    BigDecimal balance,
    BigDecimal usdBalance
) {
}
