package io.reflectoring.buckpal.presentation.controller;

import io.reflectoring.buckpal.domain.dto.AccountSendMoneyCommand;
import io.reflectoring.buckpal.domain.dto.AccountBalanceQuery;
import io.reflectoring.buckpal.domain.model.Account.AccountId;
import io.reflectoring.buckpal.domain.model.Money;
import io.reflectoring.buckpal.presentation.dto.AccountInfo;
import io.reflectoring.buckpal.presentation.dto.SendMoneyResponse;
import io.reflectoring.buckpal.domain.service.AccountBalanceService;
import io.reflectoring.buckpal.domain.service.AccountSendMoneyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountSendMoneyController {

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

			AccountBalanceQuery sourceQuery =
					new AccountBalanceQuery(new AccountId(sourceAccountId));

			AccountBalanceQuery targetQuery =
					new AccountBalanceQuery(new AccountId(targetAccountId));

			Long sourceBalance = accountBalanceService.getAccountBalance(sourceQuery)
					.getAmount()
					.longValue();

			Long targetBalance = accountBalanceService.getAccountBalance(targetQuery)
					.getAmount()
					.longValue();

			return new SendMoneyResponse(
					success,
					new AccountInfo(sourceAccountId, sourceBalance),
					new AccountInfo(targetAccountId, targetBalance),
					null);
		} catch (Exception e) {
			return new SendMoneyResponse(false, null, null, e.getMessage());
		}
	}

}
