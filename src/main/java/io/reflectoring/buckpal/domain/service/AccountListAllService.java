package io.reflectoring.buckpal.domain.service;

import io.reflectoring.buckpal.domain.model.Account;
import io.reflectoring.buckpal.domain.repository.AccountRepository;
import lombok.RequiredArgsConstructor;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class AccountListAllService {

	private final AccountRepository accountRepository;

	public List<Account> listAccounts() {
		return accountRepository.getAllAccounts();
	}

}
