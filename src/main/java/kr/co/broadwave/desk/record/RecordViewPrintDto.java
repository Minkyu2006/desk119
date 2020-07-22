package kr.co.broadwave.desk.record;

import kr.co.broadwave.desk.bscodes.LocationAddressType;
import kr.co.broadwave.desk.bscodes.LocationCityType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Minkyu
 * Date : 2020-07-16
 * Remark :
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
public class RecordViewPrintDto {

    private Long id;
    private String arNumber; // 출동일지번호
    private String arTitle; // 출동일지제목
    private String arWriter; //작성자
    private String arRelatedId; //관련부처
    private String arRelatedDetail; //관련부처상세정보
    private String arPapers; //관련문서
    private String arIntoStart; //조사일자 시작
    private String arIntoEnd; //조사일자 끝

    private LocationCityType arLocationCityType; //조사위치 시
    private LocationAddressType arLocationAddressType; //조사위치 구
    private String arLocationDetail; //조사위치 상세

    private String arPurpose; //조사목적
    private String arEngine; //공동참여기관
    private String arOutline; //현장개요
    private String arResult; //조사결과
    private String arOpinion; //검토의견

    private String arDisasterItem; //추가 재해재난분과 항목
    private String arFacItem; //추가 조사시설물 항목

    private String argita; //조사시설물:기타
    private String arDisasterGita; //재해재난분과:기타

    public String getArDisasterGita() {
        if(arDisasterGita.equals(" ")){
            return arDisasterGita;
        }else{
            return ": "+arDisasterGita;
        }
    }

    public String getArgita() {
        if(!argita.equals("")){
            return ": "+argita;
        }else{
            return argita;
        }
    }

    public String getArNumber() {
        return arNumber;
    }

    public String getArTitle() {
        return arTitle;
    }

    public Long getId() {
        return id;
    }

    public String getArWriter() {
        return arWriter;
    }

    public String getArRelatedId() {
        return arRelatedId;
    }

    public String getArRelatedDetail() {
        return arRelatedDetail;
    }

    public String getArPapers() {
        return arPapers;
    }

    public String getArIntoStart() {
        return arIntoStart;
    }

    public String getArIntoEnd() {
        return arIntoEnd;
    }

    public String getArLocationCityType() {
        return arLocationCityType.getDesc();
    }

    public String getArLocationAddressType() {
        return arLocationAddressType.getDesc();
    }

    public String getArLocationDetail() {
        return arLocationDetail;
    }

    public String getArPurpose() {
        return arPurpose;
    }

    public String getArEngine() {
        return arEngine;
    }

    public String getArOutline() {
        return arOutline;
    }

    public String getArResult() {
        return arResult;
    }

    public String getArOpinion() {
        return arOpinion;
    }

    public String getArDisasterItem() {
        return arDisasterItem;
    }

    public String getArFacItem() {
        return arFacItem;
    }
}