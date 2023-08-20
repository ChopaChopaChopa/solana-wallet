package io.github.chopachopachopa.solanawallet.service;

import io.github.chopachopachopa.solanawallet.base.AbstractTest;
import io.github.chopachopachopa.solanawallet.domain.entity.UserEntity;
import io.github.chopachopachopa.solanawallet.domain.model.RegUser;
import io.github.chopachopachopa.solanawallet.exception.CommonException;
import io.github.chopachopachopa.solanawallet.service.helper.UserHelperService;
import io.github.chopachopachopa.solanawallet.util.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest extends AbstractTest {
    private static final String REG_USER_JSON = "reg_user.json";
    private static final String USER_ENTITY_JSON = "user_entity.json";

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserHelperService userHelperService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService sut;

    @Test
    void saveUser_whenTryRegister_thenDoesNotThrowsException() {
        final String username = "username";
        final RegUser regUser = readRegUser();
        final UserEntity userEntity = readObject(USER_ENTITY_JSON, UserEntity.class);

        when(userHelperService.existsByUsername(username))
            .thenReturn(false);
        when(userHelperService.save(userEntity))
            .thenReturn(userEntity);
        when(userMapper.toEntity(regUser))
            .thenReturn(userEntity);

        assertThatCode(() -> sut.saveUser(regUser))
            .doesNotThrowAnyException();

        verify(userHelperService, times(1))
            .existsByUsername(username);
        verify(userHelperService, times(1))
            .save(userEntity);
        verify(userMapper, times(1))
            .toEntity(regUser);
    }

    @Test
    void saveUser_whenTryRegister_thenThrowsException() {
        final String username = "username";

        final RegUser regUser = readRegUser();

        when(userHelperService.existsByUsername(username))
            .thenReturn(true);

        assertThatThrownBy(() -> sut.saveUser(regUser))
            .isInstanceOf(CommonException.class);

        verify(userHelperService, times(1))
            .existsByUsername(username);
        verify(userHelperService, never())
            .save(any());
        verify(userMapper, never())
            .toEntity(any());
    }

    @Override
    protected String resourceLocation() {
        return "service/userservice/";
    }

    private RegUser readRegUser() {
        return readObject(REG_USER_JSON, RegUser.class);
    }
}