package kr.co.broadwave.desk.bscodes;

/**
 * @author MinKyu
 * Date : 2020-06-18
 * Remark : 분과종류 Enum
 */
public enum CollapseType {

    CS01("CS01", "건축물"),
    CS02("CS02", "교량"),
    CS03("CS03", "지반"),
    CS04("CS04", "사면"),
    CS05("CS05", "터널"),
    CS06("CS06", "도로"),
    CS07("CS07", "해당없음");

    private final String code;
    private final String desc;

    CollapseType(String code, String desc) {
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
