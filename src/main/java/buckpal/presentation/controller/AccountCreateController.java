package buckpal.presentation.controller;

import buckpal.business.dto.CreateCommand;
import buckpal.business.model.Account.AccountId;
import buckpal.business.model.Money;
import buckpal.presentation.dto.AccountInfo;
import buckpal.presentation.dto.CreateResponse;
import buckpal.business.service.AccountCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class AccountCreateController {

	private final AccountCreateService createAccountService;

	@PostMapping(path = "/accounts/create")
	CreateResponse createAccount(@RequestParam("initialBalance") Long initialBalance) {

		try {
			CreateCommand command = new CreateCommand(
					Money.of(initialBalance));

			AccountId accountId = createAccountService.createAccount(command);
			return new CreateResponse(true, new AccountInfo(accountId.getValue(), initialBalance), null);
		} catch (Exception e) {
			return new CreateResponse(false, null, e.getMessage());
		}
	}

}
