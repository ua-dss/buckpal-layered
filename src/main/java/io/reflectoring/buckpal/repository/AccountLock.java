package io.reflectoring.buckpal.repository;

import io.reflectoring.buckpal.entity.Account.AccountId;

public interface AccountLock {

	void lockAccount(AccountId accountId);

	void releaseAccount(AccountId accountId);

}
