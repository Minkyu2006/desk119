package kr.co.broadwave.desk.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author InSeok
 * Date : 2019-03-25
 * Time : 10:36
 * Remark : 시스템 초기설정 및 기타 Configuration
 */
@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean(name = "uploadPath")
    public String uploadPath() {
        return "d:/tmp/image/";
    }

}
