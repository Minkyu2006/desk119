package kr.co.broadwave.desk.notice;

import kr.co.broadwave.desk.common.UploadFileUtils;
import kr.co.broadwave.desk.notice.file.UploadFile;
import kr.co.broadwave.desk.notice.file.UploadFileRepository;
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
 * @author InSeok
 * Date : 2019-07-26
 * Remark : 공지사항등록에서 HTML에디터내에 이미지 파일 첨부시 이미지파일을 업로드하기위한 서비스
 */
@Slf4j
@Service
public class ImageService {


    private static final Logger logger = LoggerFactory.getLogger(ImageService.class);

    private final Path rootLocation;
    private final NoticeRepository noticeRepository;

    @Autowired
    public ImageService(String uploadNoticePath, NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
        logger.info("PATH :: " + uploadNoticePath);
        this.rootLocation = Paths.get(uploadNoticePath);
    }

    @Autowired
    UploadFileRepository uploadFileRepository;

    public List<UploadFile> loadAll() {
        return uploadFileRepository.findAll();
    }

    public UploadFile load(Long fileId) {

        return uploadFileRepository.findById(fileId).get();
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

    public UploadFile store(MultipartFile file,Notice notice) throws Exception {
        try {
            if (file.isEmpty()) {
                throw new Exception("Failed to store empty file " + file.getOriginalFilename());
            }

            String saveFileName = UploadFileUtils.fileSave(rootLocation.toString(), file);

            if (saveFileName.toCharArray()[0] == '/') {
                saveFileName = saveFileName.substring(1);
            }

            Resource resource = loadAsResource(saveFileName);

            UploadFile saveFile = new UploadFile();
            saveFile.setNotice(notice);
            saveFile.setSaveFileName(saveFileName);
            saveFile.setFileName(file.getOriginalFilename());
            saveFile.setContentType(file.getContentType());
            saveFile.setFilePath(rootLocation.toString().replace(File.separatorChar, '/') + File.separator + saveFileName);
            saveFile.setSize(resource.contentLength());
            saveFile.setInsertDateTime(LocalDateTime.now());
            saveFile = uploadFileRepository.save(saveFile);

            return saveFile;
        } catch (IOException e) {
            throw new Exception("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    //특정 게시물의 첨부파일목록을 반환하는 함수
    public List<UploadFile> uploadFileList(Long notice_id){

        log.info("공지사항 첨부파일 내역 조회 / 공지사항 ID '" + notice_id + "'");

        Optional<Notice> optionalNotice = noticeRepository.findById(notice_id);
        if (optionalNotice.isPresent()){
            return uploadFileRepository.findByNotice(optionalNotice.get());
        }else{
            return null;
        }

    }
    //특정파일삭제하기(-1반환이면 오류)
    public int uploadFileDelete(Long fileId){
        log.info("공지사항 첨부파일 삭제 시작 / 파일ID '" + fileId + "'");
        Optional<UploadFile> optionalUploadFile = uploadFileRepository.findById(fileId);
        if (optionalUploadFile.isPresent()){

            //실제 파일삭제
            String filePath = optionalUploadFile.get().getFilePath();
            File file = new File(filePath);
            file.delete();
            //DB에서 삭제
            uploadFileRepository.delete(optionalUploadFile.get());

            return 1;
        }else{
            return -1;
        }

    }
}
