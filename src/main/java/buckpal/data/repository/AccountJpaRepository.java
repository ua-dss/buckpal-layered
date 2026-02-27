package buckpal.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import buckpal.infrastructure.entity.AccountJpaEntity;

public interface AccountJpaRepository extends JpaRepository<AccountJpaEntity, Long> {
}
