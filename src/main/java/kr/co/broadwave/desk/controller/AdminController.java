package kr.co.broadwave.desk.controller;

import kr.co.broadwave.desk.accounts.AccountRole;
import kr.co.broadwave.desk.bscodes.CodeType;
import kr.co.broadwave.desk.mastercode.MasterCodeDto;
import kr.co.broadwave.desk.mastercode.MasterCodeService;
import kr.co.broadwave.desk.notice.*;
import kr.co.broadwave.desk.notice.file.UploadFile;
import kr.co.broadwave.desk.teams.TeamDto;
import kr.co.broadwave.desk.teams.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author InSeok
 * Date : 2019-07-25
 * Remark :
 */
@Controller
@RequestMapping("admin")
public class AdminController {

    private final NoticeService noticeService;
    private final ImageService imageService;
    private final MasterCodeService masterCodeService;
    private final TeamService teamService;

    @Autowired
    public AdminController(NoticeService noticeService, ImageService imageService, MasterCodeService masterCodeService, TeamService teamService) {
        this.noticeService = noticeService;
        this.imageService = imageService;

        this.masterCodeService = masterCodeService;
        this.teamService = teamService;
    }

    //사용자등록화면
    @RequestMapping("accountreg")
    public String accoutrreg(Model model){

        List<MasterCodeDto> positions = masterCodeService.findCodeList(CodeType.C0001); // 직급코드가져오기
        List<TeamDto> teams = teamService.findTeamList();
        model.addAttribute("roles", AccountRole.values());
        model.addAttribute("positions", positions);
        model.addAttribute("teams", teams);

        return "admin/accountreg";
    }

    //사용자승인
    @RequestMapping("accountapproval")
    public String accountApproval(){
        return "admin/accountapproval";
    }


    //관리코드등록
    @RequestMapping("mastercodereg")
    public String masterCodeReg(Model model){
        model.addAttribute("codetypes", CodeType.values());
        return "admin/mastercodereg";
    }

    //공지사항등록
    @RequestMapping("noticereg")
    public String noticeReg(){
        return "notice/noticereg";
    }

    //공지사항조회
    @RequestMapping("noticereg/{id}")
    public String noticeReg(Model model, @PathVariable Long id){
        NoticeDto noticeDto = noticeService.findById(id);

        List<UploadFile> uploadFiles = imageService.uploadFileList(id);
        if (noticeDto != null) {
            model.addAttribute("valueExist", true);
            model.addAttribute("notice", noticeDto);
            model.addAttribute("uploadFiles", uploadFiles);
        }else{
            model.addAttribute("valueExist", false);
        }

        return "notice/noticereg";
    }

}
