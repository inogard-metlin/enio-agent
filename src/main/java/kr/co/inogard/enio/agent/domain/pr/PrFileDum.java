package kr.co.inogard.enio.agent.domain.pr;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrFileDum {
  private String erpPrNo;
  private String fileSeq;
  private String svrFileNm;
  private String svrFilePath;
  private String svrFileLink;
}
