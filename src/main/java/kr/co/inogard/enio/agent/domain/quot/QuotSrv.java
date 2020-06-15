package kr.co.inogard.enio.agent.domain.quot;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class QuotSrv {
	
	private String rfqNo;
	private String quotRev;
	private String cusCd;
	private String itemSeq;
	private String srvSeq;
	private String rfqItemSeq;
	private String rfqSrvSeq;
	private BigDecimal qty;
	private BigDecimal unitPrc;
	private BigDecimal amt;
	
}

