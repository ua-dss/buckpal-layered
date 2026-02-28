package buckpal.presentation.controller;

import buckpal.business.dto.SendMoneyCommand;
import buckpal.business.dto.BalanceQuery;
import buckpal.business.model.Account.AccountId;
import buckpal.business.model.Money;
import buckpal.presentation.dto.AccountInfo;
import buckpal.presentation.dto.SendMoneyResponse;
import buckpal.business.service.AccountBalanceService;
import buckpal.business.service.AccountSendMoneyService;
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
			SendMoneyCommand command = new SendMoneyCommand(
					new AccountId(sourceAccountId),
					new AccountId(targetAccountId),
					Money.of(amount));

			boolean success = sendMoneyService.sendMoney(command);

			BalanceQuery sourceQuery = new BalanceQuery(new AccountId(sourceAccountId));

			BalanceQuery targetQuery = new BalanceQuery(new AccountId(targetAccountId));

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
