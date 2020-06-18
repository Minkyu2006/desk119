package kr.co.broadwave.desk.controller;

import kr.co.broadwave.desk.accounts.*;
import kr.co.broadwave.desk.bscodes.*;
import kr.co.broadwave.desk.common.CommonUtils;
import kr.co.broadwave.desk.mastercode.MasterCodeDto;
import kr.co.broadwave.desk.mastercode.MasterCodeService;
import kr.co.broadwave.desk.record.Record;
import kr.co.broadwave.desk.record.RecordService;
import kr.co.broadwave.desk.teams.TeamDto;
import kr.co.broadwave.desk.teams.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author InSeok
 * Date : 2019-03-25
 * Time : 09:17
 * Remark : 메인 컨트롤러
 */
@Slf4j
@Controller
public class Maincontroller {

    private final AccountService accountService;
    private final LoginlogService loginlogService;
    private final MasterCodeService masterCodeService;
    private final TeamService teamService;
    private final RecordService recordService;

    @Autowired
    public Maincontroller(AccountService accountService, RecordService recordService,LoginlogService loginlogService, MasterCodeService masterCodeService, TeamService teamService) {
        this.accountService = accountService;
        this.recordService = recordService;
        this.loginlogService = loginlogService;
        this.masterCodeService = masterCodeService;
        this.teamService = teamService;
    }

    //메인화면
    @RequestMapping("/")
    public String main(Model model){
        List<Record> records = recordService.findAll();
        List<String> arDisasterTypes = new ArrayList<>();
        List<String> arFacTypes = new ArrayList<>();
        List<Integer> arState  = new ArrayList<>();
        records.forEach(x -> arState.add(x.getArRecordState()));

        for(int i =0; i<records.size(); i++) {
            if(arState.get(i) == 1){
                arDisasterTypes.add(records.get(i).getArDisasterType());
                arFacTypes.add(records.get(i).getArFac());
            }
        }

        List<String> arDisasters = recordService.arDisaster(arDisasterTypes);
        List<String> arFacs = recordService.arFac(arFacTypes);

        model.addAttribute("arDisasters",arDisasters);
        model.addAttribute("arFacs",arFacs);

        return "index";
    }

    @RequestMapping("/testwebpage")
    public String test(){

        return "testwebpage";
    }

    @RequestMapping("/mypage")
    public String mypage(Model model, HttpServletRequest request){
        String userid = CommonUtils.getCurrentuser(request);
        Account account = accountService.findByUserid(userid).get();

        model.addAttribute("account",account);
        return "mypage";
    }


    @RequestMapping("/loginsuccess")
    public String loginsuccess(HttpServletRequest request){

        //Security 로그인정보가져와서 세션에 저장하자
        HttpSession session = request.getSession();
        String login_ip = CommonUtils.getIp(request);
        //User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<Account> optionalAccount = accountService.findByUserid(request.getUserPrincipal().getName());
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();

            //userid 세션저장
            session.setAttribute("userid", account.getUserid());
            session.setAttribute("username", account.getUsername());
            session.setAttribute("teamname", account.getTeam().getTeamname());
            session.setAttribute("role", account.getRole().getCode());

            Loginlog loginlog = Loginlog.builder()
                    .loginAccount(account)
                    .userid(account.getUserid())
                    .successYN("Y")
                    .loginIp(login_ip)
                    .build();
            loginlogService.save(loginlog);

            log.info("============Login Success============");
            log.info(" session userid " + session.getAttribute("userid"));
            log.info(" session username " + session.getAttribute("username"));
            log.info(" session teamname " + session.getAttribute("teamname"));
            log.info(" session role " + session.getAttribute("role"));
            log.info("=====================================");
        }

        return "redirect:/";
    }




    @RequestMapping("/login")
    public String login(HttpServletRequest request){

        String referrer = request.getHeader("Referer");
        request.getSession().setAttribute("prevPage", referrer);

        return "login";
    }

    @RequestMapping("/signup")
    public String siguup(Model model){
        List<MasterCodeDto> positions = masterCodeService.findCodeList(CodeType.C0001); // 직급코드가져오기
        model.addAttribute("DisasterTypes", DisasterType.values());
        model.addAttribute("CollapseTypes", CollapseType.values());

        model.addAttribute("LocationAddressTypes", LocationAddressType.values());

        model.addAttribute("roles", AccountRole.values());

        List<TeamDto> teams = teamService.findTeamList();
        model.addAttribute("roles", AccountRole.values());
        model.addAttribute("positions", positions);
        model.addAttribute("teams", teams);

        return "signup";
    }

}
