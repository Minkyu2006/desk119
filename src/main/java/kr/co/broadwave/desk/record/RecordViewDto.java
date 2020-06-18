package kr.co.broadwave.desk.record;

import kr.co.broadwave.desk.bscodes.LocationAddressType;
import kr.co.broadwave.desk.bscodes.LocationCityType;
import kr.co.broadwave.desk.mastercode.MasterCode;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Minkyu
 * Date : 2019-09-16
 * Remark :
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RecordViewDto {

    private Long id;
    private String arNumber; //출동일지 번호
    private String arTitle; //출동일지 제목
    private String arWriter; //작성자
    private MasterCode arRelatedId; //관련부처
    private String arRelatedDetail; //관련부처상세정보
    private String arPapers; //관련문서
    private String arIntoStart; //조사일자 시작
    private String arIntoEnd; //조사일자 끝
    private LocationCityType arLocationCityType; //조사위치 시
    private LocationAddressType arLocationAddressType; //조사위치 구
    private String arLocationDetail; //조사위치 상세
    private String argita; //조사시설물:기타
    private String arDisasterGita; //재해재난분과:기타
    private String arPurpose; //조사목적
    private String arEngine; //공동참여기관
    private String arOutline; //현장개요
    private String arResult; //조사결과
    private String arOpinion; //검토의견
    private String arDisasterItem; //추가 재해재난분과 항목
    private String arFacItem; //추가 조사시설물 항목

    public void setArDisasterGita(String arDisasterGita) {
        this.arDisasterGita = arDisasterGita;
    }

    public String getArDisasterGita() {
        if(arDisasterGita!=null){
            return ": "+arDisasterGita;
        }else{
            return arDisasterGita;
        }
    }

    private String insert_id; // 작성자 아이디

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


    public String getArRelatedId() {
        return arRelatedId.getName();
    }

    public void setArRelatedId(MasterCode arRelatedId) {
        this.arRelatedId = arRelatedId;
    }

    public String getArRelatedDetail() {
        return arRelatedDetail;
    }

    public void setArRelatedDetail(String arRelatedDetail) {
        this.arRelatedDetail = arRelatedDetail;
    }

    public String getArPapers() {
        return arPapers;
    }

    public void setArPapers(String arPapers) {
        this.arPapers = arPapers;
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

    public void setArLocationCityType(LocationCityType arLocationCityType) {
        this.arLocationCityType = arLocationCityType;
    }

    public String getArLocationAddressType() {
        return arLocationAddressType.getDesc();
    }

    public void setArLocationAddressType(LocationAddressType arLocationAddressType) {
        this.arLocationAddressType = arLocationAddressType;
    }

    public String getArLocationDetail() {
        return arLocationDetail;
    }

    public void setArLocationDetail(String arLocationDetail) {
        this.arLocationDetail = arLocationDetail;
    }

    public String getArgita() {
        if(argita!=null){
            return ": "+argita;
        }else{
            return argita;
        }
    }

    public void setArgita(String argita) {
        this.argita = argita;
    }

    public String getArPurpose() {
        return arPurpose;
    }

    public void setArPurpose(String arPurpose) {
        this.arPurpose = arPurpose;
    }

    public String getArEngine() {
        return arEngine;
    }

    public void setArEngine(String arEngine) {
        this.arEngine = arEngine;
    }

    public String getArOutline() {
        return arOutline;
    }

    public void setArOutline(String arOutline) {
        this.arOutline = arOutline;
    }

    public String getArResult() {
        return arResult;
    }

    public void setArResult(String arResult) {
        this.arResult = arResult;
    }

    public String getArOpinion() {
        return arOpinion;
    }

    public void setArOpinion(String arOpinion) {
        this.arOpinion = arOpinion;
    }

    public String getArDisasterItem() {
        return arDisasterItem;
    }

    public void setArDisasterItem(String arDisasterItem) {
        this.arDisasterItem = arDisasterItem;
    }

    public String getArFacItem() {
        return arFacItem;
    }

    public void setArFacItem(String arFacItem) {
        this.arFacItem = arFacItem;
    }

    public String getInsert_id() {
        return insert_id;
    }

    public void setInsert_id(String insert_id) {
        this.insert_id = insert_id;
    }
}