package io.reflectoring.buckpal.domain.service;

import io.reflectoring.buckpal.domain.dto.AccountCreateCommand;
import io.reflectoring.buckpal.domain.model.Account;
import io.reflectoring.buckpal.domain.model.Account.AccountId;
import io.reflectoring.buckpal.domain.repository.AccountRepository;
import io.reflectoring.buckpal.domain.model.ActivityWindow;
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
