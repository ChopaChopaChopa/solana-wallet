package io.github.chopachopachopa.solanawallet.service.helper;

import io.github.chopachopachopa.solanawallet.integration.CoinGeckoCapClient;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CoinGeckoHelperService {
    private static final String CURRENCY = "usd";
    private static final String ID = "solana";

    private final CoinGeckoCapClient client;

    @Cacheable(value = "price")
    public BigDecimal getPrice() {
        return client.getMarkets(CURRENCY, ID)
            .get(0).getCurrentPrice();
    }
}
