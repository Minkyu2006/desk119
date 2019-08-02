package kr.co.broadwave.desk.mastercode;

import kr.co.broadwave.desk.bscodes.CodeType;
import kr.co.broadwave.desk.common.AjaxResponse;
import kr.co.broadwave.desk.common.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author InSeok
 * Date : 2019-08-02
 * Remark :
 */
@Slf4j
@RestController
@RequestMapping("/api/mastercode")
public class MasterCodeRestcontroller {
    private AjaxResponse res = new AjaxResponse();
    HashMap<String, Object> data = new HashMap<>();

    private final ModelMapper modelMapper;
    private final MasterCodeService masterCodeService;

    @Autowired
    public MasterCodeRestcontroller(ModelMapper modelMapper, MasterCodeService masterCodeService) {
        this.modelMapper = modelMapper;
        this.masterCodeService = masterCodeService;
    }

    //마스터코드 조회
    @PostMapping("list")
    public ResponseEntity noticeList(@RequestParam(value="codetype", defaultValue="") String codetype,
                                     @RequestParam(value="code", defaultValue="") String code,
                                     @RequestParam(value="name", defaultValue="") String name,
                                     Pageable pageable){

        CodeType codeType = CodeType.valueOf(codetype);

        log.info("마스터코드  조회 / 조회조건 : codetype / '" + codetype + "' code / '" + code + "' name / '" + name + "'");

        Page<MasterCodeDto> masterCodes = masterCodeService.findAllBySearchStrings(codeType, code,name, pageable);
        return CommonUtils.ResponseEntityPage(masterCodes);

    }
}
