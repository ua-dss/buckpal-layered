package buckpal.business.dto;

import buckpal.business.model.Account.AccountId;
import buckpal.business.model.Money;
import jakarta.validation.constraints.NotNull;

import static buckpal.common.validation.Validation.validate;

import buckpal.common.validation.IPositiveMoney;

public record SendMoneyCommand(
		@NotNull AccountId sourceAccountId,
		@NotNull AccountId targetAccountId,
		@NotNull @IPositiveMoney Money money) {

	public SendMoneyCommand(
			AccountId sourceAccountId,
			AccountId targetAccountId,
			Money money) {
		this.sourceAccountId = sourceAccountId;
		this.targetAccountId = targetAccountId;
		this.money = money;
		validate(this);
	}

}
