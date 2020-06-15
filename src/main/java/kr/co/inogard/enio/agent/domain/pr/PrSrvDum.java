package kr.co.inogard.enio.agent.domain.pr;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrSrvDum {
	private String erpPrNo;
	private String itemSeq;
	private String srvSeq;
	private String srvNm;
	private String srvCd;
	private String unitCd;
	private BigDecimal qty;
}
