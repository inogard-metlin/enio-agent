package kr.co.inogard.enio.agent.domain.pr;

import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pr {
  private String prNo;
  private String ttl;
  private String prTypecd;
  private String taxTypecd;
  private String ioFlag;
  private Date dlvFrYmd;
  private Date dlvToYmd;
  private String dlvLoc;
  private Date dlvReqYmd;
  private String spotDscrYn;
  private Date spotDscrDt;
  private String spotDscrUsrnm;
  private String spotDscrUsrtel;
  private String spotDscrLoc;
  private String reqUsrNm;
  private String reqUsrTel;
  private String gmChrgrCd;
  private String prChrgrCd;
  private String grChrgrCd;
  private String chkChrgrCd;
  private Date regDt;
  private String prRmrk;
  private String returnYn;
  private Date returnDt;
  private String returnRmrk;
  private String prebidYn;
  private Date e4uIfDt;
  private String erpSyncYn;
  private Date erpSyncDt;
  private String erpPrNo;
  private String erpRfqNo;
  private String bidSubTypecd;
  private String bidTypecd;
  private Long filteredCount;

  List<PrItem> itemList;
  List<PrFile> fileList;
  List<PrUserDum> userList;
}
