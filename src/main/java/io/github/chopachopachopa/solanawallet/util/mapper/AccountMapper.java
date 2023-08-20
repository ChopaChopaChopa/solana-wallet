package io.github.chopachopachopa.solanawallet.util.mapper;

import io.github.chopachopachopa.solanawallet.domain.entity.UserEntity;
import io.github.chopachopachopa.solanawallet.domain.model.AccountInfo;
import io.github.chopachopachopa.solanawallet.dto.AccountInfoDto;
import java.math.BigDecimal;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountInfo toAccountInfo(
        UserEntity entity,
        BigDecimal balance,
        BigDecimal usdBalance
    );

    AccountInfoDto toAccountInfoDto(AccountInfo model);
}
