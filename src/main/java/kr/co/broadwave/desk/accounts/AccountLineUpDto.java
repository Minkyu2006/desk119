package kr.co.broadwave.desk.accounts;

import kr.co.broadwave.desk.bscodes.CollapseType;
import kr.co.broadwave.desk.bscodes.DisasterType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * @author Minkyu
 * Date : 2020-06-26
 * Time :
 * Remark : 라인업리스트 Dto
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountLineUpDto {
    private String username;
    private DisasterType disaster;
    private CollapseType collapse;
    private DisasterType disasterType;
    private CollapseType collapseType;
    private String teamname;
    private String positionname;

    public void setDisaster(DisasterType disaster) {
        this.disaster = disaster;
    }

    public void setCollapse(CollapseType collapse) {
        this.collapse = collapse;
    }

    public String getCollapseType() {
        if(collapseType.getCode().equals("CS07")){
            return "해당없음";
        }else{
            return collapseType.getDesc();
        }
    }

    public void setCollapseType(CollapseType collapseType) {
        this.collapseType = collapseType;
    }

    public void setDisasterType(DisasterType disasterType) {
        this.disasterType = disasterType;
    }

    public String getDisasterType() {
        return disasterType.getDesc();
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

    public String getTeamname() {
        return teamname;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }

}
