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
    S0005("S0005", "강동구" ,LocationCityType.L1001),
    S0006("S0006", "강북구" ,LocationCityType.L1001),
    S0007("S0007", "관악구" ,LocationCityType.L1001),
    S0008("S0008", "광진구" ,LocationCityType.L1001),
    S0009("S0009", "마포구" ,LocationCityType.L1001),
    S0010("S0010", "금천구" ,LocationCityType.L1001),
    S0011("S0011", "구로구" ,LocationCityType.L1001),
    S0012("S0012", "노원구" ,LocationCityType.L1001),
    S0013("S0013", "도봉구" ,LocationCityType.L1001),
    S0014("S0014", "동대문구" ,LocationCityType.L1001),
    S0015("S0015", "서대문구" ,LocationCityType.L1001),
    S0016("S0016", "동작구" ,LocationCityType.L1001),
    S0017("S0017", "마포구" ,LocationCityType.L1001),
    S0018("S0018", "서초구" ,LocationCityType.L1001),
    S0019("S0019", "성동구" ,LocationCityType.L1001),
    S0020("S0020", "성북구" ,LocationCityType.L1001),
    S0021("S0021", "송파구" ,LocationCityType.L1001),
    S0022("S0022", "양천구" ,LocationCityType.L1001),
    S0023("S0023", "은평구" ,LocationCityType.L1001),
    S0024("S0024", "종로구" ,LocationCityType.L1001),
    S0025("S0025", "중구" ,LocationCityType.L1001),
    S0026("S0026", "용산구" ,LocationCityType.L1001),
    S0027("S0027", "중랑구" ,LocationCityType.L1001),

    G0001("G0001","수원시",LocationCityType.L2001),
    G0002("G0002","성남시",LocationCityType.L2001),
    G0003("G0003","고양시",LocationCityType.L2001),
    G0004("G0004","용인시",LocationCityType.L2001),
    G0005("G0005","부천시",LocationCityType.L2001),
    G0006("G0006","안산시",LocationCityType.L2001),
    G0007("G0007","안양시",LocationCityType.L2001),
    G0008("G0008","남양주시",LocationCityType.L2001),
    G0009("G0009","화성시",LocationCityType.L2001),
    G0010("G0010","평택시",LocationCityType.L2001),
    G0011("G0011","의정부시",LocationCityType.L2001),
    G0012("G0012","시흥시",LocationCityType.L2001),
    G0013("G0013","파주시",LocationCityType.L2001),
    G0014("G0014","광명시",LocationCityType.L2001),
    G0015("G0015","김포시",LocationCityType.L2001),
    G0016("G0016","군포시",LocationCityType.L2001),
    G0017("G0017","광주시",LocationCityType.L2001),
    G0018("G0018","이천시",LocationCityType.L2001),
    G0019("G0019","양주시",LocationCityType.L2001),
    G0020("G0020","오산시",LocationCityType.L2001),
    G0021("G0021","구리시",LocationCityType.L2001),
    G0022("G0022","안성시",LocationCityType.L2001),
    G0023("G0023","포천시",LocationCityType.L2001),
    G0024("G0024","의왕시",LocationCityType.L2001),
    G0025("G0025","하남시",LocationCityType.L2001),
    G0026("G0026","여주시",LocationCityType.L2001),
    G0027("G0027","여주군",LocationCityType.L2001),
    G0028("G0028","양평군",LocationCityType.L2001),
    G0029("G0029","동두천시",LocationCityType.L2001),
    G0030("G0030","과천시",LocationCityType.L2001),
    G0031("G0031","가평군",LocationCityType.L2001),
    G0032("G0032","연천군",LocationCityType.L2001),

    I0001("I0001","중구",LocationCityType.L3001),
    I0002("I0002","동구",LocationCityType.L3001),
    I0003("I0003","남구",LocationCityType.L3001),
    I0004("I0004","서구",LocationCityType.L3001),
    I0005("I0005","미추홀구",LocationCityType.L3001),
    I0006("I0006","연수구",LocationCityType.L3001),
    I0007("I0007","남동구",LocationCityType.L3001),
    I0008("I0008","부평구",LocationCityType.L3001),
    I0009("I0009","계양구",LocationCityType.L3001),
    I0010("I0010","강화군",LocationCityType.L3001),
    I0011("I0011","옹진군",LocationCityType.L3001),

    GN0001("GN0001","창원시",LocationCityType.L4001),
    GN0002("GN0002","마산시",LocationCityType.L4001),
    GN0003("GN0003","진주시",LocationCityType.L4001),
    GN0004("GN0004","진해시",LocationCityType.L4001),
    GN0005("GN0005","통영시",LocationCityType.L4001),
    GN0006("GN0006","사천시",LocationCityType.L4001),
    GN0007("GN0007","김해시",LocationCityType.L4001),
    GN0008("GN0008","밀양시",LocationCityType.L4001),
    GN0009("GN0009","거제시",LocationCityType.L4001),
    GN0010("GN0010","양산시",LocationCityType.L4001),
    GN0011("GN0011","의령군",LocationCityType.L4001),
    GN0012("GN0012","함안군",LocationCityType.L4001),
    GN0013("GN0013","창녕군",LocationCityType.L4001),
    GN0014("GN0014","고성군",LocationCityType.L4001),
    GN0015("GN0015","남해군",LocationCityType.L4001),
    GN0016("GN0016","하동군",LocationCityType.L4001),
    GN0017("GN0017","산청군",LocationCityType.L4001),
    GN0018("GN0018","함양군",LocationCityType.L4001),
    GN0019("GN0019","거창군",LocationCityType.L4001),
    GN0020("GN0020","합천군",LocationCityType.L4001),

    GB0001("GB0001","포항시",LocationCityType.L5001),
    GB0002("GB0002","경주시",LocationCityType.L5001),
    GB0003("GB0003","김천시",LocationCityType.L5001),
    GB0004("GB0004","안동시",LocationCityType.L5001),
    GB0005("GB0005","구미시",LocationCityType.L5001),
    GB0006("GB0006","영주시",LocationCityType.L5001),
    GB0007("GB0007","영천시",LocationCityType.L5001),
    GB0008("GB0008","상주시",LocationCityType.L5001),
    GB0009("GB0009","문경시",LocationCityType.L5001),
    GB0010("GB0010","경산시",LocationCityType.L5001),
    GB0011("GB0011","군위군",LocationCityType.L5001),
    GB0012("GB0012","의성군",LocationCityType.L5001),
    GB0013("GB0013","청송군",LocationCityType.L5001),
    GB0014("GB0014","영양군",LocationCityType.L5001),
    GB0015("GB0015","영덕군",LocationCityType.L5001),
    GB0016("GB0016","청도군",LocationCityType.L5001),
    GB0017("GB0017","고령군",LocationCityType.L5001),
    GB0018("GB0018","성주군",LocationCityType.L5001),
    GB0019("GB0019","칠곡군",LocationCityType.L5001),
    GB0020("GB0020","예천군",LocationCityType.L5001),
    GB0021("GB0021","봉화군",LocationCityType.L5001),
    GB0022("GB0022","울진군",LocationCityType.L5001),
    GB0023("GB0023","울릉군",LocationCityType.L5001);





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
