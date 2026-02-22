package io.reflectoring.buckpal.common.synchronization;

import io.reflectoring.buckpal.domain.model.Account.AccountId;

import org.springframework.stereotype.Component;

@Component
class NoOpAccountLock implements AccountLock {

	@Override
	public void lockAccount(AccountId accountId) {
		// do nothing
	}

	@Override
	public void releaseAccount(AccountId accountId) {
		// do nothing
	}

}
