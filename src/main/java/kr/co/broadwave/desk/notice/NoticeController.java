package kr.co.broadwave.desk.notice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author InSeok
 * Date : 2019-07-25
 * Remark :
 */
@Controller
@RequestMapping("notice")
public class NoticeController {
    @RequestMapping("noticelist")
    public String noticeList(){
        return "notice/noticelist";
    }

    @RequestMapping("noticeview")
    public String noticeView(){
        return "notice/noticeview";
    }
}
