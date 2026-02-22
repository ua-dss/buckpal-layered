package io.reflectoring.buckpal.presentation.controller;

import io.reflectoring.buckpal.domain.dto.AccountCreateCommand;
import io.reflectoring.buckpal.domain.model.Account.AccountId;
import io.reflectoring.buckpal.domain.model.Money;
import io.reflectoring.buckpal.presentation.dto.AccountInfo;
import io.reflectoring.buckpal.presentation.dto.CreateResponse;
import io.reflectoring.buckpal.domain.service.AccountCreateService;
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
			AccountCreateCommand command = new AccountCreateCommand(
					Money.of(initialBalance));

			AccountId accountId = createAccountService.createAccount(command);
			return new CreateResponse(true, new AccountInfo(accountId.getValue(), initialBalance), null);
		} catch (Exception e) {
			return new CreateResponse(false, null, e.getMessage());
		}
	}

}
