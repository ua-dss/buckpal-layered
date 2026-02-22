package io.reflectoring.buckpal.domain.service;

import io.reflectoring.buckpal.domain.dto.AccountDepositCommand;
import io.reflectoring.buckpal.domain.model.Account;
import io.reflectoring.buckpal.domain.model.Account.AccountId;
import io.reflectoring.buckpal.domain.repository.AccountRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AccountDepositService {

	private final AccountRepository accountRepository;

	public boolean deposit(AccountDepositCommand command) {
		Account account = accountRepository.loadAccount(command.accountId());
		// Use a system account as the source for external deposits
		AccountId externalSourceAccount = new AccountId(0L);
		boolean success = account.deposit(command.money(), externalSourceAccount);
		accountRepository.updateActivities(account);
		return success;
	}

}
