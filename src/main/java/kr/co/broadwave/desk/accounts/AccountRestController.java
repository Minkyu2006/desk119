package kr.co.broadwave.desk.accounts;

import kr.co.broadwave.desk.bscodes.ApprovalType;
import kr.co.broadwave.desk.bscodes.CollapseType;
import kr.co.broadwave.desk.bscodes.DisasterType;
import kr.co.broadwave.desk.common.AjaxResponse;
import kr.co.broadwave.desk.common.CommonUtils;
import kr.co.broadwave.desk.common.ResponseErrorCode;
import kr.co.broadwave.desk.mastercode.MasterCode;
import kr.co.broadwave.desk.mastercode.MasterCodeService;
import kr.co.broadwave.desk.teams.Team;
import kr.co.broadwave.desk.teams.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;


/**
 * @author InSeok
 * Date : 2019-03-25
 * Time : 10:07
 * Remark : Account 용 Rest Controller
 */
@Slf4j
@RestController
@RequestMapping("/api/account")
public class AccountRestController {

    private AjaxResponse res = new AjaxResponse();
    HashMap<String, Object> data = new HashMap<>();

    private final AccountService accountService;
    private final TeamService teamService;
    private final ModelMapper modelMapper;
    private final LoginlogService loginlogService;
    private final MasterCodeService masterCodeService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountRestController(AccountService accountService, ModelMapper modelMapper
            , TeamService teamService, LoginlogService loginlogService, MasterCodeService masterCodeService, PasswordEncoder passwordEncoder) {
        this.accountService = accountService;
        this.modelMapper = modelMapper;
        this.teamService = teamService;
        this.loginlogService = loginlogService;
        this.masterCodeService = masterCodeService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("reg")
    public ResponseEntity<Map<String,Object>> accountSave(@ModelAttribute AccountMapperDto accountMapperDto, HttpServletRequest request){


        Account account = modelMapper.map(accountMapperDto, Account.class);

        Optional<Team> optionalTeam = teamService.findByTeamcode(accountMapperDto.getTeamcode());
        Optional<MasterCode> optionalPositionCode = masterCodeService.findById(accountMapperDto.getPositionid());


        //패스워드를 입력하세요.
        if (accountMapperDto.getPassword() == null || accountMapperDto.getPassword() ==""){
            log.info(ResponseErrorCode.E006.getDesc());
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E006.getCode(), ResponseErrorCode.E006.getDesc()));
        }
        //아이디를 입력하세요.
        if (accountMapperDto.getUserid() == null || accountMapperDto.getUserid() ==""){
            log.info(ResponseErrorCode.E007.getDesc());
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E007.getCode(), ResponseErrorCode.E007.getDesc()));
        }

        //부서코드가 존재하지않으면
        if (!optionalTeam.isPresent()) {
            log.info(" 선택한 부서 DB 존재 여부 체크.  부서코드: '" + accountMapperDto.getTeamcode() +"'");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E005.getCode(), ResponseErrorCode.E005.getDesc()));
        }else{
            Team team = optionalTeam.get();
            account.setTeam(team);
        }
        //직급코드가 존재하지않으면
        if (!optionalPositionCode.isPresent()) {
            log.info(" 선택한 직급 DB 존재 여부 체크.  직급코드: '" + accountMapperDto.getPositionid() +"'");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E016.getCode(), ResponseErrorCode.E016.getDesc()));
        }else{
            account.setPosition(optionalPositionCode.get());
        }


        Optional<Account> optionalAccount = accountService.findByUserid(account.getUserid());

        String currentuserid = CommonUtils.getCurrentuser(request);


        //신규일때
        if (accountMapperDto.getMode().equals("N")) {
            //userid 중복체크
            if (optionalAccount.isPresent()) {
                log.info("사용자저장실패(사용자아이디중복) 사용자아이디: '" + account.getUserid() + "'");
                return ResponseEntity.ok(res.fail(ResponseErrorCode.E001.getCode(), ResponseErrorCode.E001.getDesc()));
            }
            account.setInsert_id(currentuserid);
            account.setInsertDateTime(LocalDateTime.now());
        }else{
            //수정일때
            if(!optionalAccount.isPresent()){
                log.info("사용자정보수정실패 : 사용자아이디: '" + account.getUserid() + "'");
                return ResponseEntity.ok(res.fail(ResponseErrorCode.E004.getCode(), ResponseErrorCode.E004.getDesc()));
            }else{
                account.setId(optionalAccount.get().getId());
                account.setInsert_id(optionalAccount.get().getInsert_id());
                account.setInsertDateTime(optionalAccount.get().getInsertDateTime());
            }
            account.setModify_id(currentuserid);
            account.setModifyDateTime(LocalDateTime.now());

        }

        Account accountSave =  this.accountService.saveAccount(account);

        log.info("사용자 저장 성공 : '" + accountSave.getUserid() +"'" );
        return ResponseEntity.ok(res.success());

    }

    @PostMapping("modifyReg")
    public ResponseEntity<Map<String,Object>> accountModifySave(@ModelAttribute AccountMapperDto accountMapperDto, HttpServletRequest request){


        Account account = modelMapper.map(accountMapperDto, Account.class);
        Optional<Team> optionalTeam = teamService.findByTeamcode(accountMapperDto.getTeamcode());
        Optional<MasterCode> optionalPositionCode = masterCodeService.findById(accountMapperDto.getPositionid());

        //아이디를 입력하세요.
        if (accountMapperDto.getUserid() == null || accountMapperDto.getUserid() ==""){
            log.info(ResponseErrorCode.E007.getDesc());
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E007.getCode(), ResponseErrorCode.E007.getDesc()));
        }

        //부서코드가 존재하지않으면
        if (!optionalTeam.isPresent()) {
            log.info(" 선택한 부서 DB 존재 여부 체크.  부서코드: '" + accountMapperDto.getTeamcode() +"'");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E005.getCode(), ResponseErrorCode.E005.getDesc()));
        }else{
            Team team = optionalTeam.get();
            account.setTeam(team);
        }
        //직급코드가 존재하지않으면
        if (!optionalPositionCode.isPresent()) {
            log.info(" 선택한 직급 DB 존재 여부 체크.  직급코드: '" + accountMapperDto.getPositionid() +"'");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E016.getCode(), ResponseErrorCode.E016.getDesc()));
        }else{
            account.setPosition(optionalPositionCode.get());
        }


        Optional<Account> optionalAccount = accountService.findByUserid(account.getUserid());

        String currentuserid = CommonUtils.getCurrentuser(request);




        //수정일때
        if(!optionalAccount.isPresent()){
            log.info("사용자 일반 관리자(일반정보) : 사용자아이디: '" + account.getUserid() + "'");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E004.getCode(), ResponseErrorCode.E004.getDesc()));
        }else{
            account.setId(optionalAccount.get().getId());
            account.setInsert_id(optionalAccount.get().getInsert_id());
            account.setInsertDateTime(optionalAccount.get().getInsertDateTime());
            account.setPassword(optionalAccount.get().getPassword());
            account.setApprovalType(optionalAccount.get().getApprovalType());
        }
        account.setModify_id(currentuserid);
        account.setModifyDateTime(LocalDateTime.now());





        Account accountSave =  this.accountService.updateAccount(account);

        log.info("사용자 관리자(일반정보) 수정 성공 '" + accountMapperDto.getUserid() +"'" );
        return ResponseEntity.ok(res.success());

    }

    @PostMapping("modifyAdminPassword")
    public ResponseEntity accountmodifyAdminPassword(@ModelAttribute AccountMapperDto accountMapperDto, HttpServletRequest request){


        Account account = modelMapper.map(accountMapperDto, Account.class);
        Optional<Team> optionalTeam = teamService.findByTeamcode(accountMapperDto.getTeamcode());
        Optional<MasterCode> optionalPositionCode = masterCodeService.findById(accountMapperDto.getPositionid());

        //아이디를 입력하세요.
        if (accountMapperDto.getUserid() == null || accountMapperDto.getUserid() ==""){
            log.info(ResponseErrorCode.E007.getDesc());
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E007.getCode(), ResponseErrorCode.E007.getDesc()));
        }



        Optional<Account> optionalAccount = accountService.findByUserid(account.getUserid());

        String currentuserid = CommonUtils.getCurrentuser(request);




        //수정일때
        if(!optionalAccount.isPresent()){
            log.info("사용자 관리자(패스워드) 실패 : 사용자아이디: '" + account.getUserid() + "'");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E004.getCode(), ResponseErrorCode.E004.getDesc()));
        }else{
            account.setId(optionalAccount.get().getId());
            account.setInsert_id(optionalAccount.get().getInsert_id());
            account.setInsertDateTime(optionalAccount.get().getInsertDateTime());
            account.setUsername(optionalAccount.get().getUsername());
            account.setCellphone(optionalAccount.get().getCellphone());
            account.setEmail(optionalAccount.get().getEmail());
            account.setTeam(optionalAccount.get().getTeam());
            account.setApprovalType(optionalAccount.get().getApprovalType());
            account.setPosition(optionalAccount.get().getPosition());
            account.setRole(optionalAccount.get().getRole());
        }
        account.setModify_id(currentuserid);
        account.setModifyDateTime(LocalDateTime.now());


        Account accountSave =  this.accountService.saveAccount(account);

        log.info("사용자 관리자(패스워드) 수정 성공 '" + accountMapperDto.getUserid() +"'" );
        return ResponseEntity.ok(res.success());

    }

    //회원가입처리
    @PostMapping("signup")
    public ResponseEntity<Map<String,Object>> signup(@ModelAttribute AccountMapperDto accountMapperDto){

        Account account = modelMapper.map(accountMapperDto, Account.class);
        Optional<Team> optionalTeam = teamService.findByTeamcode(accountMapperDto.getTeamcode());
        Optional<MasterCode> optionalPositionCode = masterCodeService.findById(accountMapperDto.getPositionid());

        //패스워드를 입력하세요.
        if (accountMapperDto.getPassword() == null || accountMapperDto.getPassword().equals("")){
            log.info(ResponseErrorCode.E006.getDesc());
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E006.getCode(), ResponseErrorCode.E006.getDesc()));
        }
        //아이디를 입력하세요.
        if (accountMapperDto.getUserid() == null || accountMapperDto.getUserid().equals("")){
            log.info(ResponseErrorCode.E007.getDesc());
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E007.getCode(), ResponseErrorCode.E007.getDesc()));
        }

        Optional<Account> optionalAccount = accountService.findByUserid(account.getUserid());

        //userid 중복체크
        if (optionalAccount.isPresent()) {
            log.info("회원가입저장실패(아이디중복) 사용자아이디: '" + account.getUserid() + "'");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E008.getCode(), ResponseErrorCode.E008.getDesc()));
        }
        account.setInsert_id("signUp");
        account.setInsertDateTime(LocalDateTime.now());
        account.setApprovalType(ApprovalType.AT01); // 미승인상태로 회원가입


        //부서코드가 존재하지않으면
        if (!optionalTeam.isPresent()) {
            log.info(" 선택한 부서 DB 존재 여부 체크.  부서코드: '" + accountMapperDto.getTeamcode() +"'");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E005.getCode(), ResponseErrorCode.E005.getDesc()));
        }else{
            Team team = optionalTeam.get();
            account.setTeam(team);
        }
        //직급코드가 존재하지않으면
        if (!optionalPositionCode.isPresent()) {
            log.info(" 선택한 직급 DB 존재 여부 체크.  직급코드: '" + accountMapperDto.getPositionid() +"'");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E016.getCode(), ResponseErrorCode.E016.getDesc()));
        }else{
            account.setPosition(optionalPositionCode.get());
        }

        Account accountSave = accountService.saveAccount(account);

        log.info("회원가입 저장 성공 : '" + accountSave.getUserid() +"'" );

        return ResponseEntity.ok(res.success());
    }

    @PostMapping("modifyemail")
    public ResponseEntity<Map<String,Object>> accountSaveEmail(@ModelAttribute AccountMapperDtoModify accountMapperDto, HttpServletRequest request){


        Account account = modelMapper.map(accountMapperDto, Account.class);


        //아이디를 입력하세요.
        if (accountMapperDto.getUserid() == null || accountMapperDto.getUserid().equals("")){
            log.info(ResponseErrorCode.E009.getDesc());
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E009.getCode(), ResponseErrorCode.E009.getDesc()));
        }


        Optional<Account> optionalAccount = accountService.findByUserid(account.getUserid());

        String currentuserid = CommonUtils.getCurrentuser(request);


        //수정일때
        if(!optionalAccount.isPresent()){
            log.info("사용자정보 수정실패 : 사용자아이디: '" + account.getUserid() + "'");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E004.getCode(), ResponseErrorCode.E004.getDesc()));
        }else{
            account.setId(optionalAccount.get().getId());
            account.setInsert_id(optionalAccount.get().getInsert_id());
            account.setInsertDateTime(optionalAccount.get().getInsertDateTime());
            account.setPassword(optionalAccount.get().getPassword());
            account.setTeam(optionalAccount.get().getTeam());
            account.setRole(optionalAccount.get().getRole());
            account.setUsername(optionalAccount.get().getUsername());
            account.setApprovalType(optionalAccount.get().getApprovalType());
            account.setPosition(optionalAccount.get().getPosition());

        }
        account.setModify_id(currentuserid);
        account.setModifyDateTime(LocalDateTime.now());




        Account accountSave =  this.accountService.modifyAccount(account);

        log.info("사용자정보 수정 성공 : + " + accountMapperDto.getUserid() +"'" );
        return ResponseEntity.ok(res.success());

    }

    @PostMapping("modifypassword")
    public ResponseEntity<Map<String,Object>> accountSavepassword(@ModelAttribute AccountMapperDtoModify accountMapperDto, HttpServletRequest request){


        Account account = modelMapper.map(accountMapperDto, Account.class);



        //아이디를 입력하세요.
        if (accountMapperDto.getUserid() == null || accountMapperDto.getUserid() ==""){
            log.info(ResponseErrorCode.E009.getDesc());
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E009.getCode(), ResponseErrorCode.E009.getDesc()));
        }


        Optional<Account> optionalAccount = accountService.findByUserid(account.getUserid());

        String currentuserid = CommonUtils.getCurrentuser(request);



        //수정일때
        if(!optionalAccount.isPresent()){
            log.info("사용자정보(패스워드)수정 실패 : 사용자아이디: '" + account.getUserid() + "'");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E004.getCode(), ResponseErrorCode.E004.getDesc()));
        }else{
            //현재암호비교
            if (!passwordEncoder.matches(accountMapperDto.getOldpassword(),optionalAccount.get().getPassword())){
                return ResponseEntity.ok(res.fail(ResponseErrorCode.E010.getCode(), ResponseErrorCode.E010.getDesc()));
            }
            if( !accountMapperDto.getPassword().equals(accountMapperDto.getPasswordconfirm()) ){
                return ResponseEntity.ok(res.fail(ResponseErrorCode.E011.getCode(), ResponseErrorCode.E011.getDesc()));
            }

            account.setId(optionalAccount.get().getId());
            account.setInsert_id(optionalAccount.get().getInsert_id());
            account.setInsertDateTime(optionalAccount.get().getInsertDateTime());
            account.setEmail(optionalAccount.get().getEmail());
            account.setTeam(optionalAccount.get().getTeam());
            account.setRole(optionalAccount.get().getRole());
            account.setUsername(optionalAccount.get().getUsername());
            account.setApprovalType(optionalAccount.get().getApprovalType());
            account.setPosition(optionalAccount.get().getPosition());

        }
        account.setModify_id(currentuserid);
        account.setModifyDateTime(LocalDateTime.now());




        Account accountSave =  this.accountService.saveAccount(account);

        log.info("사용자정보(패스워드)수정 성공 :  " + accountMapperDto.getUserid() +"'" );
        return ResponseEntity.ok(res.success());

    }


    @PostMapping("list")
    public ResponseEntity<Map<String,Object>> accountList(@RequestParam(value="userid", defaultValue="") String userid,
                                      @RequestParam(value="username", defaultValue="") String username,
                                      @RequestParam(value="email", defaultValue="") String email,
                                      Pageable pageable){

//        log.info("사용자 리스트 조회 / 조회조건 : userid / '" + userid + "' username / '" + username + "', email / '" + email + "'");


        Page<AccountDtoWithTeam> accounts = this.accountService.findAllBySearchStrings(userid, username, email, pageable);
        return CommonUtils.ResponseEntityPage(accounts);

    }

    @PostMapping("approvallist")
    public ResponseEntity<Map<String,Object>> accountApprovalList(
                                      @RequestParam(value="username", defaultValue="") String username,
                                      @RequestParam(value="startdate", defaultValue="") String startdate,
                                      @RequestParam(value="enddate", defaultValue="") String enddate,
                                      Pageable pageable){

        log.info("회원가입 승인조회 / 조회조건 : username / '" + username + "', startdate / '" + startdate + "', enddate / '" + enddate + "'");


        Page<AccountDto> accounts = this.accountService.findAllByApproval(username,startdate,enddate, pageable);
        return CommonUtils.ResponseEntityPage(accounts);

    }

    //회원가입 승인처리
    @PostMapping("approval")
    public ResponseEntity<Map<String,Object>> saveApproval(
                                    @RequestParam(value="userid", defaultValue="") String userid,
                                    @RequestParam(value="approvaltype", defaultValue="") String approvaltype,
                                    HttpServletRequest request
                                    ){
        Optional<Account> optionalAccount = accountService.findByUserid(userid);


        if(!optionalAccount.isPresent()){
            log.info("회원가입 승인처리실패 : 사용자아이디: '" + userid + "'");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E012.getCode(), ResponseErrorCode.E012.getDesc()));
        }


        String currentuserid = CommonUtils.getCurrentuser(request);

        Long aLong = accountService.saveApproval(optionalAccount.get(), ApprovalType.valueOf(approvaltype), currentuserid);
        if (!aLong.equals(1L)){
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E013.getCode(), ResponseErrorCode.E013.getDesc()));
        }

        return ResponseEntity.ok(res.success());
    }

    //사용자삭제
    @PostMapping("del")
    public ResponseEntity<Map<String,Object>> accountdel(@RequestParam (value="userid", defaultValue="") String userid
                                     ){
        log.info("사용자 삭제 / userid: " + userid );
        Optional<Account> optionalAccount = accountService.findByUserid(userid);
        //정보가있는지 체크
        if (!optionalAccount.isPresent()){
            log.info("사용자삭제실패 : 삭제할 데이터가 존재하지않음 , 삭제대상 userid : '" + userid + "'");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E003.getCode(),ResponseErrorCode.E003.getDesc()));
        }
        Account account = optionalAccount.get();
        //사용중인지체크
        if( loginlogService.countByLoginAccount(account) > 0){
            log.info("사용자삭제실패 : LoginLog에서 사용중인데이터 , 삭제대상 userid : '" + userid + "'");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E002.getCode(),ResponseErrorCode.E002.getDesc()));
        }

        accountService.delete(account);
        return ResponseEntity.ok(res.success());
    }

    @PostMapping("account")
    public ResponseEntity<Map<String,Object>> account(@RequestParam (value="userid", defaultValue="") String userid){

        log.info("단일사용자조회  / userid: '" + userid + "'");
        Optional<Account> optionalAccount = accountService.findByUserid(userid);

        if(!optionalAccount.isPresent()){
            log.info("단일사용자조회실패 : 조회할 데이터가 존재하지않음 , 조회대상 userid : '" + userid +"'");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E004.getCode(),ResponseErrorCode.E004.getDesc()));
        }
        Account account = optionalAccount.get();

        data.clear();
        data.put("datarow",account);
        res.addResponse("data",data);
        log.info("단일사용자 조회 성공 : " + account.toString() );

        return ResponseEntity.ok(res.success());

    }

    @PostMapping("lineUpSet")
    public ResponseEntity<Map<String,Object>> lineUpSet(){

        List<AccountLineUpDto> accountlineUpDtoList = accountService.findByLineUpList();
        log.info("accountlineUpDtoList : "+accountlineUpDtoList);

        int listSize = accountlineUpDtoList.size();

        for(int i=0; i<listSize; i++) {
            log.info("username : " + accountlineUpDtoList.get(i).getUsername());
            log.info("getDisasterType : " + accountlineUpDtoList.get(i).getDisasterType());
            log.info("getCollapseType : " + accountlineUpDtoList.get(i).getCollapseType());
            log.info("getTeamname : " + accountlineUpDtoList.get(i).getTeamname());
            log.info("getPositionname : " + accountlineUpDtoList.get(i).getPositionname());
            System.out.println();
        }

        List<String> disaterTypeList = new ArrayList<>();
        List<String> collapseTypeList = new ArrayList<>();
        List<Map<String,String>> disaterCollList = new ArrayList<>();

        List<String> teamTypeList = new ArrayList<>();
        List<String> positionnameList = new ArrayList<>();
        List<String> usernameList = new ArrayList<>();

        List<String> chairpersonList = new ArrayList<>();
        List<String> stewardList = new ArrayList<>();

        Map<String,String> team;
        Map<Map<String,String>,String> team2;
        List<Map<Map<String,String>,String>> team3 = new ArrayList<>();
        for(int i=0; i<listSize; i++){
            if(accountlineUpDtoList.get(i).getDisasterType().equals("간사")) {
                stewardList.add(accountlineUpDtoList.get(i).getPositionname()+" "+accountlineUpDtoList.get(i).getUsername());
            }else if(accountlineUpDtoList.get(i).getDisasterType().equals("위원장")){
                chairpersonList.add(accountlineUpDtoList.get(i).getPositionname()+" "+accountlineUpDtoList.get(i).getUsername());
            }else{
                disaterTypeList.add(accountlineUpDtoList.get(i).getDisasterType());
                collapseTypeList.add(accountlineUpDtoList.get(i).getCollapseType());

                team = new HashMap<>();
                team2 = new HashMap<>();
                team.put(accountlineUpDtoList.get(i).getDisasterType(),accountlineUpDtoList.get(i).getCollapseType());
                disaterCollList.add(team);
                team2.put(team,accountlineUpDtoList.get(i).getTeamname());
                team3.add(team2);

                teamTypeList.add(accountlineUpDtoList.get(i).getTeamname());
            }
        }
        log.info("chairpersonList : "+chairpersonList);
        log.info("stewardList : "+stewardList);

//        log.info("disaterTypeList : "+disaterTypeList);
//        log.info("collapseTypeList : "+collapseTypeList);
//        log.info("teamTypeList : "+teamTypeList);
//        log.info("disaterMapTypeList : "+disaterMapTypeList);

        Map<String, Integer> disaterMap = new LinkedHashMap<>();
        Map<String, Integer> collapseMap = new LinkedHashMap<>();
        Map<Map<String,String>, Integer> discollMap = new LinkedHashMap<>();
        Map<Map<Map<String,String>,String>, Integer> teamMap = new LinkedHashMap<>();

        disaterTypeList.forEach(e -> {
            Integer count = disaterMap.get(e);
            disaterMap.put(e, count == null ? 1 : count + 1);
        });
        collapseTypeList.forEach(e -> {
            if(!e.equals("해당없음")) {
                Integer count = collapseMap.get(e);
                collapseMap.put(e, count == null ? 1 : count + 1);
            }
        });
        team3.forEach(e -> {
            Integer count = teamMap.get(e);
            teamMap.put(e, count == null ? 1 : count + 1);
        });
        team3.forEach(e -> {
            Integer count = teamMap.get(e);
            teamMap.put(e, count == null ? 1 : count + 1);
        });
        disaterCollList.forEach(e -> {
            Integer count = discollMap.get(e);
            discollMap.put(e, count == null ? 1 : count + 1);
        });

//        Iterator<String> disaterKeys = disaterMap.keySet().iterator();
//        Iterator<Integer> disaterValues = disaterMap.values().iterator();
//        Iterator<String> collapseKeys = collapseMap.keySet().iterator();
//        Iterator<Integer> collapseValues = collapseMap.values().iterator();
//        for(int a=0; a<listSize; a++){
//            String disaterKey = disaterKeys.next();
//            if(disaterKey.equals("붕괴")){
//                Integer disaterValue = disaterValues.next();
//                String collapseKey = collapseKeys.next();
//                for(int b=0; b<disaterValue; b++){
//                    DisasterType a = DisasterType.valueOf(disaterCode).getDesc();
//
//                    String disaterCode = DisasterType.valueOf(disaterKey).getDesc();
//                    String collapseCode = CollapseType.valueOf(collapseKey).getDesc();
//
////                    log.info("disaterCode : "+disaterCode);
////                    log.info("collapseCode : "+collapseCode);
////                    List<AccountPositionUserDto> accountPositionUserDto = accountService.findByPositionUser(disaterKey,collapseKey);
////                    log.info("accountPositionUserDto");
//                }
//            }
//        }

        log.info("discollMap : "+discollMap);
//        Iterator<Map<Map<String, String>, String>> teamKeys = teamMap.keySet().iterator();
        int count=0;
//        for(int a=0; a<listSize; a++){
//            String disaterKey = disaterKeys.next();
//            Integer disaterValue = disaterValues.next();
////            Map<Map<String, String>, String> teamKey = teamKeys.next();
//            log.info("disaterKey : "+disaterKey);
////            log.info("teamKey : "+teamKey);
//            if(disaterKey.equals("붕괴")){
//                count++;
//            }else{
//
//            }


//            for(int b=0; b<accountlineUpDtoList.size(); b++){
//                if(accountlineUpDtoList.get(b).getDisasterType().contains(disaterKey) && accountlineUpDtoList.get(b).getCollapseType().contains(key)){
//                    if(!accountlineUpDtoList.get(b).getDisasterType().equals("간사") || !accountlineUpDtoList.get(b).getDisasterType().equals("위원장")) {
//                        positionnameList.add(accountlineUpDtoList.get(b).getPositionname());
//                        usernameList.add(accountlineUpDtoList.get(b).getUsername());
//                    }
//                }
//            }
//        }



        log.info("disaterMap : "+disaterMap);
        log.info("collapseMap : "+collapseMap);

        log.info("positionnameList : "+positionnameList);
        log.info("usernameList : "+usernameList);
        log.info("teamMap : "+teamMap);

        data.clear();
        data.put("chairpersonList",chairpersonList);
        data.put("stewardList",stewardList);
        data.put("positionuserSize",positionnameList.size());
        data.put("disaterMap",disaterMap);
        data.put("collapseMap",collapseMap);
        data.put("positionnameList",positionnameList);
        data.put("usernameList",usernameList);
        data.put("teamTypeList",teamTypeList);
        data.put("teamMap",teamMap);

        res.addResponse("data",data);
        return ResponseEntity.ok(res.success());
    }

    /*
    @PostMapping ("team")
    public ResponseEntity team(@RequestParam (value="teamcode", defaultValue="") String teamcode
                            ){
        log.info("단일부서  / teamcode: " + teamcode );
        Optional<Team> optionalTeam = teamService.findByTeamcode(teamcode);

        if (!optionalTeam.isPresent()){
            log.info("단일부서조회실패 : 조회할 데이터가 존재하지않음 , 조회대상 teamcode : " + teamcode);
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E004.getCode(),ResponseErrorCode.E004.getDesc()));
        }
        Team team = optionalTeam.get();

        data.clear();
        data.put("datarow",team);
        res.addResponse("data",data);

        log.info("단일부서 조회 성공 : " + team.toString() );
        return ResponseEntity.ok(res.success());

    }
     */


}
