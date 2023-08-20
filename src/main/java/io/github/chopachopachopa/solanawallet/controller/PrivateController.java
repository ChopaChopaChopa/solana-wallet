package io.github.chopachopachopa.solanawallet.controller;

import io.github.chopachopachopa.solanawallet.dto.AccountInfoDto;
import io.github.chopachopachopa.solanawallet.dto.RestResponse;
import io.github.chopachopachopa.solanawallet.dto.TransferDto;
import io.github.chopachopachopa.solanawallet.service.AccountService;
import io.github.chopachopachopa.solanawallet.util.mapper.AccountMapper;
import io.github.chopachopachopa.solanawallet.util.mapper.TransferMapper;
import jakarta.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.p2p.solanaj.rpc.RpcException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/private")
public class PrivateController {
    private final TransferMapper transferMapper;
    private final AccountMapper accountMapper;
    private final AccountService service;

    @GetMapping("/accounts/info")
    public RestResponse<AccountInfoDto> getBalance() throws RpcException {
        return RestResponse.ok(
            accountMapper.toAccountInfoDto(
                service.getAccountInfo()
            )
        );
    }

    @GetMapping("/accounts/transfers")
    public RestResponse<List<TransferDto>> transferFunds(final int limit) {
        return RestResponse.ok(
            service.getTransfers(limit).stream()
                .map(transferMapper::toDto)
                .toList()
        );
    }

    @PostMapping("/accounts/transfers")
    public RestResponse<String> transferFunds(
        @NotEmpty(message = "Empty recipient address") final String to,
        BigDecimal amount
    ) {
        return RestResponse.ok(
            service.transferFunds(to, amount)
        );
    }
}
