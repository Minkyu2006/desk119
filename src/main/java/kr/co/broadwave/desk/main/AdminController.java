package kr.co.broadwave.desk.main;

import kr.co.broadwave.desk.accounts.AccountRole;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author InSeok
 * Date : 2019-07-25
 * Remark :
 */
@Controller
@RequestMapping("admin")
public class AdminController {

    //사용자등록화면
    @RequestMapping("accountreg")
    public String accoutrreg(Model model){
        model.addAttribute("roles", AccountRole.values());
        return "admin/accountreg";
    }

    //사용자승인
    @RequestMapping("accountapproval")
    public String accountApproval(){
        return "admin/accountapproval";
    }

    //사용자승인
    @RequestMapping("noticereg")
    public String noticeReg(){
        return "notice/noticereg";
    }

}
