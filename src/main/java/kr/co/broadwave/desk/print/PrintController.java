package kr.co.broadwave.desk.print;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Minkyu
 * Date : 2019-10-07
 * Remark :
 */

@Controller
@RequestMapping("/print")
public class PrintController {

    //통계화면
    @RequestMapping("/view")
    public String printview(Model model){

        return "print/view";
    }

}
