package io.reflectoring.buckpal.domain.service;

import io.reflectoring.buckpal.domain.dto.AccountBalanceQuery;
import io.reflectoring.buckpal.domain.model.Money;
import io.reflectoring.buckpal.domain.repository.AccountRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AccountBalanceService {

	private final AccountRepository accountRepository;

	public Money getAccountBalance(AccountBalanceQuery query) {
		return accountRepository.loadAccount(query.accountId())
				.calculateBalance();
	}
}
