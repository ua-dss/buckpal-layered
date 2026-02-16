package io.reflectoring.buckpal.service;

import io.reflectoring.buckpal.entity.Account;
import io.reflectoring.buckpal.entity.Account.AccountId;
import io.reflectoring.buckpal.dto.AccountWithdrawCommand;
import io.reflectoring.buckpal.repository.AccountRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AccountWithdrawService {

	private final AccountRepository accountRepository;

	public boolean withdraw(AccountWithdrawCommand command) {
		Account account = accountRepository.loadAccount(command.accountId());
		// Use a system account as the target for external withdrawals
		AccountId externalTargetAccount = new AccountId(0L);
		boolean success = account.withdraw(command.money(), externalTargetAccount);
		accountRepository.updateActivities(account);
		return success;
	}

}
