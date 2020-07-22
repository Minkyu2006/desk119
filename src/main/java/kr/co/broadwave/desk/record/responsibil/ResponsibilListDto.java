package kr.co.broadwave.desk.record.responsibil;

import kr.co.broadwave.desk.record.Record;
import kr.co.broadwave.desk.teams.Team;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author Minkyu
 * Date : 2020-07-16
 * Remark :
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
public class ResponsibilListDto {

    private Long recordId;

    private String arEmployeeNumber;

    private String arEmployeeName;

    private String team;

    public Long getRecordId() {
        return recordId;
    }

    public String getArEmployeeNumber() {
        return arEmployeeNumber;
    }

    public String getArEmployeeName() {
        return arEmployeeName;
    }

    public String getTeam() {
        return team;
    }

}