package io.reflectoring.buckpal.presentation.controller;

import io.reflectoring.buckpal.domain.dto.AccountDepositCommand;
import io.reflectoring.buckpal.domain.dto.AccountBalanceQuery;
import io.reflectoring.buckpal.domain.model.Account.AccountId;
import io.reflectoring.buckpal.domain.model.Money;
import io.reflectoring.buckpal.presentation.dto.AccountInfo;
import io.reflectoring.buckpal.presentation.dto.DepositResponse;
import io.reflectoring.buckpal.domain.service.AccountBalanceService;
import io.reflectoring.buckpal.domain.service.AccountDepositService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class AccountDepositController {

	private final AccountDepositService accountDepositService;
	private final AccountBalanceService accountBalanceService;

	@PostMapping(path = "/accounts/deposit")
	DepositResponse deposit(
			@RequestParam("accountId") Long accountId,
			@RequestParam("amount") Long amount) {

		try {
			AccountDepositCommand command = new AccountDepositCommand(
					new AccountId(accountId),
					Money.of(amount));

			boolean success = accountDepositService.deposit(command);

			AccountBalanceQuery query =
					new AccountBalanceQuery(new AccountId(accountId));

			Long balance = accountBalanceService.getAccountBalance(query)
					.getAmount()
					.longValue();

			return new DepositResponse(success, new AccountInfo(accountId, balance), null);
		} catch (Exception e) {
			return new DepositResponse(false, null, e.getMessage());
		}
	}

}
