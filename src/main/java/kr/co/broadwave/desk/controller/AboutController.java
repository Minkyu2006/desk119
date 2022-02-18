package kr.co.broadwave.desk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author InSeok
 * Date : 2019-07-31
 * Remark :
 */
@Controller
@RequestMapping("about")
public class AboutController {

    @RequestMapping("lineup")
    public String lineup(){
        return "about/lineup";
    }



}
