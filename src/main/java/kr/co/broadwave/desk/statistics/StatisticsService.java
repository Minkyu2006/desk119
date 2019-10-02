package kr.co.broadwave.desk.statistics;

import kr.co.broadwave.desk.bscodes.CodeType;
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
    private final MasterCodeService masterCodeService;
    private final RecordService recordService;
    @Autowired
    public StatisticsService(MasterCodeService masterCodeService,RecordService recordService) {
        this.masterCodeService = masterCodeService;
        this.recordService = recordService;
    }



    // 2019년도 지역통계
    public List<String> localyear(List<Record> records,List<String> city2019s, List<Integer> modefyDate, int year) {
        for(int i=0; i<records.size(); i++) {
            if (modefyDate.get(i).equals(year)) {
                city2019s.add(records.get(i).getArLocationCityType().getDesc());
            }
        }
        Stream<String> l01 = city2019s.stream().filter(x -> x.contains("서울특별시"));
        Stream<String> l02 = city2019s.stream().filter(x -> x.contains("인천광역시"));
        Stream<String> l03 = city2019s.stream().filter(x -> x.contains("대구광역시"));
        Stream<String> l04 = city2019s.stream().filter(x -> x.contains("부산광역시"));
        Stream<String> l05 = city2019s.stream().filter(x -> x.contains("대전광역시"));
        Stream<String> l06 = city2019s.stream().filter(x -> x.contains("울산광역시"));
        Stream<String> l07 = city2019s.stream().filter(x -> x.contains("광주광역시"));
        Stream<String> l08 = city2019s.stream().filter(x -> x.contains("경기도"));
        Stream<String> l09 = city2019s.stream().filter(x -> x.contains("강원도"));
        Stream<String> l10 = city2019s.stream().filter(x -> x.contains("충청북도"));
        Stream<String> l11 = city2019s.stream().filter(x -> x.contains("충청남도"));
        Stream<String> l12 = city2019s.stream().filter(x -> x.contains("전라북도"));
        Stream<String> l13 = city2019s.stream().filter(x -> x.contains("전라남도"));
        Stream<String> l14 = city2019s.stream().filter(x -> x.contains("경상북도"));
        Stream<String> l15 = city2019s.stream().filter(x -> x.contains("경상남도"));
        Stream<String> l16 = city2019s.stream().filter(x -> x.contains("제주특별자치도"));
        Stream<String> l17 = city2019s.stream().filter(x -> x.contains("세종특별자치시"));

        long countl01 = l01.count();
        long countl02 = l02.count();
        long countl03 = l03.count();
        long countl04 = l04.count();
        long countl05 = l05.count();
        long countl06 = l06.count();
        long countl07 = l07.count();
        long countl08 = l08.count();
        long countl09 = l09.count();
        long countl10 = l10.count();
        long countl11 = l11.count();
        long countl12 = l12.count();
        long countl13 = l13.count();
        long countl14 = l14.count();
        long countl15 = l15.count();
        long countl16 = l16.count();
        long countl17 = l17.count();

        city2019s.clear();
        city2019s.add(Long.toString(countl01));
        city2019s.add(Long.toString(countl02));
        city2019s.add(Long.toString(countl03));
        city2019s.add(Long.toString(countl04));
        city2019s.add(Long.toString(countl05));
        city2019s.add(Long.toString(countl06));
        city2019s.add(Long.toString(countl07));
        city2019s.add(Long.toString(countl08));
        city2019s.add(Long.toString(countl09));
        city2019s.add(Long.toString(countl10));
        city2019s.add(Long.toString(countl11));
        city2019s.add(Long.toString(countl12));
        city2019s.add(Long.toString(countl13));
        city2019s.add(Long.toString(countl14));
        city2019s.add(Long.toString(countl15));
        city2019s.add(Long.toString(countl16));
        city2019s.add(Long.toString(countl17));

        return city2019s;
    }

    // 2018년도 지역통계
    public List<String> localyear2(List<Record> records,List<String> city2018s, List<Integer> modefyDate, int year2) {
        for(int i=0; i<records.size(); i++) {
            if (modefyDate.get(i).equals(year2)) {
                city2018s.add(records.get(i).getArLocationCityType().getDesc());
            }
        }

        Stream<String> l01 = city2018s.stream().filter(x -> x.contains("서울특별시"));
        Stream<String> l02 = city2018s.stream().filter(x -> x.contains("인천광역시"));
        Stream<String> l03 = city2018s.stream().filter(x -> x.contains("대구광역시"));
        Stream<String> l04 = city2018s.stream().filter(x -> x.contains("부산광역시"));
        Stream<String> l05 = city2018s.stream().filter(x -> x.contains("대전광역시"));
        Stream<String> l06 = city2018s.stream().filter(x -> x.contains("울산광역시"));
        Stream<String> l07 = city2018s.stream().filter(x -> x.contains("광주광역시"));
        Stream<String> l08 = city2018s.stream().filter(x -> x.contains("경기도"));
        Stream<String> l09 = city2018s.stream().filter(x -> x.contains("강원도"));
        Stream<String> l10 = city2018s.stream().filter(x -> x.contains("충청북도"));
        Stream<String> l11 = city2018s.stream().filter(x -> x.contains("충청남도"));
        Stream<String> l12 = city2018s.stream().filter(x -> x.contains("전라북도"));
        Stream<String> l13 = city2018s.stream().filter(x -> x.contains("전라남도"));
        Stream<String> l14 = city2018s.stream().filter(x -> x.contains("경상북도"));
        Stream<String> l15 = city2018s.stream().filter(x -> x.contains("경상남도"));
        Stream<String> l16 = city2018s.stream().filter(x -> x.contains("제주특별자치도"));
        Stream<String> l17 = city2018s.stream().filter(x -> x.contains("세종특별자치시"));

        long countl01 = l01.count();
        long countl02 = l02.count();
        long countl03 = l03.count();
        long countl04 = l04.count();
        long countl05 = l05.count();
        long countl06 = l06.count();
        long countl07 = l07.count();
        long countl08 = l08.count();
        long countl09 = l09.count();
        long countl10 = l10.count();
        long countl11 = l11.count();
        long countl12 = l12.count();
        long countl13 = l13.count();
        long countl14 = l14.count();
        long countl15 = l15.count();
        long countl16 = l16.count();
        long countl17 = l17.count();

        city2018s.clear();
        city2018s.add(Long.toString(countl01));
        city2018s.add(Long.toString(countl02));
        city2018s.add(Long.toString(countl03));
        city2018s.add(Long.toString(countl04));
        city2018s.add(Long.toString(countl05));
        city2018s.add(Long.toString(countl06));
        city2018s.add(Long.toString(countl07));
        city2018s.add(Long.toString(countl08));
        city2018s.add(Long.toString(countl09));
        city2018s.add(Long.toString(countl10));
        city2018s.add(Long.toString(countl11));
        city2018s.add(Long.toString(countl12));
        city2018s.add(Long.toString(countl13));
        city2018s.add(Long.toString(countl14));
        city2018s.add(Long.toString(countl15));
        city2018s.add(Long.toString(countl16));
        city2018s.add(Long.toString(countl17));

        return city2018s;
    }

}
