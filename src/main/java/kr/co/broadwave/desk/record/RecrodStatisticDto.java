package kr.co.broadwave.desk.record;

import kr.co.broadwave.desk.bscodes.LocationAddressType;
import kr.co.broadwave.desk.bscodes.LocationCityType;
import kr.co.broadwave.desk.mastercode.MasterCode;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Minkyu
 * Date : 2020-08-03
 * Remark :
 */
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RecrodStatisticDto {

    private Long id;
    private String arWriter;
    private String arDisasterType; //재해.재난.분과 항목(숫자)
    private String arFac;//조사시설물(숫자)

    private String arDisasterItem; //재해.재난.분과 항목
    private String arFacItem;//조사시설물

    private LocationCityType arLocationCityType;//조사위치 시

    private MasterCode arRelatedId; //관련부처

    private String arIntoStart; //조사일자 시작
    private String arIntoEnd; //조사일자 끝난

    private String modify_id; // 작성자 아이디

    private int arRecordState;

    public String getArWriter() {
        return arWriter;
    }

    public int getArRecordState() {
        return arRecordState;
    }

    public String getModify_id() {
        return modify_id;
    }

    public Long getId() {
        return id;
    }

    public String getArRelatedId() {
        return arRelatedId.getName();
    }

    public String getArFacItem() {
        return arFacItem;
    }

    public String getArDisasterItem() {
        return arDisasterItem;
    }

    public String getArIntoStart() {
        return arIntoStart;
    }

    public String getArLocationCityType() {
        return arLocationCityType.getDesc();
    }

    public String getArDisasterType() {
        return arDisasterType;
    }

    public String getArFac() {
        return arFac;
    }

    public String getArIntoEnd() {
        return arIntoEnd;
    }
}
