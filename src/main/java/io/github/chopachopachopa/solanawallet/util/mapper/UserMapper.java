package io.github.chopachopachopa.solanawallet.util.mapper;

import io.github.chopachopachopa.solanawallet.domain.entity.UserEntity;
import io.github.chopachopachopa.solanawallet.domain.model.RegUser;
import io.github.chopachopachopa.solanawallet.domain.model.User;
import io.github.chopachopachopa.solanawallet.dto.RegUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pk", ignore = true)
    @Mapping(target = "address", ignore = true)
    UserEntity toEntity(RegUser model);
    RegUser toReqModel(RegUserDto dto);
    User toModel(UserEntity entity);
}
