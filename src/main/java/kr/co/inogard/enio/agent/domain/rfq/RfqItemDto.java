package kr.co.inogard.enio.agent.domain.rfq;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonFormat;
import kr.co.inogard.enio.agent.commons.validator.MaxByteLength;
import lombok.Data;

@Data
public class RfqItemDto {

  @Data
  public static class Create {
    @NotBlank
    @Size(min = 20)
    @MaxByteLength(20)
    private String rfqNo;

    @NotBlank
    @Size(min = 1)
    @MaxByteLength(5)
    private String itemSeq;

    @Size(min = 20)
    @MaxByteLength(20)
    private String prNo;

    @Size(min = 1)
    @MaxByteLength(5)
    private String prItemSeq;

    @MaxByteLength(150)
    private String itemNm;

    @Size(min = 20)
    @MaxByteLength(20)
    private String itemCd;

    @MaxByteLength(10)
    private String clsCd;

    @MaxByteLength(120)
    private String sizeNm;

    @MaxByteLength(150)
    private String modelNm;

    @MaxByteLength(120)
    private String mkCompNm;

    private String dlvLoc;
    private Date dlvReqYmd;

    @MaxByteLength(6)
    private String unitCd;

    @NotNull
    @Digits(fraction = 4, integer = 15)
    private BigDecimal planUnitPrc;


    @Digits(fraction = 2, integer = 12)
    private BigDecimal qty;

    @Valid
    private List<RfqSrvDto.Create> rfqSrvList;
  }

  @Data
  public static class Response {
    private String rfqNo;
    private String itemSeq;
    private String prNo;
    private String prItemSeq;
    private String itemNm;
    private String itemCd;
    private String clsCd;
    private String sizeNm;
    private String modelNm;
    private String mkCompNm;
    private String dlvLoc;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date dlvReqYmd;
    private String unitCd;
    private BigDecimal planUnitPrc;
    private BigDecimal qty;
  }

}
