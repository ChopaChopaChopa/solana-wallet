package io.github.chopachopachopa.solanawallet.repository;

import io.github.chopachopachopa.solanawallet.domain.entity.UserEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, UUID> {
    Optional<UserEntity> findByUsername(String username);
    boolean existsByUsername(String username);
}
