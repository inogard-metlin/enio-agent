package kr.co.inogard.enio.agent.domain.pr;

import java.math.BigDecimal;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import kr.co.inogard.enio.agent.commons.validator.MaxByteLength;
import lombok.Data;

public class PrSrvDto {

  @Data
  public static class Create {
    @NotBlank
    @MaxByteLength(30)
    private String prNo;

    @NotBlank
    @Size(min = 5)
    @MaxByteLength(5)
    private String itemSeq;

    @NotBlank
    @Size(min = 5)
    @MaxByteLength(5)
    private String srvSeq;

    @NotBlank
    @MaxByteLength(150)
    private String srvNm;

    @NotBlank
    @MaxByteLength(5)
    private String srvCd;

    @NotBlank
    @MaxByteLength(4)
    private String unitCd;

    @NotNull
    @Digits(fraction = 2, integer = 14)
    private BigDecimal qty;
  }
  
  @Data
  public static class Response {
    private String prNo;
    private String itemSeq;
    private String srvSeq;
    private String srvNm;
    private String srvCd;
    private String unitCd;
    private BigDecimal qty;
    private String erpPrNo;
  }
}
