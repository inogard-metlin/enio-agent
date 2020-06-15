package kr.co.inogard.enio.agent.domain.quot;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class QuotItem {
	private String rfqNo;
	private String quotRev;
	private String cusCd;
	private String itemSeq;
	private String rfqItemSeq;
	private BigDecimal qty;
	private BigDecimal unitPrc;
	private BigDecimal amt;
	
	List<QuotSrv> srvList;
}

