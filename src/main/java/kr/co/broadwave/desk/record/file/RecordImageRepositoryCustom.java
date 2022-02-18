package kr.co.broadwave.desk.record.file;

import kr.co.broadwave.desk.record.Record;

import java.util.List;

/**
 * @author Minkyu
 * Date : 2020-07-08
 * Remark : RecordImageRepositoryCustom
 */
public interface RecordImageRepositoryCustom {

    RecordUploadFileDto recordUploadFile(Long record, int stateVal);

    List<RecordUploadFileDto> recordUploadFileList(Long record, int stateVal);

    long fileDel(Record record);

    List<RecordUploadFileDto> recordUploadFilePrint(List<Long> recordList, int stateVal);
}
