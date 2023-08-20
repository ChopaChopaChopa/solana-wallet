package io.github.chopachopachopa.solanawallet.service;

import io.github.chopachopachopa.solanawallet.domain.entity.UserEntity;
import io.github.chopachopachopa.solanawallet.domain.model.RegUser;
import io.github.chopachopachopa.solanawallet.exception.CommonException;
import io.github.chopachopachopa.solanawallet.exception.ErrorCode;
import io.github.chopachopachopa.solanawallet.service.helper.UserHelperService;
import io.github.chopachopachopa.solanawallet.util.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.bitcoinj.core.Base58;
import org.p2p.solanaj.core.Account;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final UserHelperService userHelperService;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userMapper.toModel(
            userHelperService.findByUsername(username)
        );
    }

    public void saveUser(final RegUser model) {
        val username = model.username();

        if (userHelperService.existsByUsername(username)) {
            throw new CommonException(ErrorCode.USER_ALREADY_REGISTERED);
        }

        userHelperService.save(createUserEntity(model));
    }

    private UserEntity createUserEntity(final RegUser model) {
        val account = new Account();
        val encodedPassword = passwordEncoder.encode(model.password());
        val spk = Base58.encode(account.getSecretKey());

        return userMapper.toEntity(model)
            .setPassword(encodedPassword)
            .setAddress(account.getPublicKey().toBase58())
            .setPk(spk);
    }
}
