package io.reflectoring.buckpal.presentation.controller;

import io.reflectoring.buckpal.presentation.dto.AccountInfo;
import io.reflectoring.buckpal.presentation.dto.BalanceResponse;
import io.reflectoring.buckpal.domain.dto.AccountBalanceQuery;
import io.reflectoring.buckpal.domain.model.Account.AccountId;
import io.reflectoring.buckpal.domain.service.AccountBalanceService;
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
			AccountBalanceQuery query =
					new AccountBalanceQuery(new AccountId(accountId));

			Long balance = accountBalanceService.getAccountBalance(query)
					.getAmount()
					.longValue();

			return new BalanceResponse(true, new AccountInfo(accountId, balance), null);
		} catch (Exception e) {
			return new BalanceResponse(false, null, e.getMessage());
		}
	}
}
