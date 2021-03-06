package kr.co.inogard.enio.agent.domain.agent;

import java.util.Date;

import org.springframework.security.crypto.codec.Base64;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Agent {
  private String agtCd;
  private String licenceKey;
  private String adminId;
  private String adminPwd;
  private String apiCd;
  private String apiLicenceKey;
  private String apiConnUrl;
  private Date regDt;

  public String getRawLicenceKey() {
    return decodeStr(this.licenceKey);
  }

  public String getApiRawLicenceKey() {
    return decodeStr(this.apiLicenceKey);
  }

  private String decodeStr(String encodedStr) {
    String decodedStr = null;
    try {
      decodedStr = new String(Base64.decode(encodedStr.getBytes()), "UTF-8");
    } catch (Exception e) {
    }
    return decodedStr;
  }
}
