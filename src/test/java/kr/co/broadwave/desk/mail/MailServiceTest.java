package kr.co.broadwave.desk.mail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * @author InSeok
 * Date : 2019-09-24
 * Remark :
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class MailServiceTest {

    @Autowired
    MailService mailService;

    @Test
    public void mailSend(){
//        List<String> maillists = Arrays.asList("gkhtls2006@broadwave.co.kr","gkstls2006@naver.com","gkstls2017gmail.com","brian20@nate.com","desksystem119@gmail.com");
//        mailService.mailsend(maillists,"테스트제목 입니다.","테스트내용 입니다.");
    }

}

