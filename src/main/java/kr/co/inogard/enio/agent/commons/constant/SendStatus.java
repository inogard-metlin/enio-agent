package kr.co.inogard.enio.agent.commons.constant;

public enum SendStatus {
	SEND_READY("SR", "대기"), 
	SEND_WORK("SW", "송신 중"), 
	SEND_WORK_ERR("SWE", "송신 오류"), 
	SEND_FAIL("SF", "송신 실패"), 
	SEND_COMPLETE("SC", "정상 완료");

	private final String code;
	private final String codeNm;

	private SendStatus(String code, String codeNm) {
		this.code = code;
		this.codeNm = codeNm;
	}

	public static SendStatus codeOf(String code) {
		for (SendStatus sendStatus : SendStatus.values()) {
			if (sendStatus.getCode().equals(code)) {
				return sendStatus;
			}
		}
		return null;
	}

	public String getCode() {
		return this.code;
	}

	public String getCodeNm() {
		return codeNm;
	}

	public boolean isOk() {
		return (this.isReady() || this.isComplete());
	}

	public boolean isReady() {
		return (this == SendStatus.SEND_READY);
	}

	public boolean isComplete() {
		return (this == SendStatus.SEND_COMPLETE);
	}
}
