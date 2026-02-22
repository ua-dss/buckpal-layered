package io.reflectoring.buckpal.domain.dto;

import io.reflectoring.buckpal.domain.model.Account.AccountId;
import io.reflectoring.buckpal.domain.model.Money;
import jakarta.validation.constraints.NotNull;

import static io.reflectoring.buckpal.common.validation.Validation.validate;

import io.reflectoring.buckpal.common.validation.PositiveMoney;

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
