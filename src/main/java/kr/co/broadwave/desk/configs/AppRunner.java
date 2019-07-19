package kr.co.broadwave.desk.configs;

import kr.co.broadwave.desk.accounts.Account;
import kr.co.broadwave.desk.accounts.AccountRole;
import kr.co.broadwave.desk.accounts.AccountService;
import kr.co.broadwave.desk.teams.Team;
import kr.co.broadwave.desk.teams.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author InSeok
 * Date : 2019-04-01
 * Time : 14:21
 * Remark : 구동전실행(팀과 관리자 추가)
 */
@Component
public class AppRunner implements ApplicationRunner {
    @Autowired
    TeamService teamService;
    @Autowired
    AccountService accountService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        //팀저장

        Team team1 = Team.builder()
                .teamcode("T00001")
                .teamname("시스템관리")
                .remark("최초생성")
                .insertDateTime(LocalDateTime.now())
                .insert_id("system")
                .build();
        if(!teamService.findByTeamcode(team1.getTeamcode()).isPresent()) {
            teamService.tesmSave(team1);
        }else{
            team1 = teamService.findByTeamcode(team1.getTeamcode()).get();
        }

        Team team2 = Team.builder()
                .teamcode("T00002")
                .teamname("관리팀")
                .remark("최초생성")
                .insertDateTime(LocalDateTime.now())
                .insert_id("system")
                .build();
        if(!teamService.findByTeamcode(team2.getTeamcode()).isPresent()) {
            teamService.tesmSave(team2);

        }else{
            team2 = teamService.findByTeamcode(team2.getTeamcode()).get();
        }
        //사용자저장
        Account account1 = Account.builder()
                .userid("admin")
                .username("관리자")
                .email("admin@mail.com")
                .password("123789")
                .insertDateTime(LocalDateTime.now())
                .insert_id("system")
                .role(AccountRole.ROLE_ADMIN)
                .build();
        account1.setTeam(team1);
        if(!accountService.findByUserid(account1.getUserid()).isPresent()){
            accountService.saveAccount(account1);
        }


        Account account2 = Account.builder()
                .userid("hgd")
                .username("홍길동")
                .email("hgd@mail.com")
                .password("1234")
                .insertDateTime(LocalDateTime.now())
                .insert_id("system")
                .role(AccountRole.ROLE_USER)
                .build();
        account2.setTeam(team2);
        if(!accountService.findByUserid(account2.getUserid()).isPresent()) {
            accountService.saveAccount(account2);
        }





    }

}
