package kr.co.broadwave.airmanger.controller;

import kr.co.broadwave.airmanger.Account.Account;
import kr.co.broadwave.airmanger.Account.Service.AccountService;
import kr.co.broadwave.airmanger.LoginLog.Loginlog;
import kr.co.broadwave.airmanger.LoginLog.LoginlogService;
import kr.co.broadwave.airmanger.common.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * @author Minkuy
 * Date : 2022-02-18
 * Time :
 * Remark : 메인 컨트롤러
 */
@Slf4j
@Controller
public class Maincontroller {

    private final AccountService accountService;
    private final LoginlogService loginlogService;

    @Autowired
    public Maincontroller(AccountService accountService, LoginlogService loginlogService) {
        this.accountService = accountService;
        this.loginlogService = loginlogService;
    }

    // 기본 메인화면
    @RequestMapping("/")
    public String main(){
        return "index";
    }

    // 관리자 메인화면
    @RequestMapping("/adminindex")
    public String adminMain(){
        return "/admin/adminindex";
    }

    // AS업체 메인화면
    @RequestMapping("/manager")
    public String managerMain(){
        return "/main/resources/templates/manager/managerindex.html";
    }

    // 고객 메인화면
    @RequestMapping("/user")
    public String userMain(){
        return "/main/resources/templates/user/userindex.html";
    }

    @RequestMapping("/loginsuccess")
    public String loginsuccess(HttpServletRequest request){

        //Security 로그인정보가져와서 세션에 저장하자
        HttpSession session = request.getSession();
        String login_ip = CommonUtils.getIp(request);
        //User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String role = null;
        Optional<Account> optionalAccount = accountService.findByUserid(request.getUserPrincipal().getName());
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();

            role = account.getRole().getCode();

            //userid 세션저장
            session.setAttribute("userid", account.getUserId());
            session.setAttribute("username", account.getUserName());
            session.setAttribute("role", account.getRole().getCode());

            Loginlog loginlog = Loginlog.builder()
                    .accountId(account)
                    .userId(account.getUserId())
                    .successYN("Y")
                    .loginIp(login_ip)
                    .build();
            loginlogService.save(loginlog);

            log.info("============Login Success============");
            log.info(" session userid " + session.getAttribute("userid"));
            log.info(" session username " + session.getAttribute("username"));
            log.info(" session role " + session.getAttribute("role"));
            log.info("=====================================");
        }

        log.info("로그인성공 현재 권한 : "+role);
//        if()
        return "redirect:/";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request){
        String referrer = request.getHeader("Referer");
        request.getSession().setAttribute("prevPage", referrer);
        return "login";
    }

}
