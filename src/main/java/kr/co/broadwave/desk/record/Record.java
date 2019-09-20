package kr.co.broadwave.desk.record;

import kr.co.broadwave.desk.bscodes.LocationAddressType;
import kr.co.broadwave.desk.bscodes.LocationCityType;
import kr.co.broadwave.desk.mastercode.MasterCode;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Minkyu
 * Date : 2019-09-06
 * Remark : Record 테이블
 */
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="ar_record")
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ar_id")
    private Long id;

    @Column(unique = true,name="ar_number")
    private String arNumber;

    //출동일지 제목
    @Column(name="ar_title")
    private String arTitle;

    //작성자
    @Column(name="ar_writer")
    private String arWriter;

    //재해.재난.분과
    @Column(name="ar_disaster_type")
    private String arDisasterType;

    //관련부처
    @ManyToOne(targetEntity = MasterCode.class,fetch = FetchType.LAZY)
    @JoinColumn(name="ar_related_id")
    private MasterCode arRelatedId;

    //관련부처상세정보
    @Column(name="ar_related_detail")
    private String arRelatedDetail;

    //관련문서
    @Column(name="ar_papers")
    private String arPapers;

    //조사일자 시작
    @Column(name="ar_into_start")
    private String arIntoStart;

    //조사일자 끝
    @Column(name="ar_into_end")
    private String arIntoEnd;

    //조사위치 시
    @Enumerated(EnumType.STRING)
    @Column(name="ar_location_city_type")
    private LocationCityType arLocationCityType;

    //조사위치 구
    @Enumerated(EnumType.STRING)
    @Column(name="ar_location_address_type")
    private LocationAddressType arLocationAddressType;

    //조사위치 상세
    @Column(name="ar_location_detail")
    private String arLocationDetail;

    //조사시설물
    @Column(name="ar_fac")
    private String arFac;

    //조사시설물 기타
    @Column(name="ar_gita")
    private String argita;

    //조사목적
    @Column(name="ar_purpose")
    private String arPurpose;

    //공동참여기관
    @Column(name="ar_engine")
    private String arEngine;

    //현장개요
    @Column(length = 100000, name="ar_outline")
    private String arOutline;

    //조사결과
    @Column(length = 100000, name="ar_result")
    private String arResult;

    //검토의견
    @Column(length = 100000, name="ar_opinion")
    private String arOpinion;

    //기본적으로 들어가는것들
    @Column(name="insert_date")
    private LocalDateTime insertDateTime;
    @Column(name="insert_id")
    private String insert_id;
    @Column(name="insert_name")
    private String insert_name;
    @Column(name="modify_date")
    private LocalDateTime modifyDateTime;
    @Column(name="modify_id")
    private String modify_id;
    @Column(name="modify_name")
    private String modify_name;


    // 추가 재해재난분과 항목들
    @Column(name="ar_disaster_item")
    private String arDisasterItem;
    // 추가 조사시설물 항목들
    @Column(name="ar_fac_item")
    private String arFacItem;
    // 추가 파일이름_띄어쓰기없음
    @Column(name="ar_disaster_item_filename")
    private String arDisasterItemFilename;

}
