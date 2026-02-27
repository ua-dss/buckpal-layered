package buckpal.service;

import buckpal.common.synchronization.AccountLock;
import buckpal.business.dto.AccountSendMoneyCommand;
import buckpal.business.model.Account;
import buckpal.business.model.Account.AccountId;
import buckpal.infrastructure.repository.AccountJpaRepository;
import buckpal.infrastructure.repository.ActivityJpaRepository;
import buckpal.business.repository.AccountMapper;
import buckpal.infrastructure.entity.AccountJpaEntity;
import buckpal.business.model.Money;
import buckpal.business.service.AccountSendMoneyService;
import buckpal.business.service.MoneyTransferProperties;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

class AccountSendMoneyServiceTest {

	private final AccountJpaRepository accountRepository =
			Mockito.mock(AccountJpaRepository.class);

	private final ActivityJpaRepository activityRepository =
			Mockito.mock(ActivityJpaRepository.class);

	private final AccountMapper accountMapper =
			Mockito.mock(AccountMapper.class);

	private final AccountLock accountLock =
			Mockito.mock(AccountLock.class);

	private final AccountSendMoneyService sendMoneyService =
			new AccountSendMoneyService(accountRepository, activityRepository, accountLock, moneyTransferProperties(), accountMapper);

	@Test
	void givenWithdrawalFails_thenOnlySourceAccountIsLockedAndReleased() {

		AccountId sourceAccountId = new AccountId(41L);
		Account sourceAccount = givenAnAccountWithId(sourceAccountId);

		AccountId targetAccountId = new AccountId(42L);
		Account targetAccount = givenAnAccountWithId(targetAccountId);

		givenWithdrawalWillFail(sourceAccount);
		givenDepositWillSucceed(targetAccount);

		AccountSendMoneyCommand command = new AccountSendMoneyCommand(
				sourceAccountId,
				targetAccountId,
				Money.of(300L));

		boolean success = sendMoneyService.sendMoney(command);

		assertThat(success).isFalse();

		then(accountLock).should().lockAccount(eq(sourceAccountId));
		then(accountLock).should().releaseAccount(eq(sourceAccountId));
		then(accountLock).should(times(0)).lockAccount(eq(targetAccountId));
	}

	@Test
	void transactionSucceeds() {

		Account sourceAccount = givenSourceAccount();
		Account targetAccount = givenTargetAccount();

		givenWithdrawalWillSucceed(sourceAccount);
		givenDepositWillSucceed(targetAccount);

		Money money = Money.of(500L);

		AccountSendMoneyCommand command = new AccountSendMoneyCommand(
				sourceAccount.getId().get(),
				targetAccount.getId().get(),
				money);

		boolean success = sendMoneyService.sendMoney(command);

		assertThat(success).isTrue();

		AccountId sourceAccountId = sourceAccount.getId().get();
		AccountId targetAccountId = targetAccount.getId().get();

		then(accountLock).should().lockAccount(eq(sourceAccountId));
		then(sourceAccount).should().withdraw(eq(money), eq(targetAccountId));
		then(accountLock).should().releaseAccount(eq(sourceAccountId));

		then(accountLock).should().lockAccount(eq(targetAccountId));
		then(targetAccount).should().deposit(eq(money), eq(sourceAccountId));
		then(accountLock).should().releaseAccount(eq(targetAccountId));
	}

	private void givenDepositWillSucceed(Account account) {
		given(account.deposit(any(Money.class), any(AccountId.class)))
				.willReturn(true);
	}

	private void givenWithdrawalWillFail(Account account) {
		given(account.withdraw(any(Money.class), any(AccountId.class)))
				.willReturn(false);
	}

	private void givenWithdrawalWillSucceed(Account account) {
		given(account.withdraw(any(Money.class), any(AccountId.class)))
				.willReturn(true);
	}

	private Account givenTargetAccount() {
		return givenAnAccountWithId(new AccountId(42L));
	}

	private Account givenSourceAccount() {
		return givenAnAccountWithId(new AccountId(41L));
	}

	private Account givenAnAccountWithId(AccountId id) {
		Account account = Mockito.mock(Account.class);
		given(account.getId())
				.willReturn(Optional.of(id));
		
		// Mock the JPA entity and mapper
		AccountJpaEntity jpaEntity = Mockito.mock(AccountJpaEntity.class);
		given(jpaEntity.getId()).willReturn(id.getValue());
		given(accountRepository.findById(eq(id.getValue())))
				.willReturn(Optional.of(jpaEntity));
		given(activityRepository.findByOwner(eq(id.getValue())))
				.willReturn(new ArrayList<>());
		given(accountMapper.mapToDomainEntity(eq(jpaEntity), any()))
				.willReturn(account);

		buckpal.business.model.ActivityWindow activityWindow =
				Mockito.mock(buckpal.business.model.ActivityWindow.class);
		given(activityWindow.getActivities()).willReturn(new ArrayList<>());
		given(account.getActivityWindow()).willReturn(activityWindow);
		
		return account;
	}

	private MoneyTransferProperties moneyTransferProperties() {
		return new MoneyTransferProperties(Money.of(Long.MAX_VALUE));
	}

}
