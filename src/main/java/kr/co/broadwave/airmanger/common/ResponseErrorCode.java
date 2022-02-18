package kr.co.broadwave.airmanger.common;

/**
 * @author Minkyu
 * Date : 2022-02-18
 * Time :
 * Remark : Rest controller 응답코드
 */
public enum ResponseErrorCode {
    AM001("AM001", "테스트중입니다"),
    ;

    private String code;
    private String desc;

    ResponseErrorCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
