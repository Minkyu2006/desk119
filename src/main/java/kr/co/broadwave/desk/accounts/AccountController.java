package kr.co.broadwave.desk.accounts;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author InSeok
 * Date : 2019-03-25
 * Time : 09:39
 * Remark : Account 관련 컨트롤러
 */
@Controller
@RequestMapping("/admin")
public class AccountController {
    //사용자등록화면
    @RequestMapping("accountreg")
    public String accoutrreg(Model model){
        model.addAttribute("roles", AccountRole.values());
        return "admin/accountreg";
    }
    //테스트 맵
    @RequestMapping("maptest")
    public String maptest(){
        return "admin/maptest";
    }
    

}
