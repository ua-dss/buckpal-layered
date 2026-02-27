package buckpal.business.repository;

import java.util.ArrayList;
import java.util.List;

import buckpal.business.model.Account;
import buckpal.business.model.Account.AccountId;
import buckpal.data.entity.AccountJpaEntity;
import buckpal.business.model.Activity;
import buckpal.data.entity.ActivityJpaEntity;
import buckpal.business.model.Money;
import buckpal.business.model.Activity.ActivityId;
import buckpal.business.model.ActivityWindow;

import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

	public Account mapToDomainEntity(
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

	public ActivityJpaEntity mapToJpaEntity(Activity activity) {
		return new ActivityJpaEntity(
				activity.getId() == null ? null : activity.getId().getValue(),
				activity.getTimestamp(),
				activity.getOwnerAccountId().getValue(),
				activity.getSourceAccountId().getValue(),
				activity.getTargetAccountId().getValue(),
				activity.getMoney().getAmount().longValue());
	}

}
