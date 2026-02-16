package io.reflectoring.buckpal.service;

import io.reflectoring.buckpal.dto.GetAccountBalanceQuery;
import io.reflectoring.buckpal.entity.Money;
import io.reflectoring.buckpal.repository.AccountRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AccountBalanceService {

	private final AccountRepository accountRepository;

	public Money getAccountBalance(GetAccountBalanceQuery query) {
		return accountRepository.loadAccount(query.accountId())
				.calculateBalance();
	}
}
