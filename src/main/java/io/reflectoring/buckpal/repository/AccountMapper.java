package io.reflectoring.buckpal.repository;

import java.util.ArrayList;
import java.util.List;

import io.reflectoring.buckpal.entity.Account;
import io.reflectoring.buckpal.entity.Account.AccountId;
import io.reflectoring.buckpal.entity.Activity;
import io.reflectoring.buckpal.entity.Activity.ActivityId;
import io.reflectoring.buckpal.entity.ActivityWindow;
import io.reflectoring.buckpal.entity.Money;
import io.reflectoring.buckpal.entity.AccountJpaEntity;
import io.reflectoring.buckpal.entity.ActivityJpaEntity;
import org.springframework.stereotype.Component;

@Component
class AccountMapper {

	Account mapToDomainEntity(
			AccountJpaEntity account,
			List<ActivityJpaEntity> activities) {

		Money baselineBalance = Money.of(account.getBaselineBalance());

		return Account.withId(
				new AccountId(account.getId()),
				baselineBalance,
				mapToActivityWindow(activities));

	}

	ActivityWindow mapToActivityWindow(List<ActivityJpaEntity> activities) {
		List<Activity> mappedActivities = new ArrayList<>();

		for (ActivityJpaEntity activity : activities) {
			mappedActivities.add(new Activity(
					new ActivityId(activity.getId()),
					new AccountId(activity.getOwnerAccountId()),
					new AccountId(activity.getSourceAccountId()),
					new AccountId(activity.getTargetAccountId()),
					activity.getTimestamp(),
					Money.of(activity.getAmount())));
		}

		return new ActivityWindow(mappedActivities);
	}

	ActivityJpaEntity mapToJpaEntity(Activity activity) {
		return new ActivityJpaEntity(
				activity.getId() == null ? null : activity.getId().getValue(),
				activity.getTimestamp(),
				activity.getOwnerAccountId().getValue(),
				activity.getSourceAccountId().getValue(),
				activity.getTargetAccountId().getValue(),
				activity.getMoney().getAmount().longValue());
	}

}
