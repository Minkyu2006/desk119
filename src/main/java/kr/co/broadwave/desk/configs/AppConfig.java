package kr.co.broadwave.desk.configs;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
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

    //공지사항관련 업로드 파일 경로
    @Value("${noticefile.upload.directory}")
    String uploadNoticeFileDir;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean(name = "uploadNoticePath")
    public String uploadNoticePath() {
        return uploadNoticeFileDir;
    }

}
