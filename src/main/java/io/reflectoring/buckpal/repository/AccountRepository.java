package io.reflectoring.buckpal.repository;

import io.reflectoring.buckpal.entity.Account;
import io.reflectoring.buckpal.entity.Account.AccountId;

import java.util.List;

public interface AccountRepository {

	Account loadAccount(AccountId accountId);

	List<Account> getAllAccounts();

	void updateActivities(Account account);

	AccountId createAccount(Account account);
}
