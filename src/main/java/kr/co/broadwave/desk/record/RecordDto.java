package kr.co.broadwave.desk.record;

import kr.co.broadwave.desk.bscodes.LocationAddressType;
import kr.co.broadwave.desk.bscodes.LocationCityType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Minkyu
 * Date : 2019-09-10
 * Time : 10:11
 * Remark : RecordDto
 */

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RecordDto {

    private Long id;
    private String arNumber; //출동일지 번호
    private String arTitle; //출동일지 제목
    private String arWriter; //작성자
    private String arDisasterType; //재해.재난.분과
    private Long arRelatedId; //관련부처
    private String arRelatedDetail; //관련부처상세정보
    private String arPapers; //관련문서
    private String arIntoStart; //조사일자 시작
    private String arIntoEnd; //조사일자 끝
    private LocationCityType arLocationCityType; //조사위치 시
    private LocationAddressType arLocationAddressType; //조사위치 구
    private String arLocationDetail; //조사위치 상세
    private String arFac; //조사시설물
    private String argita; //조사시설물:기타
    private String arPurpose; //조사목적
    private String arEngine; //공동참여기관
    private String arOutline; //현장개요
    private String arResult; //조사결과
    private String arOpinion; //검토의견

    private String arDisasterItem; //추가 재해재난분과 항목
    private String arFacItem; //추가 조사시설물 항목

    //기본적인것
    private String modify_name;
    private LocalDateTime modifyDateTime;
    private String modify_id;
    private LocalDateTime insertDateTime;
    private String insert_id;
    private String insert_name;

    public String getModify_id() {
        return modify_id;
    }

    public void setModify_id(String modify_id) {
        this.modify_id = modify_id;
    }

    public String getInsertDateTime() {
        if (this.insertDateTime == null){
            return null;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return insertDateTime.format(dateTimeFormatter);
    }

    public String getArDisasterItem() {
        return arDisasterItem;
    }

    public void setArDisasterItem(String arDisasterItem) {
        this.arDisasterItem = arDisasterItem;
    }

    public void setInsertDateTime(LocalDateTime insertDateTime) {
        this.insertDateTime = insertDateTime;
    }

    public String getInsert_id() {
        return insert_id;
    }

    public void setInsert_id(String insert_id) {
        this.insert_id = insert_id;
    }

    public String getInsert_name() {
        return insert_name;
    }

    public void setInsert_name(String insert_name) {
        this.insert_name = insert_name;
    }

    public String getModify_name() {
        return modify_name;
    }

    public void setModify_name(String modify_name) {
        this.modify_name = modify_name;
    }

    public String getModifyDateTime() {
        if (this.modifyDateTime == null){
            return null;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return modifyDateTime.format(dateTimeFormatter);
    }

    public void setModifyDateTime(LocalDateTime modifyDateTime) {
        this.modifyDateTime = modifyDateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getArDisasterType() {
        return arDisasterType;
    }

    public void setArDisasterType(String arDisasterType) {
        this.arDisasterType = arDisasterType;
    }

    public Long getArRelatedId() { return arRelatedId; }

    public void setArRelatedId(Long arRelatedId) {
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

    public LocationCityType getArLocationCityType() {
        return arLocationCityType;
    }

    public String getArLocationAddressType() {
        return arLocationAddressType.getCode();
    }

    public void setArLocationCityType(LocationCityType arLocationCityType) {
        this.arLocationCityType = arLocationCityType;
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

    public String getArFac() {
        return arFac;
    }

    public void setArFac(String arFac) {
        this.arFac = arFac;
    }

    public String getArgita() {
        return argita;
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

    public String getArNumber() {
        return arNumber;
    }

    public void setArNumber(String arNumber) {
        this.arNumber = arNumber;
    }

    public String getArFacItem() {
        return arFacItem;
    }

    public void setArFacItem(String arFacItem) {
        this.arFacItem = arFacItem;
    }

}
