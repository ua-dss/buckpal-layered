package io.reflectoring.buckpal.presentation.controller;

import io.reflectoring.buckpal.domain.dto.AccountWithdrawCommand;
import io.reflectoring.buckpal.domain.dto.AccountBalanceQuery;
import io.reflectoring.buckpal.domain.model.Account.AccountId;
import io.reflectoring.buckpal.domain.model.Money;
import io.reflectoring.buckpal.presentation.dto.AccountInfo;
import io.reflectoring.buckpal.presentation.dto.WithdrawResponse;
import io.reflectoring.buckpal.domain.service.AccountBalanceService;
import io.reflectoring.buckpal.domain.service.AccountWithdrawService;
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

			AccountBalanceQuery query =
					new AccountBalanceQuery(new AccountId(accountId));

			Long balance = accountBalanceService.getAccountBalance(query)
					.getAmount()
					.longValue();

			return new WithdrawResponse(success, new AccountInfo(accountId, balance), null);
		} catch (Exception e) {
			return new WithdrawResponse(false, null, e.getMessage());
		}
	}

}
