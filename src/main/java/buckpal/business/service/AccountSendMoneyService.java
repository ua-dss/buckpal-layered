package buckpal.business.service;

import buckpal.common.synchronization.IAccountLock;
import buckpal.business.dto.SendMoneyCommand;
import buckpal.business.model.Account;
import buckpal.business.model.Account.AccountId;
import buckpal.data.repository.IAccountJpaRepository;
import buckpal.data.entity.ActivityJpaEntity;
import buckpal.business.repository.AccountMapper;
import buckpal.business.exception.ThresholdExceededException;
import buckpal.business.model.Activity;
import lombok.RequiredArgsConstructor;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class AccountSendMoneyService {

	private final IAccountJpaRepository accountRepository;
	private final buckpal.data.repository.IActivityJpaRepository activityRepository;
	private final IAccountLock accountLock;
	private final MoneyTransferProperties moneyTransferProperties;
	private final AccountMapper accountMapper;

	public boolean sendMoney(SendMoneyCommand command) {

		checkThreshold(command);

		// Load source account using JpaRepository directly
		var sourceAccountJpaEntity = accountRepository.findById(command.sourceAccountId().getValue())
				.orElseThrow(() -> new EntityNotFoundException(
						"Account with ID " + command.sourceAccountId().getValue() + " not found"));
		List<ActivityJpaEntity> sourceActivities = activityRepository.findByOwner(command.sourceAccountId().getValue());
		Account sourceAccount = accountMapper.mapToDomainEntity(sourceAccountJpaEntity, sourceActivities);

		// Load target account using JpaRepository directly
		var targetAccountJpaEntity = accountRepository.findById(command.targetAccountId().getValue())
				.orElseThrow(() -> new EntityNotFoundException(
						"Account with ID " + command.targetAccountId().getValue() + " not found"));
		List<ActivityJpaEntity> targetActivities = activityRepository.findByOwner(command.targetAccountId().getValue());
		Account targetAccount = accountMapper.mapToDomainEntity(targetAccountJpaEntity, targetActivities);

		AccountId sourceAccountId = sourceAccount.getId()
				.orElseThrow(() -> new IllegalStateException("expected source account ID not to be empty"));
		AccountId targetAccountId = targetAccount.getId()
				.orElseThrow(() -> new IllegalStateException("expected target account ID not to be empty"));

		accountLock.lockAccount(sourceAccountId);
		if (!sourceAccount.withdraw(command.money(), targetAccountId)) {
			accountLock.releaseAccount(sourceAccountId);
			return false;
		}

		accountLock.lockAccount(targetAccountId);
		if (!targetAccount.deposit(command.money(), sourceAccountId)) {
			accountLock.releaseAccount(sourceAccountId);
			accountLock.releaseAccount(targetAccountId);
			return false;
		}

		// Save activities using JpaRepository directly
		for (Activity activity : sourceAccount.getActivityWindow().getActivities()) {
			if (activity.getId() == null) {
				activityRepository.save(accountMapper.mapToJpaEntity(activity));
			}
		}
		for (Activity activity : targetAccount.getActivityWindow().getActivities()) {
			if (activity.getId() == null) {
				activityRepository.save(accountMapper.mapToJpaEntity(activity));
			}
		}

		accountLock.releaseAccount(sourceAccountId);
		accountLock.releaseAccount(targetAccountId);
		return true;
	}

	private void checkThreshold(SendMoneyCommand command) {
		if (command.money().isGreaterThan(moneyTransferProperties.getMaximumTransferThreshold())) {
			throw new ThresholdExceededException(moneyTransferProperties.getMaximumTransferThreshold(),
					command.money());
		}
	}

}
