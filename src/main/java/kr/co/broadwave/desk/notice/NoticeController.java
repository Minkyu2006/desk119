package kr.co.broadwave.desk.notice;

import kr.co.broadwave.desk.notice.file.UploadFile;
import kr.co.broadwave.desk.notice.file.UploadFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Optional;

/**
 * @author InSeok
 * Date : 2019-07-25
 * Remark :
 */
@Controller
@RequestMapping("notice")
public class NoticeController {

    private final NoticeService noticeService;
    private final UploadFileRepository uploadFileRepository;


    @Autowired
    public NoticeController(NoticeService noticeService, UploadFileRepository uploadFileRepository) {
        this.noticeService = noticeService;
        this.uploadFileRepository = uploadFileRepository;
    }


    @RequestMapping("noticelist")
    public String noticeList(){
        return "notice/noticelist";
    }

    @RequestMapping("noticeview/{id}")
    public String noticeView(Model model, @PathVariable Long id){
        NoticeDto noticeDto = noticeService.findById(id);

        model.addAttribute("notice", noticeDto);
        return "notice/noticeview";
    }
    @RequestMapping("download/{fileid}")
    @ResponseBody
    public byte[] downProcess(HttpServletResponse response,
                              @PathVariable Long fileid) throws IOException {

        Optional<UploadFile> optionalUploadFile = uploadFileRepository.findById(fileid);
        String filePath = optionalUploadFile.get().getFilePath();
        String filename = URLEncoder.encode(optionalUploadFile.get().getFileName(),"UTF-8").replaceAll("\\+", "%20");
        File file = new File(filePath);
        byte[] bytes = FileCopyUtils.copyToByteArray(file);

        //String filename = new String(file.getName().getBytes(), "iso_8859_1");

        response.setHeader("Content-Disposition",
                "attachment;filename=\"" + filename + "\"");
        response.setContentLength(bytes.length);
        return bytes;
    }
}
