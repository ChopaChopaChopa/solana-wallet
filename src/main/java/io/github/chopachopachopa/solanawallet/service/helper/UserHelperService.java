package io.github.chopachopachopa.solanawallet.service.helper;

import io.github.chopachopachopa.solanawallet.domain.entity.UserEntity;
import io.github.chopachopachopa.solanawallet.exception.CommonException;
import io.github.chopachopachopa.solanawallet.exception.ErrorCode;
import io.github.chopachopachopa.solanawallet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.bitcoinj.core.Base58;
import org.p2p.solanaj.core.PublicKey;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserHelperService {
    private final UserRepository repository;

    public UserEntity findByUsername(final String username) {
        return repository.findByUsername(username)
            .orElseThrow(() -> new CommonException(ErrorCode.USER_NOT_FOUND));
    }

    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    public UserEntity save(UserEntity entity) {
        return repository.save(entity);
    }
}
