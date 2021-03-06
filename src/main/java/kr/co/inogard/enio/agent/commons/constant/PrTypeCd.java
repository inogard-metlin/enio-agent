package kr.co.inogard.enio.agent.commons.constant;

import lombok.Getter;

@Getter
public enum PrTypeCd {
	A("매각"), E("잡수입"), N("물품"), S("공사"), T("위탁관리"), W("용역");
	
	private String codeNm;
	
	private PrTypeCd(String codeNm) {
		this.codeNm = codeNm;
	}
	
	public boolean isNormal() {
		return (this == PrTypeCd.N);
	}
	
	public boolean isService() {
		return (this == PrTypeCd.S);
	}
	
	public boolean isWork() {
		return (this == PrTypeCd.W);
	}
	
}
