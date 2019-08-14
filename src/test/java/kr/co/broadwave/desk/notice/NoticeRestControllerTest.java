package kr.co.broadwave.desk.notice;

import kr.co.broadwave.desk.accounts.Account;
import kr.co.broadwave.desk.accounts.AccountRepository;
import kr.co.broadwave.desk.accounts.AccountRole;
import kr.co.broadwave.desk.teams.Team;
import kr.co.broadwave.desk.teams.TeamRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author InSeok
 * Date : 2019-07-29
 * Remark :
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class NoticeRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    NoticeRepository noticeRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    AccountRepository accountRepository;


    @Test
    @WithMockUser(value = "nrtestuser",roles = {"ADMIN"})
    public void notice_API_reg() throws Exception {

        //given  ('test user 정보')

        Team t1 = Team.builder()
                .teamcode("NAA001")
                .teamname("TestTeam1")
                .remark("비고").build();
        teamRepository.save(t1);
        Account a1 = Account.builder()
                .userid("nrtestuser")
                .username("테스트유저")
                .password("1234")
                .email("test@naver.com")
                .role(AccountRole.ROLE_ADMIN)
                .team(t1)
                .build();
        accountRepository.save(a1);


        //when then
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/notice/reg")
                .with((csrf()))
                .param("subject","테스트공지사항제목")
                .param("content","<p>컨텐츠<p>")
        )
                .andDo(print())
                .andExpect(status().isOk());

        List<Notice> notices = noticeRepository.findTop1ByOrderById();

//        notices.forEach(e->{
//            assertThat(e.getInsert_id()).as("등록자아이디 확인 [Expect nrtestuser]").isEqualTo("nrtestuser");
//            assertThat(e.getSubject()).as("공지사항 제목 확인 [Expect 테스트공지사항제목]").isEqualTo("테스트공지사항제목");
//
//        });

        assertThat(notices.size()).as("공지사항 등록된 수 [Expect 1").isEqualTo(1);

        accountRepository.delete(a1);
        teamRepository.delete(t1);

    }
}
