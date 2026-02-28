package buckpal.presentation.controller;

import buckpal.presentation.dto.AccountInfo;
import buckpal.presentation.dto.BalanceResponse;
import buckpal.business.dto.BalanceQuery;
import buckpal.business.model.Account.AccountId;
import buckpal.business.service.AccountBalanceService;
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
			BalanceQuery query = new BalanceQuery(new AccountId(accountId));

			Long balance = accountBalanceService.getAccountBalance(query)
					.getAmount()
					.longValue();

			return new BalanceResponse(true, new AccountInfo(accountId, balance), null);
		} catch (Exception e) {
			return new BalanceResponse(false, null, e.getMessage());
		}
	}
}
