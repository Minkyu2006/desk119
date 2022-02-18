package kr.co.broadwave.desk.bscodes;

/**
 * @author InSeok
 * Date : 2019-07-22
 * Remark : 결재타입.
 */
public enum ApprovalType {
    AT01("AT01", "미처리"),
    AT02("AT02", "승인"),
    AT03("AT03", "거절"),
    AT04("AT04", "삭제");

    private final String code;
    private final String desc;

    ApprovalType(String code, String desc) {
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
