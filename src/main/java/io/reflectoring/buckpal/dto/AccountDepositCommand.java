package io.reflectoring.buckpal.dto;

import io.reflectoring.buckpal.entity.Account.AccountId;
import io.reflectoring.buckpal.entity.Money;
import jakarta.validation.constraints.NotNull;

import static io.reflectoring.buckpal.common.validation.Validation.validate;

public record AccountDepositCommand(
		@NotNull AccountId accountId,
		@NotNull Money money
) {

	public AccountDepositCommand(AccountId accountId, Money money) {
		this.accountId = accountId;
		this.money = money;
		validate(this);
	}

}
