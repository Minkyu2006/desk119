package kr.co.broadwave.desk.record.file.mobilefile;

import kr.co.broadwave.desk.accounts.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Minkyu
 * Date : 2020-08-05
 * Remark : MobileUploadFileRepositoryCustom
 */
public interface MobileUploadFileRepositoryCustom {
    List<MobileUploadFileDto> findByMobileUploadFileList(Account account);

    Page<MobileUploadFileDto> findByMobileUploadFilePage(Account account, LocalDateTime s_from, LocalDateTime s_to,String s_username,Pageable pageable);
}
