package buckpal.business.dto;

import buckpal.business.model.Account.AccountId;
import buckpal.business.model.Money;
import jakarta.validation.constraints.NotNull;

import static buckpal.common.validation.Validation.validate;

import buckpal.common.validation.IPositiveMoney;

public record AccountWithdrawCommand(
		@NotNull AccountId accountId,
		@NotNull @IPositiveMoney Money money

) {

	public AccountWithdrawCommand(AccountId accountId, Money money) {
		this.accountId = accountId;
		this.money = money;
		validate(this);
	}

}
