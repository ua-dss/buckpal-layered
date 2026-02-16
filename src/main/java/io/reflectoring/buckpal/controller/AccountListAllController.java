package io.reflectoring.buckpal.controller;

import io.reflectoring.buckpal.dto.AccountInfoDto;
import io.reflectoring.buckpal.dto.ListResponse;
import io.reflectoring.buckpal.service.AccountListAllService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
class AccountListAllController {

	private final AccountListAllService listAccountsService;

	@GetMapping(path = "/accounts")
	ListResponse listAccounts() {

		try {
			List<AccountInfoDto> accounts = listAccountsService.listAccounts().stream()
					.map(account -> new AccountInfoDto(
							account.getId().orElse(null).getValue(),
							account.getBalance().getAmount().longValue()))
					.collect(Collectors.toList());
			return new ListResponse(true, accounts, null);
		} catch (Exception e) {
			return new ListResponse(false, null, e.getMessage());
		}
	}

}
