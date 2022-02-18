package kr.co.broadwave.airmanger.configs;

import kr.co.broadwave.airmanger.Account.Account;
import kr.co.broadwave.airmanger.Account.AccountRole;
import kr.co.broadwave.airmanger.Account.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author Minkyu
 * Date : 2022-02-18
 * Time :
 * Remark : 구동전실행 -> 관리자, AS업체, 고객 기본값계정 생성
 */
@Component
public class AppRunner implements ApplicationRunner {

    @Autowired
    AccountService accountService;

    @Override
    public void run(ApplicationArguments args) throws Exception {


        Account newAccount = new Account();
        //관리자 저장
        newAccount = Account.builder()
                .userId("admin")
                .userName("관리자")
                .userEmail("admin@mail.com")
                .userPassword("123789")
                .insert_date(LocalDateTime.now())
                .insert_id("system")
                .role(AccountRole.ROLE_ADMIN)
                .build();
        if(!accountService.findByUserid(newAccount.getUserId()).isPresent()){
            accountService.saveAccount(newAccount);
        }

        newAccount = new Account();
        // AS업체 기본저장
        newAccount = Account.builder()
                .userId("manager")
                .userName("AS업체")
                .userEmail("as@mail.com")
                .userPassword("123789")
                .insert_date(LocalDateTime.now())
                .insert_id("system")
                .role(AccountRole.ROLE_MANAGER)
                .build();
        if(!accountService.findByUserid(newAccount.getUserId()).isPresent()){
            accountService.saveAccount(newAccount);
        }

        newAccount = new Account();
        //고객 저장
        newAccount = Account.builder()
                .userId("user")
                .userName("고객")
                .userEmail("user@mail.com")
                .userPassword("123789")
                .insert_date(LocalDateTime.now())
                .insert_id("system")
                .role(AccountRole.ROLE_USER)
                .build();

        if(!accountService.findByUserid(newAccount.getUserId()).isPresent()){
            accountService.saveAccount(newAccount);
        }








    }

}
