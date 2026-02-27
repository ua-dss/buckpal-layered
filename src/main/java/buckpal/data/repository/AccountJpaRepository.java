package buckpal.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import buckpal.data.entity.AccountJpaEntity;

public interface AccountJpaRepository extends JpaRepository<AccountJpaEntity, Long> {
}
