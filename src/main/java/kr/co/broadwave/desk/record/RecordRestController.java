package kr.co.broadwave.desk.record;

import kr.co.broadwave.desk.accounts.Account;
import kr.co.broadwave.desk.accounts.AccountService;
import kr.co.broadwave.desk.bscodes.CommonCode;
import kr.co.broadwave.desk.bscodes.LocationAddressType;
import kr.co.broadwave.desk.common.AjaxResponse;
import kr.co.broadwave.desk.common.CommonUtils;
import kr.co.broadwave.desk.common.ResponseErrorCode;
import kr.co.broadwave.desk.mastercode.MasterCode;
import kr.co.broadwave.desk.mastercode.MasterCodeService;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author MinKyu
 * Date : 2019-09-05
 * Remark : 출동일지 Rest컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/api/record")
public class RecordRestController {
    private AjaxResponse res = new AjaxResponse();
    HashMap<String, Object> data = new HashMap<>();

    private final RecordService recordService;
    private final AccountService accountService;
    private final MasterCodeService masterCodeService;
    private final ModelMapper modelMapper;

    @Autowired
    public RecordRestController(RecordService recordService,
                                AccountService accountService,
                                ModelMapper modelMapper,
                                MasterCodeService masterCodeService) {
        this.recordService = recordService;
        this.accountService = accountService;
        this.modelMapper = modelMapper;
        this.masterCodeService = masterCodeService;
    }

    @PostMapping("reg")
    public ResponseEntity recordSave(@ModelAttribute RecordDto recordDto,
                                     MultipartHttpServletRequest multi,
                                     ModelMap model,
                                     HttpServletRequest request) throws Exception {

        Record record = modelMapper.map(recordDto,Record.class);
        Optional<MasterCode> optionalRelatedId = masterCodeService.findById(recordDto.getArRelatedId());

        String currentuserid = CommonUtils.getCurrentuser(request);
        Optional<Account> optionalAccount = accountService.findByUserid(currentuserid);

        if (!optionalAccount.isPresent()) {
            log.info("출동일지 저장한 사람 아이디 미존재 : '" + currentuserid + "'");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E014.getCode(),
                    ResponseErrorCode.E014.getDesc() + "'" + currentuserid + "'" ));
        }

        //직급코드가 존재하지않으면
        if (!optionalRelatedId.isPresent()) {
            log.info(" 선택한 직급 DB 존재 여부 체크.  직급코드: '" + recordDto.getArRelatedId() +"'");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E016.getCode(),
                    ResponseErrorCode.E016.getDesc()));
        }else{
            record.setArRelatedId(optionalRelatedId.get());
        }

        record.setInsert_id(currentuserid);
        record.setInsert_name(optionalAccount.get().getUsername());
        record.setInsertDateTime(LocalDateTime.now());
        record.setModify_id(currentuserid);
        record.setModify_name(optionalAccount.get().getUsername());
        record.setModifyDateTime(LocalDateTime.now());

        Record recordSave = this.recordService.save(record);

        log.info("출동일지 저장 성공 : " + recordSave.toString() );
        return ResponseEntity.ok(res.success());
    }

    @PostMapping("location")
    public ResponseEntity Location(
            @RequestParam(value="locationCityType", defaultValue="") String locationCityType){

        List<CommonCode> commonCodes = new ArrayList<>();

        Arrays.stream(LocationAddressType.values())
                .filter(v -> v.getLocationCityType().equals(locationCityType))
                .forEach(v->{
                    CommonCode commonCode = new CommonCode(v.getCode(),v.getDesc());
                    commonCodes.add(commonCode);
                });
        data.clear();
        data.put("dataselect",commonCodes);

        res.addResponse("data",data);
        return ResponseEntity.ok(res.success());
    }

}
