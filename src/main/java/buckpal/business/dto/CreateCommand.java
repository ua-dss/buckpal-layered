package buckpal.business.dto;

import jakarta.validation.constraints.NotNull;

import static buckpal.common.validation.Validation.validate;

import buckpal.business.model.Money;
import buckpal.common.validation.IPositiveMoney;

public record CreateCommand(
		@NotNull @IPositiveMoney Money initialBalance) {

	public CreateCommand(Money initialBalance) {
		this.initialBalance = initialBalance;
		validate(this);
	}

}
