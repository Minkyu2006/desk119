package kr.co.broadwave.desk.record;

import kr.co.broadwave.desk.keygenerate.KeyGenerateService;
import kr.co.broadwave.desk.record.responsibil.Responsibil;
import kr.co.broadwave.desk.record.responsibil.ResponsibilListDto;
import kr.co.broadwave.desk.record.responsibil.ResponsibilRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author MinKyu
 * Date : 2019-09-06
 * Remark : 출동일지 레코드서비스
 */
@Slf4j
@Service
public class RecordService {

    private final RecordRepository recordRepository;
    private final ModelMapper modelMapper;
    private final RecordRepositoryCustom recordRepositoryCustom;
    private final KeyGenerateService keyGenerateService;
    private final ResponsibilRepository responsibilRepository;

    @Autowired
    public RecordService(RecordRepository recordRepository,
                         RecordRepositoryCustom recordRepositoryCustom,
                         KeyGenerateService keyGenerateService,
                         ResponsibilRepository responsibilRepository,
                         ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.recordRepository = recordRepository;
        this.recordRepositoryCustom = recordRepositoryCustom;
        this.keyGenerateService = keyGenerateService;
        this.responsibilRepository = responsibilRepository;
    }

    // 출동일지 고유번호 저장 및 입력데이터저장
    public Record save(Record record){
        if ( record.getArNumber() == null || record.getArNumber().isEmpty()){
            log.info("신규 RC코드생성");
            Date now = new Date();
            SimpleDateFormat yyMM = new SimpleDateFormat("yyMM");
            String arNumber = keyGenerateService.keyGenerate("ar_record", yyMM.format(now), record.getModify_id());
            record.setArNumber(arNumber);
        }
        return recordRepository.save(record);
    }

    public List<Record> findAll() {
        return this.recordRepository.findAll();
    }

    public List<Responsibil> responsibilfindAll() {
        return this.responsibilRepository.findAll();
    }

    //재해재난붕괴 서비스
   public List<String> arDisaster(List<String> arDisasterTypes){
       int cnt1 = 0; int cnt2 = 0; int cnt3 = 0; int cnt4 = 0; int cnt5 = 0; int cnt6 = 0; int cnt7 = 0; int cntAll;
            for(int i=0; i<arDisasterTypes.size(); i++){
                String a = arDisasterTypes.get(i).substring(0,1);
                if(a.equals("1")){
                    cnt1++;
                }
                String b = arDisasterTypes.get(i).substring(2,3);
                if(b.equals("1")){
                    cnt2++;
                }
                String c = arDisasterTypes.get(i).substring(4,5);
                if(c.equals("1")){
                    cnt3++;
                }
                String d = arDisasterTypes.get(i).substring(6,7);
                if(d.equals("1")){
                    cnt4++;
                }
                String e = arDisasterTypes.get(i).substring(8,9);
                if(e.equals("1")){
                    cnt5++;
                }
                String f = arDisasterTypes.get(i).substring(10,11);
                if(f.equals("1")){
                    cnt6++;
                }
                String g = arDisasterTypes.get(i).substring(12,13);
                if(g.equals("1")){
                    cnt7++;
                }
            }

            cntAll = cnt1+cnt2+cnt3+cnt4+cnt5+cnt6+cnt7;

           arDisasterTypes.clear();
           arDisasterTypes. add(Integer.toString(cnt1));
           arDisasterTypes. add(Integer.toString(cnt2));
           arDisasterTypes. add(Integer.toString(cnt3));
           arDisasterTypes. add(Integer.toString(cnt4));
           arDisasterTypes. add(Integer.toString(cnt5));
           arDisasterTypes. add(Integer.toString(cnt6));
           arDisasterTypes. add(Integer.toString(cnt7));
           arDisasterTypes. add(Integer.toString(cntAll));
           return arDisasterTypes;
       }


