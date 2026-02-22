package io.reflectoring.buckpal.domain.dto;

import jakarta.validation.constraints.NotNull;

import static io.reflectoring.buckpal.common.validation.Validation.validate;

import io.reflectoring.buckpal.domain.model.Money;
import io.reflectoring.buckpal.common.validation.PositiveMoney;

public record AccountCreateCommand(
		@NotNull @PositiveMoney Money initialBalance
) {

	public AccountCreateCommand(Money initialBalance) {
		this.initialBalance = initialBalance;
		validate(this);
	}

}
