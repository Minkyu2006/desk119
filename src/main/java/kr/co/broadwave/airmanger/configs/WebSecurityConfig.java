package kr.co.broadwave.airmanger.configs;

import kr.co.broadwave.airmanger.Account.Service.AccountService;
import kr.co.broadwave.airmanger.handler.LoginFailureHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * @author Minkyu
 * Date : 2022-02-18
 * Time :
 * Remark : Security 관련 config
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AccountService accountService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/record/list").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasAnyRole("USER")
                .antMatchers("/manager").hasAnyRole("MANGER")
                .antMatchers("/","/assets/**","/login","/logout","/favicon.ico").permitAll()
                .anyRequest().permitAll()

                .and()
                .formLogin()
                    .loginPage("/login")
                    .failureHandler(failureHandler())
                    .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .permitAll()
                ;
    }

    @Bean
    public AuthenticationFailureHandler failureHandler() {
        return new LoginFailureHandler();
    }

}
