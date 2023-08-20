package io.github.chopachopachopa.solanawallet.integration;

import io.github.chopachopachopa.solanawallet.dto.integration.CoinMarketResponseDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
    name = "coingecko",
    url = "${feign.client.config.coingecko.url}"
)
public interface CoinGeckoCapClient {
    @GetMapping("coins/markets")
    List<CoinMarketResponseDto> getMarkets(
        @RequestParam("vs_currency")
        String currency,
        @RequestParam("ids")
        String id
    );
}
