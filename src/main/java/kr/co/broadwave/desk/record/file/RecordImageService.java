package kr.co.broadwave.desk.record.file;

import kr.co.broadwave.desk.common.UploadFileUtils;
import kr.co.broadwave.desk.record.Record;
import kr.co.broadwave.desk.record.RecordRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author Minkyu
 * Date : 2019-09-16
 * Remark :
 */
@Slf4j
@Service
public class RecordImageService {
    private static final Logger logger = LoggerFactory.getLogger(RecordImageService.class);
    private final Path rootLocation;
    private final RecordRepository recordRepository;
    private final RecordUploadFileRepository recordUploadFileRepository;


    @Autowired
    public RecordImageService(String uploadRecordPath, RecordRepository recordRepository, RecordUploadFileRepository recordUploadFileRepository) {
        this.recordRepository = recordRepository;
        this.recordUploadFileRepository = recordUploadFileRepository;

        logger.info("PATH :: " + uploadRecordPath);
        this.rootLocation = Paths.get(uploadRecordPath);
    }


    public List<RecordUploadFile> loadAll() {
        return recordUploadFileRepository.findAll();
    }
    public RecordUploadFile load(Long fileId) {
        return recordUploadFileRepository.findById(fileId).get();
    }
    public Resource loadAsResource(String fileName) throws Exception {
        try {
            if (fileName.toCharArray()[0] == '/') {
                fileName = fileName.substring(1);
            }
            Path file = loadPath(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new Exception("Could not read file: " + fileName);
            }
        } catch (Exception e) {
            throw new Exception("Could not read file: " + fileName);
        }
    }

    private Path loadPath(String fileName) {
        return rootLocation.resolve(fileName);
    }

    public RecordUploadFile store(MultipartFile file, Record record) throws Exception {
        try {
            if (file.isEmpty()) {
                throw new Exception("Failed to store empty file: " + file.getOriginalFilename());
            }
            String saveFileName = UploadFileUtils.fileSave(rootLocation.toString(), file);
            if (saveFileName.toCharArray()[0] == '/') {
                saveFileName = saveFileName.substring(1);
            }
                Resource resource = loadAsResource(saveFileName);
                RecordUploadFile saveFile = new RecordUploadFile();
                saveFile.setRecord(record);
                saveFile.setAfSaveFileName(saveFileName);
                saveFile.setAfFileName(file.getOriginalFilename());
                saveFile.setContentType(file.getContentType());
                saveFile.setAfFilePath(rootLocation.toString().replace(File.separatorChar, '/') + File.separator + saveFileName);
                saveFile.setSize(resource.contentLength());
                saveFile.setInsertDateTime(LocalDateTime.now());
                saveFile = recordUploadFileRepository.save(saveFile);
                return saveFile;
        } catch (IOException e) {
            throw new Exception("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    //특정 게시물의 첨부파일목록을 반환하는 함수
    public List<RecordUploadFile> recorduploadFileList(Long record_id){
        log.info("출동일지 첨부파일 내역 조회 / 출동일지 ID '" + record_id + "'");
        Optional<Record> optionalRecord = recordRepository.findById(record_id);
        if (optionalRecord.isPresent()){
            return recordUploadFileRepository.findByRecord(optionalRecord.get());
        }else{
            return null;
        }
    }

    //특정파일삭제하기(-1반환이면 오류)
    public int recorduploadFileDelete(Long fileId){
        log.info("출동일지 첨부파일 삭제 시작 / 파일ID '" + fileId + "'");
        Optional<RecordUploadFile> optionalUploadFile = recordUploadFileRepository.findById(fileId);
        if (optionalUploadFile.isPresent()){
            //실제 파일삭제
            String filePath = optionalUploadFile.get().getAfFilePath();
            File file = new File(filePath);
            file.delete();
            //DB에서 삭제
            recordUploadFileRepository.delete(optionalUploadFile.get());
            return 1;
        }else{
            return -1;
        }
    }

    //파일순번 채번하기
    public void makefileseq(Record recordSave) {
        List<RecordUploadFile> recordUploadFiles = recordUploadFileRepository.findByRecord(recordSave);

        int i = 0;
        for (RecordUploadFile recordUploadFile : recordUploadFiles) {
            i = i + 1;
            String arDisasterItem = recordSave.getArDisasterItem(); //재해재난분과
            String arLocationCityType = recordSave.getArLocationCityType().getDesc(); //시
            String arLocationAddressType = recordSave.getArLocationAddressType().getDesc(); //구
            String arIntoStart = recordSave.getArIntoStart(); // 시작일
            String arWriter = recordSave.getArWriter(); // 작성자

            String afName = recordUploadFile.getAfFileName(); //파일이름

            String filename = arDisasterItem + "_" + arLocationCityType + "_" + arLocationAddressType + "_" + arIntoStart + "_" + arWriter + "_" + "순번 " + i + "_" + afName;
            recordUploadFile.setAfFileName(filename);

            recordUploadFileRepository.save(recordUploadFile);
        }

    }

}