        //조사시설물 서비스
    public List<String> arFac(List<String> arFacTypes){
        int cnt1 = 0; int cnt2 = 0; int cnt3 = 0; int cnt4 = 0; int cnt5 = 0; int cnt6 = 0; int cnt7 = 0;
        int cnt8 = 0; int cnt9 = 0; int cnt10 = 0; int cntAll;
        for(int i=0; i<arFacTypes.size(); i++){
            String a = arFacTypes.get(i).substring(0,1);
            if(a.equals("1")){
                cnt1++;
            }
            String b = arFacTypes.get(i).substring(2,3);
            if(b.equals("1")){
                cnt2++;
            }
            String c = arFacTypes.get(i).substring(4,5);
            if(c.equals("1")){
                cnt3++;
            }
            String d = arFacTypes.get(i).substring(6,7);
            if(d.equals("1")){
                cnt4++;
            }
            String e = arFacTypes.get(i).substring(8,9);
            if(e.equals("1")){
                cnt5++;
            }
            String f = arFacTypes.get(i).substring(10,11);
            if(f.equals("1")){
                cnt6++;
            }
            String g = arFacTypes.get(i).substring(12,13);
            if(g.equals("1")){
                cnt7++;
            }
            String h = arFacTypes.get(i).substring(14,15);
            if(h.equals("1")){
                cnt8++;
            }
            String k = arFacTypes.get(i).substring(16,17);
            if(k.equals("1")){
                cnt9++;
            }
            String l = arFacTypes.get(i).substring(18,19);
            if(l.equals("1")){
                cnt10++;
            }
        }
        cntAll = cnt1+cnt2+cnt3+cnt4+cnt5+cnt6+cnt7+cnt8+cnt9+cnt10;

        cnt2 += cnt3;
        cnt5 += cnt6;
        cnt8 += cnt9;

        arFacTypes.clear();
        arFacTypes. add(Integer.toString(cnt1));
        arFacTypes. add(Integer.toString(cnt2));
        arFacTypes. add(Integer.toString(cnt4));
        arFacTypes. add(Integer.toString(cnt7));
        arFacTypes. add(Integer.toString(cnt5));
        arFacTypes. add(Integer.toString(cnt8));
        arFacTypes. add(Integer.toString(cnt10));
        arFacTypes. add(Integer.toString(cntAll));
        return arFacTypes;
    }


    public void recordResponSave(List<Responsibil> responsibil) {
        for (Responsibil responsibils : responsibil) {
            responsibilRepository.save(responsibils);
        }
    }

    public Optional<Record> findByIdRecord(Long recordid){
        return  recordRepository.findById(recordid);
    }

    public RecordMapperDto findById(Long id){
        Optional<Record> optionalRecord = recordRepository.findById(id);
        if (optionalRecord.isPresent()) {
            return modelMapper.map(optionalRecord.get(), RecordMapperDto.class);
        } else {
            return null;
        }
    }

    public RecordViewDto findByIdView(Long id){
        Optional<Record> optionalRecord = recordRepository.findById(id);
        if (optionalRecord.isPresent()) {
            return modelMapper.map(optionalRecord.get(), RecordViewDto.class);
        } else {
            return null;
        }
    }

//    public List<RecordViewDto> findByIdView2(Long[] id){
//        Optional<Record> optionalRecord = recordRepository.findById(id);
//        if (optionalRecord.isPresent()) {
//            return modelMapper.map(optionalRecord.get(), RecordViewDto.class);
//        } else {
//            return null;
//        }
//    }

    public Page<RecrodListDto> findAllBySearchStrings(String arNumber, String arTitle, String arWriter, Pageable pageable){
        return recordRepositoryCustom.findAllBySearchStrings(arNumber,arTitle,arWriter,pageable);

    }

    public void delete(Record record){
        recordRepository.delete(record);
    }

    //조사담당자 viewlist
    @Transactional
    public List<Responsibil> recordRespon(Long record_id){
        Optional<Record> optionalRecord = recordRepository.findById(record_id);
        if (optionalRecord.isPresent()){
            return responsibilRepository.findByRecord(optionalRecord.get());
        }else{
            return null;
        }
    }

    public int recordresponsibilDelete(Long rsid) {
        Optional<Responsibil> optionalResponsibil = responsibilRepository.findById(rsid);
        if (optionalResponsibil.isPresent()){
            responsibilRepository.delete(optionalResponsibil.get());
            return 1;
        }else{
            return -1;
        }
    }

    public List<RecordViewPrintDto> findByIdViewList(List<Long> ids) {
        return recordRepositoryCustom.findByIdViewList(ids);
    }

    public List<ResponsibilListDto> recordResponList(List<Long> recordList) {
        return recordRepositoryCustom.recordResponList(recordList);
    }

    public Page<RecrodStatisticDto> findByStatisticList(String typeName,Pageable pageable,String num) {
        return recordRepositoryCustom.findByStatisticList(typeName,pageable,num);
    }

    public List<RecrodStatisticDto> findByStatisticList2(String typeName, String num) {
        return recordRepositoryCustom.findByStatisticList2(typeName,num);
    }

}

