package kr.co.broadwave.desk.bscodes;

/**
 * @author InSeok
 * Date : 2019-07-31
 * Remark :
 */
public enum CodeType {
    C0001("C0001", "관련부처"),
    C0002("C0002", "행정구역")
    ;

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
