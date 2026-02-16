package io.reflectoring.buckpal.service;

import io.reflectoring.buckpal.entity.Account;
import io.reflectoring.buckpal.repository.AccountRepository;
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
