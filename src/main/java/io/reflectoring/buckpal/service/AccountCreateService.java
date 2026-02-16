package io.reflectoring.buckpal.service;

import io.reflectoring.buckpal.entity.Account;
import io.reflectoring.buckpal.entity.Account.AccountId;
import io.reflectoring.buckpal.entity.ActivityWindow;
import io.reflectoring.buckpal.dto.AccountCreateCommand;
import io.reflectoring.buckpal.repository.AccountRepository;
import lombok.RequiredArgsConstructor;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Transactional
public class AccountCreateService {

	private final AccountRepository accountRepository;

	public AccountId createAccount(AccountCreateCommand command) {
		Account newAccount = Account.withoutId(
				command.initialBalance(),
				new ActivityWindow());

		return accountRepository.createAccount(newAccount);
	}

}
