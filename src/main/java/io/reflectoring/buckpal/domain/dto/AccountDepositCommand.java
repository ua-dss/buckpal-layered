package io.reflectoring.buckpal.domain.dto;

import io.reflectoring.buckpal.domain.model.Account.AccountId;
import io.reflectoring.buckpal.domain.model.Money;
import jakarta.validation.constraints.NotNull;

import static io.reflectoring.buckpal.common.validation.Validation.validate;

import io.reflectoring.buckpal.common.validation.PositiveMoney;

public record AccountDepositCommand(
		@NotNull AccountId accountId,
		@NotNull @PositiveMoney Money money

) {

	public AccountDepositCommand(AccountId accountId, Money money) {
		this.accountId = accountId;
		this.money = money;
		validate(this);
	}

}
