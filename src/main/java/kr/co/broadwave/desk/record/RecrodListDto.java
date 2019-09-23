package kr.co.broadwave.desk.record;

import kr.co.broadwave.desk.bscodes.LocationAddressType;
import kr.co.broadwave.desk.bscodes.LocationCityType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * @author Minkyu
 * Date : 2019-09-11
 * Remark :
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RecrodListDto {
    private Long id;
    private String arNumber; //출동일지 번호
    private String arTitle; //출동일지 제목
    private String arWriter; //작성자

    private String arFacItem;//조사시설물
    private String arDisasterItem; //재해.재난.분과 항목
    private String arIntoStart; //조사일자 시작
    private String arIntoEnd; //조사일자 끝
    private LocationCityType arLocationCityType;//조사위치 시
    private LocationAddressType arLocationAddressType;//조사위치 구

    private LocalDateTime modifyDateTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArNumber() {
        return arNumber;
    }

    public void setArNumber(String arNumber) {
        this.arNumber = arNumber;
    }

    public String getArTitle() {
        return arTitle;
    }

    public void setArTitle(String arTitle) {
        this.arTitle = arTitle;
    }

    public String getArWriter() {
        return arWriter;
    }

    public void setArWriter(String arWriter) {
        this.arWriter = arWriter;
    }

    public String getArFacItem() {
        return arFacItem;
    }

    public void setArFacItem(String arFacItem) {
        this.arFacItem = arFacItem;
    }

    public String getArDisasterItem() {
        return arDisasterItem;
    }

    public void setArDisasterItem(String arDisasterItem) {
        this.arDisasterItem = arDisasterItem;
    }

    public String getArIntoStart() {
        return arIntoStart;
    }

    public void setArIntoStart(String arIntoStart) {
        this.arIntoStart = arIntoStart;
    }

    public String getArIntoEnd() {
        return arIntoEnd;
    }

    public void setArIntoEnd(String arIntoEnd) {
        this.arIntoEnd = arIntoEnd;
    }

    public String getArLocationCityType() {
        return arLocationCityType.getDesc();
    }

    public String getArLocationAddressType() {
        return arLocationAddressType.getDesc();
    }

    public void setArLocationCityType(LocationCityType arLocationCityType) {
        this.arLocationCityType = arLocationCityType;
    }

    public void setArLocationAddressType(LocationAddressType arLocationAddressType) {
        this.arLocationAddressType = arLocationAddressType;
    }

    public String getModifyDateTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return modifyDateTime.format(dateTimeFormatter);
    }

    public void setModifyDateTime(LocalDateTime modifyDateTime) {
        this.modifyDateTime = modifyDateTime;
    }

}
