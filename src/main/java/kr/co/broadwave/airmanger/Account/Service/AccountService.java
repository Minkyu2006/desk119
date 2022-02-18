package kr.co.broadwave.airmanger.Account.Service;

import kr.co.broadwave.airmanger.Account.Account;
import kr.co.broadwave.airmanger.Account.AccountRepository;
import kr.co.broadwave.airmanger.Account.AccountRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Minkyu
 * Date : 2022-02-18
 * Time :
 * Remark : Account Service
 */
@Service
public class AccountService {

    AccountRepository accountRepository;
    AccountRepositoryCustom accountRepositoryCustom;
    PasswordEncoder passwordEncoder;

    @Autowired
    public AccountService(AccountRepository accountRepository, AccountRepositoryCustom accountRepositoryCustom, PasswordEncoder passwordEncoder){
        this.accountRepository = accountRepository;
        this.accountRepositoryCustom = accountRepositoryCustom;
        this.passwordEncoder = passwordEncoder;
    }

    public Account saveAccount(Account account){
        account.setUserPassword(passwordEncoder.encode(account.getUserPassword()));
        return this.accountRepository.save(account);
    }

    public Optional<Account> findByUserid(String userid ){
        return accountRepository.findByUserid(userid);
    }


}
