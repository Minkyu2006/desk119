package kr.co.broadwave.desk.notice;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * @author InSeok
 * Date : 2019-07-29
 * Remark :
 */
@Slf4j
@Service
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public NoticeService(NoticeRepository noticeRepository, ModelMapper modelMapper) {
        this.noticeRepository = noticeRepository;
        this.modelMapper = modelMapper;
    }

    public Notice save(Notice notice){

        return noticeRepository.save(notice);
    }

    public NoticeDto findById(Long id){
        Optional<Notice> optionalNotice = noticeRepository.findById(id);
        if (optionalNotice.isPresent()) {
            return modelMapper.map(optionalNotice.get(), NoticeDto.class);
        } else {
            return null;
        }
    }

}
