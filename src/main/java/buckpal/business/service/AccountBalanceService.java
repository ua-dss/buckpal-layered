package buckpal.business.service;

import buckpal.business.dto.AccountBalanceQuery;
import buckpal.business.model.Money;
import buckpal.infrastructure.repository.AccountJpaRepository;
import buckpal.infrastructure.repository.ActivityJpaRepository;
import buckpal.business.repository.AccountMapper;
import buckpal.infrastructure.entity.ActivityJpaEntity;
import lombok.RequiredArgsConstructor;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AccountBalanceService {

	private final AccountJpaRepository accountRepository;
	private final ActivityJpaRepository activityRepository;
	private final AccountMapper accountMapper;

	public Money getAccountBalance(AccountBalanceQuery query) {
		// Load account using JpaRepository directly
		var accountJpaEntity =
				accountRepository.findById(query.accountId().getValue())
						.orElseThrow(() -> new EntityNotFoundException(
								"Account with ID " + query.accountId().getValue() + " not found"));
		List<ActivityJpaEntity> activities =
				activityRepository.findByOwner(query.accountId().getValue());
		return accountMapper.mapToDomainEntity(accountJpaEntity, activities)
				.calculateBalance();
	}
}
