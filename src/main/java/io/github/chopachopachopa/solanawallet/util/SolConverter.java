package io.github.chopachopachopa.solanawallet.util;

import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SolConverter {
    private static final int LAMPORTS_IN_COIN = 100000000;

    public static BigDecimal toSol(final long lamports) {
        return BigDecimal.valueOf(lamports).divide(BigDecimal.valueOf(LAMPORTS_IN_COIN));
    }

    public static long toLamports(final BigDecimal coin) {
        return coin.multiply(BigDecimal.valueOf(LAMPORTS_IN_COIN)).longValue();
    }
}
