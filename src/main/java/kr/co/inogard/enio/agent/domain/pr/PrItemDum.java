package kr.co.inogard.enio.agent.domain.pr;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrItemDum {
  private String erpPrNo;
  private String itemSeq;
  private String itemNm;
  private String itemCd;
  private String erpItemCd;
  private String clsCd;
  private String sizeNm;
  private String modelNm;
  private String dlvLoc;
  private Date dlvReqYmd;
  private String mkCompNm;
  private String unitCd;
  private BigDecimal qty;
  private BigDecimal planUnitPrc;

  List<PrSrvDum> srvList;
}
