package buckpal.business.service;

import buckpal.business.dto.AccountCreateCommand;
import buckpal.business.model.Account;
import buckpal.business.model.Account.AccountId;
import buckpal.infrastructure.repository.AccountJpaRepository;
import buckpal.infrastructure.entity.AccountJpaEntity;
import buckpal.business.model.ActivityWindow;
import lombok.RequiredArgsConstructor;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Transactional
public class AccountCreateService {

	private final AccountJpaRepository accountRepository;

	public AccountId createAccount(AccountCreateCommand command) {
		Account newAccount = Account.withoutId(
				command.initialBalance(),
				new ActivityWindow());

		// Create JPA entity directly
		AccountJpaEntity accountJpaEntity = new AccountJpaEntity();
		accountJpaEntity.setBaselineBalance(newAccount.getBaselineBalance().getAmount().longValue());
		AccountJpaEntity savedAccount = accountRepository.save(accountJpaEntity);
		return new AccountId(savedAccount.getId());
	}

}
