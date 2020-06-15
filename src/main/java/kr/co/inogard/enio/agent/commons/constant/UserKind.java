package kr.co.inogard.enio.agent.commons.constant;

import lombok.Getter;

@Getter
public enum UserKind {
	PR("구매요청자"), GM("구매담당자"), GR("입고담당자"), CHK("검수담당자");

	private String codeNm;

	private UserKind(String codeNm) {
		this.codeNm = codeNm;
	}

	public boolean isPrUser() {
		return (this == UserKind.PR);
	}

	public boolean isGmUser() {
		return (this == UserKind.GM);
	}

	public boolean isGrUser() {
		return (this == UserKind.GR);
	}

	public boolean isChkUser() {
		return (this == UserKind.CHK);
	}

}