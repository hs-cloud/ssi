package ssi.service.account;

import ssi.model.Account;
import ssi.repository.AccountRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AcountUserDetailsService implements UserDetailsService {
	@Autowired
	AccountRepository accountRepository;	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accountRepository.findByAccountId(username);
		if(account == null) {
			throw new UsernameNotFoundException(username+ "is not found.");
		}
		return new AccountUserDetails(account);
	}
}
