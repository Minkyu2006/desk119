package kr.co.broadwave.desk.notice;

import kr.co.broadwave.desk.common.UploadFileUtils;
import kr.co.broadwave.desk.notice.file.UploadFile;
import kr.co.broadwave.desk.notice.file.UploadFileRepository;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author InSeok
 * Date : 2019-07-26
 * Remark : 공지사항등록에서 HTML에디터내에 이미지 파일 첨부시 이미지파일을 업로드하기위한 서비스
 */
@Service
public class ImageService {
    private static final Logger logger = LoggerFactory.getLogger(ImageService.class);

    private final Path rootLocation;

    @Autowired
    public ImageService(String uploadPath) {
        logger.info("PATH :: " + uploadPath);
        this.rootLocation = Paths.get(uploadPath);
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

    public UploadFile store(MultipartFile file) throws Exception {
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
}
