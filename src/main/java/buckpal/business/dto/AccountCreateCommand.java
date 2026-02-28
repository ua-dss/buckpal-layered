package buckpal.business.dto;

import jakarta.validation.constraints.NotNull;

import static buckpal.common.validation.Validation.validate;

import buckpal.business.model.Money;
import buckpal.common.validation.IPositiveMoney;

public record AccountCreateCommand(
		@NotNull @IPositiveMoney Money initialBalance) {

	public AccountCreateCommand(Money initialBalance) {
		this.initialBalance = initialBalance;
		validate(this);
	}

}
