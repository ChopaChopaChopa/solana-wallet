package io.github.chopachopachopa.solanawallet.dto.integration;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CoinMarketResponseDto {
    private String id;
    private String symbol;
    private String name;
    @JsonProperty("current_price")
    private BigDecimal currentPrice;
}
