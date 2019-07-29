package kr.co.broadwave.desk.notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author InSeok
 * Date : 2019-07-25
 * Remark :
 */
@Controller
@RequestMapping("notice")
public class NoticeController {

    private final NoticeService noticeService;

    @Autowired
    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }


    @RequestMapping("noticelist")
    public String noticeList(){
        return "notice/noticelist";
    }

    @RequestMapping("noticeview/{id}")
    public String noticeView(Model model, @PathVariable Long id){
        NoticeDto noticeDto = noticeService.findById(id);

        model.addAttribute("notice", noticeDto);




        return "notice/noticeview";
    }
}
