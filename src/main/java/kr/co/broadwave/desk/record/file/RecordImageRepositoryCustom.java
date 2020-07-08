package kr.co.broadwave.desk.record.file;

import kr.co.broadwave.desk.record.Record;

import java.util.List;

/**
 * @author Minkyu
 * Date : 2020-07-08
 * Remark : RecordImageRepositoryCustom
 */
public interface RecordImageRepositoryCustom {

    RecordUploadFileDto recordUploadFile(Record record, int stateVal);

    List<RecordUploadFileDto> recordUploadFileList(Record record, int stateVal);

    long fileDel(Record record);
}
