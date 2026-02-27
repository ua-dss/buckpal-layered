package buckpal.business.service;

import buckpal.business.model.Account;
import buckpal.data.repository.AccountJpaRepository;
import buckpal.data.repository.ActivityJpaRepository;
import buckpal.business.repository.AccountMapper;
import buckpal.data.entity.ActivityJpaEntity;
import lombok.RequiredArgsConstructor;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class AccountListAllService {

	private final AccountJpaRepository accountRepository;
	private final ActivityJpaRepository activityRepository;
	private final AccountMapper accountMapper;

	public List<Account> listAccounts() {
		return accountRepository.findAll().stream()
				.map(accountJpaEntity -> {
					List<ActivityJpaEntity> activities =
							activityRepository.findByOwner(
									accountJpaEntity.getId());

					return accountMapper.mapToDomainEntity(
							accountJpaEntity,
							activities);
				})
				.collect(Collectors.toList());
	}

}
