package kr.co.broadwave.airmanger.LoginLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author Minkyu
 * Date : 2022-02-18
 * Time :
 * Remark : 로그인 로그기록
 */@Service
public class LoginlogService {

    LoginlogRepository loginlogRepository;

    @Autowired
    public LoginlogService( LoginlogRepository loginlogRepository){
        this.loginlogRepository = loginlogRepository;
    }

    public void save(Loginlog loginlog){
        loginlog.setInsert_date(LocalDateTime.now());
        loginlogRepository.save(loginlog);
    }

}
