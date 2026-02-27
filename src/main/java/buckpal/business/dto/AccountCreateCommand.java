package buckpal.business.dto;

import jakarta.validation.constraints.NotNull;

import static buckpal.common.validation.Validation.validate;

import buckpal.business.model.Money;
import buckpal.common.validation.PositiveMoney;

public record AccountCreateCommand(
		@NotNull @PositiveMoney Money initialBalance
) {

	public AccountCreateCommand(Money initialBalance) {
		this.initialBalance = initialBalance;
		validate(this);
	}

}
