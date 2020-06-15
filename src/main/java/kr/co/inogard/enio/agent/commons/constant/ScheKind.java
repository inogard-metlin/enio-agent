package kr.co.inogard.enio.agent.commons.constant;

import lombok.Getter;

@Getter
public enum ScheKind {
	S("Schedule"), R("Realtime");
	
	private String codeNm;
	
	private ScheKind(String codeNm) {
		this.codeNm = codeNm;
	}
}
