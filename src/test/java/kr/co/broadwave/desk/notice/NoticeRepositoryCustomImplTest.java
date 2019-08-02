package kr.co.broadwave.desk.notice;

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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

/**
 * @author InSeok
 * Date : 2019-07-26
 * Remark :
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class NoticeRepositoryCustomImplTest {

    @Autowired
    NoticeRepository noticeRepository;

    @Autowired
    NoticeRepositoryCustom noticeRepositoryCustom;

    @Test
    public void findAllByApproval(){
        //given
        Notice n1 = Notice.builder()
                .id(1L)
                .subject("테스트공지사항입니다.")
                .content("공지사항내용")
                .modify_name("최인석")
                .insert_name("최인석")
                .insert_id("cis")
                .modify_id("cis")
                //.insertDateTime(LocalDateTime.now())
                //.modifyDateTime(LocalDateTime.now())
                .build();
        Notice n2 = Notice.builder()
                .id(2L)
                .subject("사용법공지입니다.")
                .content("사용내용")
                .modify_name("홍길동")
                .insert_name("홍길동")
                .insert_id("hgd")
                .modify_id("hgd")
                .insertDateTime(LocalDateTime.now())
                .modifyDateTime(LocalDateTime.now())
                .build();
        Notice n3 = Notice.builder()
                .id(3L)
                .subject("세번째공지.")
                .content("사용내용")
                .modify_name("최인석")
                .insert_name("최인석")
                .insert_id("cis")
                .modify_id("cis")
                .insertDateTime(LocalDateTime.now())
                .modifyDateTime(LocalDateTime.now())
                .build();

        noticeRepository.saveAll(Arrays.asList(n1,n2,n3));
        Pageable pageable = PageRequest.of(0, 2, Sort.Direction.ASC, "id");

        //when
        Page<NoticeDto> noticeDtoPage1 = noticeRepositoryCustom.findAllBySearchStrings("", "최인석", pageable);
        Page<NoticeDto> noticeDtoPage2 = noticeRepositoryCustom.findAllBySearchStrings("공지", "", pageable);


        //then

        System.out.println(noticeDtoPage1.getContent());
        assertThat(noticeDtoPage1.getTotalElements()).as("작성자가 최인석 인 공지검색 [expect 2]").isEqualTo(2);
        assertThat(noticeDtoPage2.getTotalElements()).as("제목에 공지라는 글자가 포함된 인 공지사항검색 [expect 3]").isEqualTo(3);

        noticeRepository.delete(n1);
        noticeRepository.delete(n2);
        noticeRepository.delete(n3);


    }

}