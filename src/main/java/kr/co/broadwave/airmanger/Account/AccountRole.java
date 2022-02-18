package kr.co.broadwave.airmanger.Account;

/**
 * @author Minkyu
 * Date : 2022-02-18
 * Time :
 * Remark : 사용자 권한 구분
 */
public enum AccountRole {
    ROLE_USER("ROLE_USER", "고객사"),
    ROLE_MANAGER("ROLE_MANAGER", "AS업체"),
    ROLE_ADMIN("ROLE_ADMIN", "관리자")
    ;

    private String code;
    private String desc;

    AccountRole(String code,String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }}


