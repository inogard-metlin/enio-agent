package kr.co.inogard.enio.agent.domain.item;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Item {
  private String itemCd;
  private String itemNm;
  private String sizeNm;
  private String modelNm;
  private String unitCd;
  private String mkCompNm;
  private Date regDt;
  private String useYn;
  private String clsCd;
  private Date e4uIfDt;
  private String erpItemCd;
  private Long filteredCount;
}
