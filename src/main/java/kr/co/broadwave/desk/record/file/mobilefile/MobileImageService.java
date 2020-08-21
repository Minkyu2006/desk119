package kr.co.broadwave.desk.record.file.mobilefile;

import kr.co.broadwave.desk.accounts.Account;
import kr.co.broadwave.desk.bscodes.CodeType;
import kr.co.broadwave.desk.common.UploadFileUtils;
import kr.co.broadwave.desk.mail.MailService;
import kr.co.broadwave.desk.mastercode.MasterCodeDto;
import kr.co.broadwave.desk.mastercode.MasterCodeService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Minkyu
 * Date : 2020-08-05
 * Remark :
 */
@Slf4j
@Service
public class MobileImageService {
    private static final Logger logger = LoggerFactory.getLogger(MobileImageService.class);
    private final Path rootLocation;
    private final MobileUploadFileRepository mobileUploadFileRepository;
    private final  MobileUploadFileRepositoryCustom mobileUploadFileRepositoryCustom;
    private final MailService mailService;
    private final MasterCodeService masterCodeService;

    @Autowired
    public MobileImageService(String uploadPath,MobileUploadFileRepository mobileUploadFileRepository,
                              MobileUploadFileRepositoryCustom mobileUploadFileRepositoryCustom,
                              MailService mailService,
                              MasterCodeService masterCodeService) {
        this.mobileUploadFileRepository = mobileUploadFileRepository;
        this.mobileUploadFileRepositoryCustom = mobileUploadFileRepositoryCustom;
        this.mailService = mailService;
        this.masterCodeService = masterCodeService;
        logger.info("PATH :: " + uploadPath);
        this.rootLocation = Paths.get(uploadPath + "recordimages");
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

    // 모바일사진 파일업로드
    public MobileUploadFile mobileUploadFile(MultipartFile file, Account account,String comment) throws Exception {
        try {
            if (file.isEmpty()) {
                throw new Exception("Failed to store empty file: " + file.getOriginalFilename());
            }
            String saveFileName = UploadFileUtils.s_fileSave(rootLocation.toString(), file);
            if (saveFileName.toCharArray()[0] == '/') {
                saveFileName = saveFileName.substring(1);
            }
            Resource resource = loadAsResource(saveFileName);
            MobileUploadFile saveFile = new MobileUploadFile();
            saveFile.setAccount(account);
            saveFile.setAfmSaveFilename(saveFileName);
            saveFile.setAfmFilename(file.getOriginalFilename());
            saveFile.setAfmOriginalFilename(file.getOriginalFilename());
            saveFile.setAfmContentType(file.getContentType());
            saveFile.setAfmFilePath(rootLocation.toString().replace(File.separatorChar, '/') + File.separator + saveFileName);
            saveFile.setAfmFileThumPath(rootLocation.toString().replace(File.separatorChar, '/') + File.separator + saveFileName.substring(0,11)+"s_"+saveFileName.substring(11));
            saveFile.setAfmSize(resource.contentLength());
            saveFile.setInsertDateTime(LocalDateTime.now());
            if(comment.equals("")){
                comment = "설명없음";
                saveFile.setAfmComment(comment);
            }else{
                saveFile.setAfmComment(comment);
            }
            saveFile = mobileUploadFileRepository.save(saveFile);

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            List<MasterCodeDto> mailListLRaws = masterCodeService.findCodeList(CodeType.C0004);
            List<String> maillists = new ArrayList<>();

            for (MasterCodeDto masterCodeDto : mailListLRaws) {
                maillists.add(masterCodeDto.getName());
            }

            if(maillists.size() != 0) {
                mailService.mailsend(maillists,
                        "현장사진이 등록되었습니다.",
                        "작성자 : " + account.getUsername() + "\r\n\n",
                        "사진설명 : "+ comment  + "\r\n\n",
                        " 작성날짜 : " + LocalDateTime.now().format(dateTimeFormatter) + "", "" + "\r\n\n",
                        "해당사진 보기 : https://kict119.broadwave.co.kr/uploadfiles/recordimages/"+saveFileName);
            }

            return saveFile;
        } catch (IOException e) {
            throw new Exception("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    public List<MobileUploadFileDto> findByMobileUploadFileList(Account account) {
        return mobileUploadFileRepositoryCustom.findByMobileUploadFileList(account);
    }


    public Page<MobileUploadFileDto> findByMobileUploadFilePage(Account account, LocalDateTime s_from, LocalDateTime s_to,String s_username, Pageable pageable) {
        return mobileUploadFileRepositoryCustom.findByMobileUploadFilePage(account,s_from,s_to,s_username,pageable);
    }

    public Optional<MobileUploadFile> findById(Long mobileid) {
        return mobileUploadFileRepository.findById(mobileid);
    }

    public void delete(MobileUploadFile mobileUploadFile) {
        mobileUploadFileRepository.delete(mobileUploadFile);
    }
}
