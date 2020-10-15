package kr.co.inogard.enio.agent.commons.constant;

public enum CnclFlag {
  CR("취소접수"), CW("취소처리중"), CWE("취소처리오류"), CF("취소실패"), CC("취소성공");
  private final String codeNm;

  CnclFlag(String codeNm) {
    this.codeNm = codeNm;
  }

  public String getCodeNm() {
    return codeNm;
  }

}
