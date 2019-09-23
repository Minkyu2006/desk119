package kr.co.broadwave.desk.bscodes;

/**
 * @author MinKyu
 * Date : 2019-09-06
 * Remark : 출동일지 시타입
 */
public enum LocationCityType {

    L1001("L1001", "서울특별시"),

    L2001("L2001", "인천광역시"),

    L3001("L3001", "대구광역시");

//    L0004("L0004", "대전광역시"),
//    L0005("L0005", "부산광역시");

    private String code;
    private String desc;


    LocationCityType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }


}
