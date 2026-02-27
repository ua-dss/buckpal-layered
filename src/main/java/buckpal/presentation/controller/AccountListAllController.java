package buckpal.presentation.controller;

import buckpal.presentation.dto.AccountInfo;
import buckpal.presentation.dto.ListResponse;
import buckpal.business.service.AccountListAllService;
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
			List<AccountInfo> accounts = listAccountsService.listAccounts().stream()
					.map(account -> new AccountInfo(
							account.getId().orElse(null).getValue(),
							account.getBalance().getAmount().longValue()))
					.collect(Collectors.toList());
			return new ListResponse(true, accounts, null);
		} catch (Exception e) {
			return new ListResponse(false, null, e.getMessage());
		}
	}

}
