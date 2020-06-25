package kr.co.broadwave.desk.accounts;

import kr.co.broadwave.desk.bscodes.ApprovalType;
import lombok.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author InSeok
 * Date : 2019-03-25
 * Time : 10:11
 * Remark : Account Dto
 */
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountMapperDto {

    private String userid;
    private String username;
    private String password;
    private String email;
    private String cellphone;
    private AccountRole role;
    private ApprovalType approvalType;
    private String teamcode;
    private String disasterType;
    private String collapseType;
    private String mode;
    private Long positionid;

    public String getUserid() {
        return userid;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    //이메일인지 확인하고 값보내기
    public String getEmail() {
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if(m.matches()) {
            return email;
        }else{
            return email+"@kict.re.kr";
        }
    }

    public String getCellphone() {
        return cellphone;
    }

    public AccountRole getRole() {
        return role;
    }

    public ApprovalType getApprovalType() {
        return approvalType;
    }

    public String getTeamcode() {
        return teamcode;
    }

    public String getDisasterType() {
        return disasterType;
    }

    public String getCollapseType() {
        return collapseType;
    }

    public String getMode() {
        return mode;
    }

    public Long getPositionid() {
        return positionid;
    }
}
