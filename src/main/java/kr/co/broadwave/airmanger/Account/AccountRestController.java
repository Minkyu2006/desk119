package kr.co.broadwave.airmanger.Account;

import kr.co.broadwave.airmanger.Account.Service.AccountService;
import kr.co.broadwave.airmanger.LoginLog.LoginlogService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author Minkyu
    2022-02-18
 * Time :
 * Remark : Account 용 Rest Controller
 */
@Slf4j
@RestController
@RequestMapping("/api/account")
public class AccountRestController {

//    AjaxResponse res = new AjaxResponse();
//    HashMap<String, Object> data = new HashMap<>();

    private final AccountService accountService;
    private final ModelMapper modelMapper;
    private final LoginlogService loginlogService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountRestController(AccountService accountService, ModelMapper modelMapper, LoginlogService loginlogService, PasswordEncoder passwordEncoder) {
        this.accountService = accountService;
        this.modelMapper = modelMapper;
        this.loginlogService = loginlogService;
        this.passwordEncoder = passwordEncoder;
    }



}
