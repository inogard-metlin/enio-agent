package kr.co.inogard.enio.agent.domain.pr;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

public class PrFileDto {

  @Data
  public static class Response {
    private String prNo;
    private String fileSeq;
    private String fileNo;
    private String cliFileNm;
    private String svrFileNm;
    private String svrFilePath;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm",
        timezone = "Asia/Seoul")
    private Date regDt;
    private String erpPrNo;
  }

}
