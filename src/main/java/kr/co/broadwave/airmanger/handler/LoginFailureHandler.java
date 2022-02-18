package kr.co.broadwave.airmanger.handler;

import kr.co.broadwave.airmanger.LoginLog.Loginlog;
import kr.co.broadwave.airmanger.LoginLog.LoginlogService;
import kr.co.broadwave.airmanger.common.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Minkyu
 * Date : 2022-02-18
 * Time :
 * Remark :
 */
@Slf4j
public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Autowired
    LoginlogService loginlogService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse res, AuthenticationException e) throws IOException, ServletException {

        String login_ip = CommonUtils.getIp(req);

        log.info("로그인 실패 : '" + req.getParameter("userName") +"' IP:'" + login_ip + "'");

        Loginlog loginlog = Loginlog.builder()
                .userId(req.getParameter("userName"))
                .successYN("N")
                .loginIp(login_ip)
                .build();
        loginlogService.save(loginlog);

        req.getRequestDispatcher("/login?error=true").forward(req, res);


    }


}
