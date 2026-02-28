package buckpal.business.dto;

import buckpal.business.model.Account.AccountId;
import jakarta.validation.constraints.NotNull;

import static buckpal.common.validation.Validation.validate;

public record BalanceQuery(
		@NotNull AccountId accountId) {

	public BalanceQuery(AccountId accountId) {
		this.accountId = accountId;
		validate(this);
	}

}
