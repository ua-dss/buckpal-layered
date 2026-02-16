package io.reflectoring.buckpal.dto;

import io.reflectoring.buckpal.entity.Account.AccountId;
import jakarta.validation.constraints.NotNull;

import static io.reflectoring.buckpal.common.validation.Validation.validate;

public record GetAccountBalanceQuery(
		@NotNull AccountId accountId
) {

	public GetAccountBalanceQuery(AccountId accountId) {
		this.accountId = accountId;
		validate(this);
	}

}
