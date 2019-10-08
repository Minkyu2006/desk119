package kr.co.broadwave.desk.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

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
    private final JavaMailSender javaMailSender;

    @Autowired
    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    // 이메일 정규식함수
    public static boolean isEmail(String email) {
        if (email==null) return false;
        boolean emailPattern = Pattern.matches("[\\w\\~\\-\\.]+@[\\w\\~\\-]+(\\.[\\w\\~\\-]+)+", email.trim());
        return emailPattern;
    }

    public void mailsend(List<String> maillists, String title, String content,String recordtitle,String recordstart,String recordend){
        String mail = maillists.stream()
                .map(n -> String.valueOf(n))
                .collect(Collectors.joining(";"));
        String[] addressSplit = mail.split(";");
        for(int i=0; i<addressSplit.length; i++) {
            if (isEmail(addressSplit[i])) {
                MailService es = new MailService(javaMailSender);
                es.sendSimpleMessage(addressSplit[i], title, content,recordtitle,recordstart,recordend);
            }
        }
    }

    public void sendSimpleMessage(String to, String subject, String text, String recordtitle, String recordstart, String recordend) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);//보낼 대상
        message.setSubject(subject);//제목
        message.setText(text+recordtitle+recordstart+recordend);//작성자,출동일지제목,시작날짜,끝난날짜

        try{//예외처리
            javaMailSender.send(message);
        }catch(MailException es){
            es.printStackTrace();
        }
    }

}
