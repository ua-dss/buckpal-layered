package io.reflectoring.buckpal.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.reflectoring.buckpal.infrastructure.entity.AccountJpaEntity;

public interface AccountJpaRepository extends JpaRepository<AccountJpaEntity, Long> {
}
