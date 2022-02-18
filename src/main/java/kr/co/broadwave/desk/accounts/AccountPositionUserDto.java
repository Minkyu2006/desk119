package kr.co.broadwave.desk.accounts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * @author Minkyu
 * Date : 2020-07-02
 * Time :
 * Remark : 라인업리스트 PositionUserDto
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountPositionUserDto {
    private String positionname;
    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPositionname(String positionname) {
        this.positionname = positionname;
    }

    public String getPositionname() {
        return positionname;
    }

    public String getUsername() {
        return username;
    }
}
