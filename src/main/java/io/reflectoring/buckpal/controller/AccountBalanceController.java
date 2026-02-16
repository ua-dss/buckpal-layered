package io.reflectoring.buckpal.controller;

import io.reflectoring.buckpal.dto.AccountInfoDto;
import io.reflectoring.buckpal.dto.BalanceResponse;
import io.reflectoring.buckpal.dto.GetAccountBalanceQuery;
import io.reflectoring.buckpal.entity.Account.AccountId;
import io.reflectoring.buckpal.service.AccountBalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class AccountBalanceController {

	private final AccountBalanceService accountBalanceService;

	@GetMapping(path = "/accounts/balance")
	BalanceResponse getAccountBalance(@RequestParam("accountId") Long accountId) {

		try {
			GetAccountBalanceQuery query =
					new GetAccountBalanceQuery(new AccountId(accountId));

			Long balance = accountBalanceService.getAccountBalance(query)
					.getAmount()
					.longValue();

			return new BalanceResponse(true, new AccountInfoDto(accountId, balance), null);
		} catch (Exception e) {
			return new BalanceResponse(false, null, e.getMessage());
		}
	}
}
