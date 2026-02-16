package io.reflectoring.buckpal.dto;

import io.reflectoring.buckpal.entity.Money;
import jakarta.validation.constraints.NotNull;

import static io.reflectoring.buckpal.common.validation.Validation.validate;

public record AccountCreateCommand(
		@NotNull Money initialBalance
) {

	public AccountCreateCommand(Money initialBalance) {
		this.initialBalance = initialBalance;
		validate(this);
	}

}
