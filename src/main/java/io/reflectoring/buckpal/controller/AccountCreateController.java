package io.reflectoring.buckpal.controller;

import io.reflectoring.buckpal.dto.AccountCreateCommand;
import io.reflectoring.buckpal.dto.AccountInfoDto;
import io.reflectoring.buckpal.dto.CreateResponse;
import io.reflectoring.buckpal.entity.Account.AccountId;
import io.reflectoring.buckpal.entity.Money;
import io.reflectoring.buckpal.service.AccountCreateService;
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
			return new CreateResponse(true, new AccountInfoDto(accountId.getValue(), initialBalance), null);
		} catch (Exception e) {
			return new CreateResponse(false, null, e.getMessage());
		}
	}

}
