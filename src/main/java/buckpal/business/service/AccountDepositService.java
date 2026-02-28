package buckpal.business.service;

import buckpal.business.dto.AccountDepositCommand;
import buckpal.business.model.Account;
import buckpal.business.model.Account.AccountId;
import buckpal.business.model.Activity;
import buckpal.data.repository.IAccountJpaRepository;
import buckpal.data.repository.IActivityJpaRepository;
import buckpal.business.repository.AccountMapper;
import buckpal.data.entity.ActivityJpaEntity;
import lombok.RequiredArgsConstructor;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AccountDepositService {

	private final IAccountJpaRepository accountRepository;
	private final IActivityJpaRepository activityRepository;
	private final AccountMapper accountMapper;

	public boolean deposit(AccountDepositCommand command) {
		// Load account using JpaRepository directly
		var accountJpaEntity =
				accountRepository.findById(command.accountId().getValue())
						.orElseThrow(() -> new EntityNotFoundException(
								"Account with ID " + command.accountId().getValue() + " not found"));
		List<ActivityJpaEntity> activities =
				activityRepository.findByOwner(command.accountId().getValue());
		Account account = accountMapper.mapToDomainEntity(accountJpaEntity, activities);

		// Use a system account as the source for external deposits
		AccountId externalSourceAccount = new AccountId(0L);
		boolean success = account.deposit(command.money(), externalSourceAccount);
		
		// Save activities using JpaRepository directly
		for (Activity activity : account.getActivityWindow().getActivities()) {
			if (activity.getId() == null) {
				activityRepository.save(accountMapper.mapToJpaEntity(activity));
			}
		}
		return success;
	}

}
