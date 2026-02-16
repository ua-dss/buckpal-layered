package io.reflectoring.buckpal.controller;

import io.reflectoring.buckpal.dto.AccountDepositCommand;
import io.reflectoring.buckpal.dto.AccountInfoDto;
import io.reflectoring.buckpal.dto.DepositResponse;
import io.reflectoring.buckpal.dto.GetAccountBalanceQuery;
import io.reflectoring.buckpal.entity.Account.AccountId;
import io.reflectoring.buckpal.entity.Money;
import io.reflectoring.buckpal.service.AccountBalanceService;
import io.reflectoring.buckpal.service.AccountDepositService;
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

			GetAccountBalanceQuery query =
					new GetAccountBalanceQuery(new AccountId(accountId));

			Long balance = accountBalanceService.getAccountBalance(query)
					.getAmount()
					.longValue();

			return new DepositResponse(success, new AccountInfoDto(accountId, balance), null);
		} catch (Exception e) {
			return new DepositResponse(false, null, e.getMessage());
		}
	}

}
