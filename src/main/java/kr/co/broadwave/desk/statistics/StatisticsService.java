package kr.co.broadwave.desk.statistics;

import kr.co.broadwave.desk.bscodes.CodeType;
import kr.co.broadwave.desk.bscodes.LocationCityType;
import kr.co.broadwave.desk.mastercode.MasterCodeDto;
import kr.co.broadwave.desk.mastercode.MasterCodeService;
import kr.co.broadwave.desk.record.Record;
import kr.co.broadwave.desk.record.RecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Minkyu
 * Date : 2019-09-30
 * Remark :
 */
@Slf4j
@Service
public class StatisticsService {

    // 2019년도 지역통계
    public List<String> localyear(List<String> nowYears) {
        List<String> nowYearCitys = new ArrayList<>();

        nowYearCitys.add(Long.toString(nowYears.stream().filter(x -> x.contains("서울특별시")).count()));
        nowYearCitys.add(Long.toString(nowYears.stream().filter(x -> x.contains("인천광역시")).count()));
        nowYearCitys.add(Long.toString(nowYears.stream().filter(x -> x.contains("대구광역시")).count()));
        nowYearCitys.add(Long.toString(nowYears.stream().filter(x -> x.contains("부산광역시")).count()));
        nowYearCitys.add(Long.toString(nowYears.stream().filter(x -> x.contains("대전광역시")).count()));
        nowYearCitys.add(Long.toString(nowYears.stream().filter(x -> x.contains("울산광역시")).count()));
        nowYearCitys.add(Long.toString(nowYears.stream().filter(x -> x.contains("광주광역시")).count()));
        nowYearCitys.add(Long.toString(nowYears.stream().filter(x -> x.contains("경기도")).count()));
        nowYearCitys.add(Long.toString(nowYears.stream().filter(x -> x.contains("강원도")).count()));
        nowYearCitys.add(Long.toString(nowYears.stream().filter(x -> x.contains("충청북도")).count()));
        nowYearCitys.add(Long.toString(nowYears.stream().filter(x -> x.contains("충청남도")).count()));
        nowYearCitys.add(Long.toString(nowYears.stream().filter(x -> x.contains("전라북도")).count()));
        nowYearCitys.add(Long.toString(nowYears.stream().filter(x -> x.contains("전라남도")).count()));
        nowYearCitys.add(Long.toString(nowYears.stream().filter(x -> x.contains("경상북도")).count()));
        nowYearCitys.add(Long.toString(nowYears.stream().filter(x -> x.contains("경상남도")).count()));
        nowYearCitys.add(Long.toString(nowYears.stream().filter(x -> x.contains("제주특별자치도")).count()));
        nowYearCitys.add(Long.toString(nowYears.stream().filter(x -> x.contains("세종특별자치시")).count()));

//        System.out.println("현재 시티들 : "+nowYears);
//        System.out.println("현재 시티들 건수 : "+nowYearCitys);

        return nowYearCitys;
    }

    // 2018년도 지역통계
    public List<String> localyear2(List<String> productionYears) {
        List<String> productionYearCitys = new ArrayList<>();

        productionYearCitys.add(Long.toString(productionYears.stream().filter(x -> x.contains("서울특별시")).count()));
        productionYearCitys.add(Long.toString(productionYears.stream().filter(x -> x.contains("인천광역시")).count()));
        productionYearCitys.add(Long.toString(productionYears.stream().filter(x -> x.contains("대구광역시")).count()));
        productionYearCitys.add(Long.toString(productionYears.stream().filter(x -> x.contains("부산광역시")).count()));
        productionYearCitys.add(Long.toString(productionYears.stream().filter(x -> x.contains("대전광역시")).count()));
        productionYearCitys.add(Long.toString(productionYears.stream().filter(x -> x.contains("울산광역시")).count()));
        productionYearCitys.add(Long.toString(productionYears.stream().filter(x -> x.contains("광주광역시")).count()));
        productionYearCitys.add(Long.toString(productionYears.stream().filter(x -> x.contains("경기도")).count()));
        productionYearCitys.add(Long.toString(productionYears.stream().filter(x -> x.contains("강원도")).count()));
        productionYearCitys.add(Long.toString(productionYears.stream().filter(x -> x.contains("충청북도")).count()));
        productionYearCitys.add(Long.toString(productionYears.stream().filter(x -> x.contains("충청남도")).count()));
        productionYearCitys.add(Long.toString(productionYears.stream().filter(x -> x.contains("전라북도")).count()));
        productionYearCitys.add(Long.toString(productionYears.stream().filter(x -> x.contains("전라남도")).count()));
        productionYearCitys.add(Long.toString(productionYears.stream().filter(x -> x.contains("경상북도")).count()));
        productionYearCitys.add(Long.toString(productionYears.stream().filter(x -> x.contains("경상남도")).count()));
        productionYearCitys.add(Long.toString(productionYears.stream().filter(x -> x.contains("제주특별자치도")).count()));
        productionYearCitys.add(Long.toString(productionYears.stream().filter(x -> x.contains("세종특별자치시")).count()));

//        System.out.println("작년 시티들 건수 : "+productionYears);
//        System.out.println("작년 시티들 건수 : "+productionYearCitys);

        return productionYearCitys;
    }

}
