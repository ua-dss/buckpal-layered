package io.reflectoring.buckpal.domain.dto;

import io.reflectoring.buckpal.domain.model.Account.AccountId;
import jakarta.validation.constraints.NotNull;

import static io.reflectoring.buckpal.common.validation.Validation.validate;

public record AccountBalanceQuery(
		@NotNull AccountId accountId
) {

	public AccountBalanceQuery(AccountId accountId) {
		this.accountId = accountId;
		validate(this);
	}

}
