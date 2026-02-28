package buckpal.common.synchronization;

import buckpal.business.model.Account.AccountId;

public interface IAccountLock {

	void lockAccount(AccountId accountId);

	void releaseAccount(AccountId accountId);

}
