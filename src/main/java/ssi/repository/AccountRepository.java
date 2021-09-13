package ssi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ssi.model.Account;

public interface AccountRepository extends JpaRepository<Account, String> {

	public Account findByAccountId(String username);
}
