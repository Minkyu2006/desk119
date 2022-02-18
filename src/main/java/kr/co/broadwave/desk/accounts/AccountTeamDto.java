package kr.co.broadwave.desk.accounts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * @author Minkyu
 * Date : 2020-08-03
 * Time :
 * Remark : Team Dto
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountTeamDto {

    private String teamname;

    public String getTeamname() {
        return teamname;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }

}
