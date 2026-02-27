package buckpal;

import buckpal.business.model.Account;
import buckpal.business.model.Account.AccountId;
import buckpal.data.repository.AccountJpaRepository;
import buckpal.data.repository.ActivityJpaRepository;
import buckpal.business.repository.AccountMapper;
import buckpal.business.model.Money;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import static org.assertj.core.api.BDDAssertions.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SendMoneySystemTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private AccountJpaRepository accountRepository;

	@Autowired
	private ActivityJpaRepository activityRepository;

	@Autowired
	private AccountMapper accountMapper;

	@Test
	@Sql("SendMoneySystemTest.sql")
	void sendMoney() {

		Money initialSourceBalance = sourceAccount().calculateBalance();
		Money initialTargetBalance = targetAccount().calculateBalance();

		ResponseEntity response = whenSendMoney(
				sourceAccountId(),
				targetAccountId(),
				transferredAmount());

		then(response.getStatusCode())
				.isEqualTo(HttpStatus.OK);

		then(sourceAccount().calculateBalance())
				.isEqualTo(initialSourceBalance.minus(transferredAmount()));

		then(targetAccount().calculateBalance())
				.isEqualTo(initialTargetBalance.plus(transferredAmount()));

	}

	private Account sourceAccount() {
		return loadAccount(sourceAccountId());
	}

	private Account targetAccount() {
		return loadAccount(targetAccountId());
	}

	private Account loadAccount(AccountId accountId) {
		var jpaEntity = accountRepository.findById(accountId.getValue())
				.orElseThrow(() -> new IllegalArgumentException("Account not found"));
		var activities = activityRepository.findByOwner(accountId.getValue());
		return accountMapper.mapToDomainEntity(jpaEntity, activities);
	}


	private ResponseEntity whenSendMoney(
			AccountId sourceAccountId,
			AccountId targetAccountId,
			Money amount) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		HttpEntity<Void> request = new HttpEntity<>(null, headers);

		return restTemplate.exchange(
				"/accounts/send?sourceAccountId={sourceAccountId}&targetAccountId={targetAccountId}&amount={amount}",
				HttpMethod.POST,
				request,
				Object.class,
				sourceAccountId.getValue(),
				targetAccountId.getValue(),
				amount.getAmount());
	}

	private Money transferredAmount() {
		return Money.of(500L);
	}

	private Money balanceOf(AccountId accountId) {
		Account account = loadAccount(accountId);
		return account.calculateBalance();
	}

	private AccountId sourceAccountId() {
		return new AccountId(1L);
	}

	private AccountId targetAccountId() {
		return new AccountId(2L);
	}

}
