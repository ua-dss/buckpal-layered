package buckpal.presentation.controller;

import buckpal.business.dto.AccountDepositCommand;
import buckpal.business.dto.AccountBalanceQuery;
import buckpal.business.model.Account.AccountId;
import buckpal.business.model.Money;
import buckpal.presentation.dto.AccountInfo;
import buckpal.presentation.dto.DepositResponse;
import buckpal.business.service.AccountBalanceService;
import buckpal.business.service.AccountDepositService;
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
