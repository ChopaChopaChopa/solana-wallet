package io.github.chopachopachopa.solanawallet.service;

import io.github.chopachopachopa.solanawallet.domain.entity.UserEntity;
import io.github.chopachopachopa.solanawallet.domain.model.AccountInfo;
import io.github.chopachopachopa.solanawallet.domain.model.Transfer;
import io.github.chopachopachopa.solanawallet.service.helper.CoinGeckoHelperService;
import io.github.chopachopachopa.solanawallet.service.helper.SolanaHelperService;
import io.github.chopachopachopa.solanawallet.service.helper.UserHelperService;
import io.github.chopachopachopa.solanawallet.util.mapper.AccountMapper;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountMapper accountMapper;
    private final UserHelperService userHelperService;
    private final SolanaHelperService solanaHelperService;
    private final CoinGeckoHelperService coinGeckoHelperService;

    public AccountInfo getAccountInfo() {
        val userEntity = findUser();

        val address = userEntity.getAddress();
        val balance = solanaHelperService.getBalance(address);

        return accountMapper.toAccountInfo(
            userEntity,
            balance,
            coinGeckoHelperService.getPrice().multiply(balance)
        );
    }

    public String transferFunds(final String to, BigDecimal amount) {
        val secretKey = findUser().getPk();
        return solanaHelperService.sendTransaction(secretKey, to, amount);
    }

    public List<Transfer> getTransfers(int limit) {
        val user = findUser();
        return solanaHelperService.getTransfers(user.getAddress(), limit);
    }

    private UserEntity findUser() {
        return userHelperService.findUser();
    }
}
