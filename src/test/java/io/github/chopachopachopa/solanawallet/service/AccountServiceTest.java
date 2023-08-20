package io.github.chopachopachopa.solanawallet.service;

import io.github.chopachopachopa.solanawallet.base.AbstractTest;
import io.github.chopachopachopa.solanawallet.domain.entity.UserEntity;
import io.github.chopachopachopa.solanawallet.domain.model.AccountInfo;
import io.github.chopachopachopa.solanawallet.service.helper.CoinGeckoHelperService;
import io.github.chopachopachopa.solanawallet.service.helper.SolanaHelperService;
import io.github.chopachopachopa.solanawallet.service.helper.UserHelperService;
import io.github.chopachopachopa.solanawallet.util.mapper.AccountMapper;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest extends AbstractTest {
    private static final String USER_ENTITY_JSON = "user_entity.json";
    private static final String ACCOUNT_INFO_JSON = "account_info.json";

    @Mock
    private AccountMapper accountMapper;

    @Mock
    private UserHelperService userHelperService;

    @Mock
    private SolanaHelperService solanaHelperService;

    @Mock
    private CoinGeckoHelperService coinGeckoHelperService;

    @InjectMocks
    private AccountService sut;

    @Test
    void getAccountInfo_whenExecute_thenReturnsAccountInfo() {
        final String address = "DFCMEe2ZneWj6BHDQ2Var56PccoA1Tm8f7oQUbur1Lmy";

        final UserEntity userEntity = readUserEntity();
        final AccountInfo accountInfo = readObject(ACCOUNT_INFO_JSON, AccountInfo.class);
        final BigDecimal balance = BigDecimal.valueOf(1);
        final BigDecimal usdBalance = BigDecimal.valueOf(20);
        final BigDecimal price = BigDecimal.valueOf(20);

        when(userHelperService.findUser())
            .thenReturn(userEntity);
        when(solanaHelperService.getBalance(address))
            .thenReturn(balance);
        when(coinGeckoHelperService.getPrice())
            .thenReturn(price);
        when(accountMapper.toAccountInfo(userEntity, balance, usdBalance))
            .thenReturn(accountInfo);

        assertThatCode(() -> sut.getAccountInfo())
            .doesNotThrowAnyException();

        verify(userHelperService, times(1))
            .findUser();
        verify(solanaHelperService, times(1))
            .getBalance(address);
        verify(coinGeckoHelperService, times(1))
            .getPrice();
        verify(accountMapper, times(1))
            .toAccountInfo(userEntity, balance, usdBalance);
    }

    @Override
    protected String resourceLocation() {
        return "service/accountservice/";
    }

    private UserEntity readUserEntity() {
        return readObject(USER_ENTITY_JSON, UserEntity.class);
    }
}
