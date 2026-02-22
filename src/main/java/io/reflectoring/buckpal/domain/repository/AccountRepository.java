package io.reflectoring.buckpal.domain.repository;

import io.reflectoring.buckpal.domain.model.Account;
import io.reflectoring.buckpal.domain.model.Account.AccountId;

import java.util.List;

public interface AccountRepository {

	Account loadAccount(AccountId accountId);

	List<Account> getAllAccounts();

	void updateActivities(Account account);

	AccountId createAccount(Account account);
}
