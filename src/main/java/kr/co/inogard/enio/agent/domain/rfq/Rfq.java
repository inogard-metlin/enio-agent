package kr.co.inogard.enio.agent.domain.rfq;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data
public class Rfq {
	private String rfqNo;
	private String ttl;
	private String quotRev;
	private String prTypecd;
	private String ioFlag;
	private String taxTypecd;
	private Date dlvFrYmd;
	private Date dlvToYmd;
	private String dlvLoc;
	private Date dlvReqYmd;
	private String spotDscrYn;
	private Date spotDscrDt;
	private String spotDscrUsrnm;
	private String spotDscrUsrtel;
	private String spotDscrLoc;
	private String dlvCondRmrk;
	private String payCondRmrk;
	private BigDecimal bidPlanAmt;
	private String bidSubTypecd;
	private String bidTypecd;
	private String bidInsrYn;
	private String bidInsrRate;
	private String rfqRmrk;
	private String chrgUsrcd;
	private String chrgDeptcd;
	private String rfqSt;
	private Date regDt;
	private String erpRfqNo;
	private Long filteredCount;

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

	private String subOrgCd;
}
