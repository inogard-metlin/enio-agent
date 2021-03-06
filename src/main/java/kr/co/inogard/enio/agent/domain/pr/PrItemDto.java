package kr.co.inogard.enio.agent.domain.pr;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import kr.co.inogard.enio.agent.commons.validator.MaxByteLength;
import lombok.Data;

public class PrItemDto {

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
    @MaxByteLength(150)
    private String itemNm;

    @NotBlank
    @MaxByteLength(20)
    private String itemCd;

    @NotBlank
    @MaxByteLength(10)
    private String clsCd;

    @NotBlank
    @MaxByteLength(120)
    private String sizeNm;

    @NotBlank
    @MaxByteLength(150)
    private String modelNm;

    @NotBlank
    @MaxByteLength(6)
    private String unitCd;

    @NotNull
    @Digits(fraction = 2, integer = 12)
    private BigDecimal qty;

    @NotNull
    @Digits(fraction = 2, integer = 17)
    private BigDecimal planUnitPrc;

    @MaxByteLength(120)
    private String mkCompNm;

    @NotBlank
    @MaxByteLength(120)
    private String dlvLoc;

    @NotNull
    @DateTimeFormat(pattern = "yyyyMMdd")
    private Date dlvReqYmd;

    private List<PrSrvDto.Create> srvList;
  }

  @Data
  public static class Response {
    private String prNo;
    private String itemSeq;
    private String itemNm;
    private String itemCd;
    private String erpItemCd;
    private String clsCd;
    private String sizeNm;
    private String modelNm;
    private String dlvLoc;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date dlvReqYmd;
    private String mkCompNm;
    private String unitCd;
    private BigDecimal qty;
    private BigDecimal planUnitPrc;
    private String rfqNo;
    private String poNo;
    private String itemSt;
    private String erpPrNo;
  }

}
