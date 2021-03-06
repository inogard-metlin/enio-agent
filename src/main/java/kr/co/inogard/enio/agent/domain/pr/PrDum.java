package kr.co.inogard.enio.agent.domain.pr;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrDum {
  private String erpPrNo;
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
  private String prebidYn;
  private Date e4uIfDt;
  private String e4uPrNo;
  private String erpRfqNo;
  private String bidSubTypecd;
  private String bidTypecd;
  private String e4uIfSt;
  private String cnclFlag;
  private Date cnclReqDt;
  private Date cnclRsltDt;
  private String cnclRsltMsg;
  private Long filteredCount;

  /*
   *2018.06.28 김원기 추가
   *덕성여대 요구 
   */
  private String selectedBidderStd;
  private BigDecimal bidderRate;
  private Date bidExpireDt;
  private String rfqdocYn;
  private Date rfqdocExpireDt;
  private String rfqdocRmrk;
  private String techdocYn;
  private Date techdocExpireDt;
  private String techdocRmrk;
  private String constrPeriodInptKind;
  private String constrPeriodRmrk;
  
  /*
   *2018.09.20 김원기 추가
   *울산대 요구 
   */
  private String subOrgCd;
  
  
  List<PrItemDum> itemList;
  List<PrFileDum> fileList;
  List<PrUserDum> userList;
  
}
