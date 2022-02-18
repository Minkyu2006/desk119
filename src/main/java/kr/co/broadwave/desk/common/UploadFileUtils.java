package kr.co.broadwave.desk.common;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import lombok.extern.slf4j.Slf4j;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

/**
 * @author InSeok
 * Date : 2019-07-26
 * Remark :  파일 업로드 유틸
 * https://github.com/woobong/spring-boot-jpa-summernote-image-upload-example 소스 참고함
 */
@Slf4j
public class UploadFileUtils {
    private static final Logger logger = LoggerFactory.getLogger(UploadFileUtils.class);

    /**
//     * @param filePath
//     * @param multipartFile
     * @return 생성된 파일 명(유일한 값)
     * @throws IllegalStateException
     * @throws IOException
     */

    // 일반파일 저장
    public static String fileSave(String uploadPath, MultipartFile file) throws IllegalStateException, IOException {

        File uploadPathDir = new File(uploadPath);

        if ( !uploadPathDir.exists() ){
            uploadPathDir.mkdirs();
        }

        // 파일 중복명 처리
        String genId = UUID.randomUUID().toString();
        genId = genId.replace("-", "");

        String originalfileName = file.getOriginalFilename();
        String fileExtension = getExtension(originalfileName);
        String saveFileName = genId + "." + fileExtension;

        String savePath = calcPath(uploadPath);

        File target = new File(uploadPath + savePath, saveFileName);

        FileCopyUtils.copy(file.getBytes(), target);

        return makeFilePath(uploadPath, savePath, saveFileName);
    }

    // 모바일파일저장(썸네일파일추가)
    public static String s_fileSave(String uploadPath, MultipartFile file) throws Exception {

        File uploadPathDir = new File(uploadPath);

        if ( !uploadPathDir.exists() ){
            uploadPathDir.mkdirs();
        }

        // 파일 중복명 처리
        String genId = UUID.randomUUID().toString();
        genId = genId.replace("-", "");

        String originalfileName = file.getOriginalFilename();
        String fileExtension = getExtension(originalfileName);

        String saveFileName = genId + "." + fileExtension;

        String savePath = calcPath(uploadPath);

        File target = new File(uploadPath + savePath, saveFileName);
        FileCopyUtils.copy(file.getBytes(), target);

        makeThumbnail(uploadPath, savePath, saveFileName);

        return makeFilePath(uploadPath, savePath, saveFileName);
    }

    /**
     * 파일이름으로부터 확장자를 반환
     *
     * @param fileName
     *            확장자를 포함한 파일 명
     * @return 파일 이름에서 뒤의 확장자 이름만을 반환
     */
    public static String getExtension(String fileName) {
        int dotPosition = fileName.lastIndexOf('.');

        if (-1 != dotPosition && fileName.length() - 1 > dotPosition) {
            return fileName.substring(dotPosition + 1);
        } else {
            return "";
        }
    }

    private static String calcPath(String uploadPath) {

        Calendar cal = Calendar.getInstance();

        String yearPath = File.separator + cal.get(Calendar.YEAR);
        String monthPath = yearPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.MONTH) +1);
        String datePath = monthPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.DATE));

        makeDir(uploadPath, yearPath, monthPath, datePath);

        logger.info(datePath);

        return datePath;
    }

    private static void makeDir(String uploadPath, String... paths) {

        System.out.println(paths[paths.length - 1] + " : " + new File(paths[paths.length - 1]).exists());
        if (new File(paths[paths.length - 1]).exists()) {
            return;
        }

        for (String path : paths) {
            File dirPath = new File(uploadPath + path);

            if (!dirPath.exists()) {
                dirPath.mkdir();
            }
        }
    }

    private static String makeFilePath(String uploadPath, String path, String fileName) throws IOException {
        String filePath = uploadPath + path + File.separator + fileName;
        return filePath.substring(uploadPath.length()).replace(File.separatorChar, '/');
    }

    private static String makeThumbnail(String uploadPath, String path, String fileName) throws Exception {

//        BufferedImage sourceImg = ImageIO.read(new File(uploadPath + path, fileName));
        File imageFile = new File(uploadPath+'/'+path+'/'+fileName);
        int orientation = 1;
        Metadata metadata; // 이미지 메타 데이터 객체
        ExifIFD0Directory directory; // 이미지의 Exif 데이터를 읽기 위한 객체

        // 사진 회전현상 방지
        try {
            metadata = ImageMetadataReader.readMetadata(imageFile);
            directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
            if(directory != null){
                orientation = directory.getInt(ExifIFD0Directory.TAG_ORIENTATION); // 회전정보
            }

        }catch (Exception e) {
            orientation=1;
        }
//        log.info("orientation : "+orientation);
        // 회전 시킨다.
//        sourceImg = Scalr.rotate(sourceImg, Scalr.Rotation.CW_90, (BufferedImageOp) null);
        BufferedImage srcImg = ImageIO.read(imageFile);
        switch (orientation) {
            case 6:
                srcImg = Scalr.rotate(srcImg, Scalr.Rotation.CW_90, (BufferedImageOp) null);
                break;
            case 1:
                break;
            case 3:
                srcImg = Scalr.rotate(srcImg, Scalr.Rotation.CW_180, (BufferedImageOp) null);
                break;
            case 8:
                srcImg = Scalr.rotate(srcImg, Scalr.Rotation.CW_270, (BufferedImageOp) null);
                break;
            default:
                orientation=1;
                break;
        }
//        BufferedImage destImg = Scalr.resize(srcImg, maxWidth, maxHeight);
        BufferedImage destImg = Scalr.resize(srcImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 100);

        String thumbnailName = uploadPath + path + File.separator + "s_" + fileName;

        File newFile = new File(thumbnailName);

        String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);

        ImageIO.write(destImg, formatName.toUpperCase(), newFile);

        return thumbnailName.substring(uploadPath.length()).replace(File.separatorChar, '/');
    }
}
