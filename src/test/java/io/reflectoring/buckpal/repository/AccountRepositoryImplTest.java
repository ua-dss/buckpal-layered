package io.reflectoring.buckpal.repository;

import io.reflectoring.buckpal.domain.model.Account;
import io.reflectoring.buckpal.domain.model.Account.AccountId;
import io.reflectoring.buckpal.infrastructure.entity.ActivityJpaEntity;
import io.reflectoring.buckpal.domain.model.Money;
import io.reflectoring.buckpal.infrastructure.repository.AccountMapper;
import io.reflectoring.buckpal.infrastructure.repository.AccountRepositoryImpl;
import io.reflectoring.buckpal.infrastructure.repository.ActivityJpaRepository;
import io.reflectoring.buckpal.domain.model.ActivityWindow;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import static io.reflectoring.buckpal.common.AccountTestData.*;
import static io.reflectoring.buckpal.common.ActivityTestData.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({AccountRepositoryImpl.class, AccountMapper.class})
class AccountRepositoryImplTest {

	@Autowired
	private AccountRepositoryImpl repositoryUnderTest;

	@Autowired
	private ActivityJpaRepository activityRepository;

	@Test
	@Sql("AccountRepositoryImplTest.sql")
	void loadsAccount() {
		Account account = repositoryUnderTest.loadAccount(new AccountId(1L));

		assertThat(account.getActivityWindow().getActivities()).hasSize(4);
		assertThat(account.calculateBalance()).isEqualTo(Money.of(1500));
	}

	@Test
	void updatesActivities() {
		Account account = defaultAccount()
				.withBaselineBalance(Money.of(555L))
				.withActivityWindow(new ActivityWindow(
						defaultActivity()
								.withId(null)
								.withMoney(Money.of(1L)).build()))
				.build();

		repositoryUnderTest.updateActivities(account);

		assertThat(activityRepository.count()).isEqualTo(1);

		ActivityJpaEntity savedActivity = activityRepository.findAll().get(0);
		assertThat(savedActivity.getAmount()).isEqualTo(1L);
	}

}
