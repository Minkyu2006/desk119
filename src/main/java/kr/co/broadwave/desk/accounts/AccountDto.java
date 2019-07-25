package kr.co.broadwave.desk.accounts;

import kr.co.broadwave.desk.bscodes.ApprovalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author InSeok
 * Date : 2019-07-25
 * Remark :
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private String userid;
    private String username;
    private String email;
    private String cellphone;
    private AccountRole role;
    private ApprovalType approvalType;
    private LocalDateTime insertDateTime;

    public String getUserid() {
        return userid;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getCellphone() {
        return cellphone;
    }

    public String getRole() {
        return role.getDesc();
    }

    public String getApprovalType() {
        return approvalType.getCode();
    }

    public String getInsertDateTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return insertDateTime.format(dateTimeFormatter);
    }
}
