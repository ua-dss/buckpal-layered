package io.reflectoring.buckpal.controller;

import io.reflectoring.buckpal.dto.AccountInfoDto;
import io.reflectoring.buckpal.dto.AccountSendMoneyCommand;
import io.reflectoring.buckpal.dto.GetAccountBalanceQuery;
import io.reflectoring.buckpal.dto.SendMoneyResponse;
import io.reflectoring.buckpal.entity.Account.AccountId;
import io.reflectoring.buckpal.entity.Money;
import io.reflectoring.buckpal.service.AccountBalanceService;
import io.reflectoring.buckpal.service.AccountSendMoneyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class AccountSendMoneyController {

	private final AccountSendMoneyService sendMoneyService;
	private final AccountBalanceService accountBalanceService;

	@PostMapping(path = "/accounts/send")
	SendMoneyResponse sendMoney(
			@RequestParam("sourceAccountId") Long sourceAccountId,
			@RequestParam("targetAccountId") Long targetAccountId,
			@RequestParam("amount") Long amount) {

		try {
			AccountSendMoneyCommand command = new AccountSendMoneyCommand(
					new AccountId(sourceAccountId),
					new AccountId(targetAccountId),
					Money.of(amount));

			boolean success = sendMoneyService.sendMoney(command);

			GetAccountBalanceQuery sourceQuery =
					new GetAccountBalanceQuery(new AccountId(sourceAccountId));

			GetAccountBalanceQuery targetQuery =
					new GetAccountBalanceQuery(new AccountId(targetAccountId));

			Long sourceBalance = accountBalanceService.getAccountBalance(sourceQuery)
					.getAmount()
					.longValue();

			Long targetBalance = accountBalanceService.getAccountBalance(targetQuery)
					.getAmount()
					.longValue();

			return new SendMoneyResponse(
					success,
					new AccountInfoDto(sourceAccountId, sourceBalance),
					new AccountInfoDto(targetAccountId, targetBalance),
					null);
		} catch (Exception e) {
			return new SendMoneyResponse(false, null, null, e.getMessage());
		}
	}

}
