package io.reflectoring.buckpal.controller;

import io.reflectoring.buckpal.dto.AccountInfoDto;
import io.reflectoring.buckpal.dto.AccountWithdrawCommand;
import io.reflectoring.buckpal.dto.GetAccountBalanceQuery;
import io.reflectoring.buckpal.dto.WithdrawResponse;
import io.reflectoring.buckpal.entity.Account.AccountId;
import io.reflectoring.buckpal.entity.Money;
import io.reflectoring.buckpal.service.AccountBalanceService;
import io.reflectoring.buckpal.service.AccountWithdrawService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class AccountWithdrawController {

	private final AccountWithdrawService accountWithdrawService;
	private final AccountBalanceService accountBalanceService;

	@PostMapping(path = "/accounts/withdraw")
	WithdrawResponse withdraw(
			@RequestParam("accountId") Long accountId,
			@RequestParam("amount") Long amount) {

		try {
			AccountWithdrawCommand command = new AccountWithdrawCommand(
					new AccountId(accountId),
					Money.of(amount));

			boolean success = accountWithdrawService.withdraw(command);

			GetAccountBalanceQuery query =
					new GetAccountBalanceQuery(new AccountId(accountId));

			Long balance = accountBalanceService.getAccountBalance(query)
					.getAmount()
					.longValue();

			return new WithdrawResponse(success, new AccountInfoDto(accountId, balance), null);
		} catch (Exception e) {
			return new WithdrawResponse(false, null, e.getMessage());
		}
	}

}
