package kr.co.broadwave.desk.bscodes;

/**
 * @author MinKyu
 * Date : 2020-06-18
 * Remark : 분과종류 Enum
 */
public enum CollapseType {

    CS01("CS01", "건축물",DisasterType.DS01),
    CS02("CS02", "교량",DisasterType.DS01),
    CS03("CS03", "지반",DisasterType.DS01),
    CS04("CS04", "사면",DisasterType.DS01),
    CS05("CS05", "터널",DisasterType.DS01),
    CS06("CS06", "도로",DisasterType.DS01);

    private final String code;
    private final String desc;
    private final DisasterType disasterType;

    CollapseType(String code, String desc,DisasterType disasterType) {
        this.code = code;
        this.desc = desc;
        this.disasterType = disasterType;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public DisasterType getDisasterType() {
        return disasterType;
    }

}
