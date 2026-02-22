package io.reflectoring.buckpal.common.synchronization;

import io.reflectoring.buckpal.domain.model.Account.AccountId;

public interface AccountLock {

	void lockAccount(AccountId accountId);

	void releaseAccount(AccountId accountId);

}
