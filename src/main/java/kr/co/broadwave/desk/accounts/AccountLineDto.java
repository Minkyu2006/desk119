package kr.co.broadwave.desk.accounts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * @author Minkyu
 * Date : 2020-08-20
 * Time :
 * Remark : 라인업리스트 Dto
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountLineDto {

    private String teamname;
    private String positionname;
    private String username;

    public String getTeamname() {
        return teamname;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }

    public String getPositionname() {
        return positionname;
    }

    public void setPositionname(String positionname) {
        this.positionname = positionname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
