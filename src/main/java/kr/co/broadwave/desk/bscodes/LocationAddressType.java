package kr.co.broadwave.desk.bscodes;

/**
 * @author MinKyu
 * Date : 2019-09-06
 * Remark : 출동일지 구타입
 */
public enum LocationAddressType {
    S0001("S0001", "영등포구",LocationCityType.L1001),
    S0002("S0002", "종로구" ,LocationCityType.L1001),
    S0003("S0003", "강서구" ,LocationCityType.L1001),
    S0004("S0004", "강남구" ,LocationCityType.L1001),
    S0005("S0005", "마포구" ,LocationCityType.L1001),
    S0006("S0006", "금천구" ,LocationCityType.L1001),

    I0001("I0001", "서구",LocationCityType.L2001),
    I0002("I0002", "남구",LocationCityType.L2001),
    I0003("I0003", "미추홀구",LocationCityType.L2001),
    I0004("I0004", "부평구",LocationCityType.L2001),
    I0005("I0005", "계양구",LocationCityType.L2001),

    D0001("D0001", "수성수",LocationCityType.L3001),
    D0002("D0002", "달서구",LocationCityType.L3001),
    D0003("D0003", "북구",LocationCityType.L3001),
    D0004("D0004", "동구",LocationCityType.L3001),
    D0005("D0005", "중구",LocationCityType.L3001);

    private String code;
    private String desc;
    private LocationCityType locationCityType;

    LocationAddressType(String code, String desc,LocationCityType locationCityType) {
        this.code = code;
        this.desc = desc;
        this.locationCityType = locationCityType;
    }
    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public String getLocationCityType(){
        return locationCityType.getCode();
    }
}
