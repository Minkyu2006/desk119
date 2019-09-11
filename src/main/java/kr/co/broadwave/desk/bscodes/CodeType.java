package kr.co.broadwave.desk.bscodes;

/**
 * @author InSeok
 * Date : 2019-07-31
 * Remark : 코드 대분류코드
 */
public enum CodeType {
    C0001("C0001", "정부·지자체"),
    C0002("C0002", "타 기관"),
    C0003("C0003", "KICT 자체");

    private String code;
    private String desc;

    CodeType(String code, String desc) {
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
