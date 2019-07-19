package kr.co.broadwave.desk.accounts;

import kr.co.broadwave.desk.teams.Team;
import kr.co.broadwave.desk.teams.TeamRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author InSeok
 * Date : 2019-04-23
 * Time : 09:32
 * Remark : 사용자 조회 QueryDsl 테스트
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AccountRepositoryCustomImplTest {

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountRepositoryCustom accountRepositoryCustom;

    @Test
    public void findAllBySearchStrings() {
        //given
        Team t1 = Team.builder()
                .teamcode("A001")
                .teamname("TestTeam1")
                .remark("비고").build();
        teamRepository.save(t1);
        Account a1 = Account.builder()
                .userid("S0001")
                .username("테스트유저")
                .password("1234")
                .email("test@naver.com")
                .role(AccountRole.ROLE_ADMIN)
                .team(t1)
                .build();
        Account a2 = Account.builder()
                .userid("S0002")
                .username("테스트유저2")
                .password("1234")
                .email("test2@naver.com")
                .role(AccountRole.ROLE_ADMIN)
                .team(t1)
                .build();
        Account a3 = Account.builder()
                .userid("S0003")
                .username("신규유저")
                .password("1234")
                .email("test3@naver.com")
                .role(AccountRole.ROLE_ADMIN)
                .team(t1)
                .build();
        accountRepository.save(a1);
        accountRepository.save(a2);
        accountRepository.save(a3);

        Pageable pageable = PageRequest.of(0, 2, Sort.Direction.ASC, "userid");

        //when
        Page<AccountDtoWithTeam> accounts1 = accountRepositoryCustom.findAllBySearchStrings("S000", "", "", pageable);
        Page<AccountDtoWithTeam> accounts2 = accountRepositoryCustom.findAllBySearchStrings("", "테스트", "", pageable);


        //then
        System.out.println(accounts1.getContent());
        assertThat(accounts1.getTotalPages()).isEqualTo(2);
        assertThat(accounts1.getContent().size()).isEqualTo(2);
        assertThat(accounts1.getTotalElements()).isEqualTo(3);


        assertThat(accounts2.getTotalPages()).isEqualTo(1);
        assertThat(accounts2.getContent().size()).isEqualTo(2);



        accountRepository.delete(a1);
        accountRepository.delete(a2);
        accountRepository.delete(a3);
        teamRepository.delete(t1);



    }
}