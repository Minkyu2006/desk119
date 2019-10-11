package kr.co.broadwave.desk.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Minkyu
 * Date : 2019-09-24
 * Remark :
 */
@Service
public class MailService {
      private final MailSender mailSender;

    @Autowired
    public MailService(MailSender mailSender) {
           this.mailSender = mailSender;
    }

    // 이메일 정규식함수
//    public static boolean isEmail(String email) {
//        if (email==null) return false;
//        boolean emailPattern = Pattern.matches("[\\w\\~\\-\\.]+@[\\w\\~\\-]+(\\.[\\w\\~\\-]+)+", email.trim());
//        return emailPattern;
//    }

    public void mailsend(List<String> maillists, String title, String writer, String recordtitle, String recordstart, String recordend, String viewId){
        MailService mailService = new MailService(mailSender);
        mailService.sendSimpleMessage(maillists,title,writer,recordtitle,recordstart,recordend,viewId);
    }

    public void sendSimpleMessage(List<String> maillists, String title, String writer, String recordtitle, String recordstart, String recordend,String viewId) {
        String mailArray[] = maillists.toArray(new String[maillists.size()]);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailArray);//보낼 대상
        message.setSubject(title);//제목
        message.setText(writer+recordtitle+recordstart+recordend+viewId);//작성자,출동일지제목,시작날짜,끝난날짜

        try{//예외처리
            mailSender.send(message);
        }catch(MailException es){
            es.printStackTrace();
        }
    }

}
