package io.reflectoring.buckpal.dto;

import io.reflectoring.buckpal.entity.Account.AccountId;
import io.reflectoring.buckpal.entity.Money;
import jakarta.validation.constraints.NotNull;

import static io.reflectoring.buckpal.common.validation.Validation.validate;

public record AccountSendMoneyCommand(
		@NotNull AccountId sourceAccountId,
		@NotNull AccountId targetAccountId,
		@NotNull @PositiveMoney Money money
) {

	public AccountSendMoneyCommand(
			AccountId sourceAccountId,
			AccountId targetAccountId,
			Money money) {
		this.sourceAccountId = sourceAccountId;
		this.targetAccountId = targetAccountId;
		this.money = money;
		validate(this);
	}

}
