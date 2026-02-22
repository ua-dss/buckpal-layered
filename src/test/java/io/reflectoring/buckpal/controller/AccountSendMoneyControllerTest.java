package io.reflectoring.buckpal.controller;

import io.reflectoring.buckpal.domain.dto.AccountSendMoneyCommand;
import io.reflectoring.buckpal.domain.dto.AccountBalanceQuery;
import io.reflectoring.buckpal.domain.model.Account.AccountId;
import io.reflectoring.buckpal.domain.model.Money;
import io.reflectoring.buckpal.presentation.controller.AccountSendMoneyController;
import io.reflectoring.buckpal.domain.service.AccountBalanceService;
import io.reflectoring.buckpal.domain.service.AccountSendMoneyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AccountSendMoneyController.class)
class AccountSendMoneyControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AccountSendMoneyService sendMoneyService;

	@MockBean
	private AccountBalanceService accountBalanceService;

	@Test
	void testSendMoney() throws Exception {

		given(accountBalanceService.getAccountBalance(any(AccountBalanceQuery.class)))
				.willReturn(Money.of(1000L));

		mockMvc.perform(post("/accounts/send")
				.param("sourceAccountId", "41")
				.param("targetAccountId", "42")
				.param("amount", "500")
				.header("Content-Type", "application/json"))
				.andExpect(status().isOk());

		then(sendMoneyService).should()
				.sendMoney(eq(new AccountSendMoneyCommand(
						new AccountId(41L),
						new AccountId(42L),
						Money.of(500L))));
	}

}
