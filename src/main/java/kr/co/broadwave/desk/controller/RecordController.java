package kr.co.broadwave.desk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author InSeok
 * Date : 2019-07-22
 * Remark : 출동일지 컨트롤러
 */
@Controller
@RequestMapping("/record")
public class RecordController {
    @RequestMapping("/reg")
    public String regist(){
        return "record/recordreg";
    }
}
