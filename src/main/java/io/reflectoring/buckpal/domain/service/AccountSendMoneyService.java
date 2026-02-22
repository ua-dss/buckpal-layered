package io.reflectoring.buckpal.domain.service;

import io.reflectoring.buckpal.common.synchronization.AccountLock;
import io.reflectoring.buckpal.domain.dto.AccountSendMoneyCommand;
import io.reflectoring.buckpal.domain.model.Account;
import io.reflectoring.buckpal.domain.model.Account.AccountId;
import io.reflectoring.buckpal.domain.repository.AccountRepository;
import lombok.RequiredArgsConstructor;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Transactional
public class AccountSendMoneyService {

	private final AccountRepository accountRepository;
	private final AccountLock accountLock;
	private final MoneyTransferProperties moneyTransferProperties;

	public boolean sendMoney(AccountSendMoneyCommand command) {

		checkThreshold(command);

		Account sourceAccount = accountRepository.loadAccount(
				command.sourceAccountId());

		Account targetAccount = accountRepository.loadAccount(
				command.targetAccountId());

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

		accountRepository.updateActivities(sourceAccount);
		accountRepository.updateActivities(targetAccount);

		accountLock.releaseAccount(sourceAccountId);
		accountLock.releaseAccount(targetAccountId);
		return true;
	}

	private void checkThreshold(AccountSendMoneyCommand command) {
		if (command.money().isGreaterThan(moneyTransferProperties.getMaximumTransferThreshold())) {
			throw new ThresholdExceededException(moneyTransferProperties.getMaximumTransferThreshold(), command.money());
		}
	}

}
