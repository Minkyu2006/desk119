package kr.co.broadwave.desk.bscodes;

/**
 * @author MinKyu
 * Date : 2020-06-18
 * Remark : 분과 Enum
 */
public enum DisasterType {

    DS01("DS01", "붕괴"),
    DS02("DS02", "화재/폭발"),
    DS03("DS03", "지진"),
    DS04("DS04", "싱크홀"),
    DS05("DS05", "교통사고"),
    DS06("DS06", "홍수/가뭄"),
    DS07("DS07", "환경오염");

    private final String code;
    private final String desc;


    DisasterType(String code, String desc) {
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

