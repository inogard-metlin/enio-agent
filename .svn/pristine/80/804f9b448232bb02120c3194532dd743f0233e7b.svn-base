package kr.co.inogard.enio.agent.domain.quot;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import kr.co.inogard.enio.agent.commons.validator.MaxByteLength;
import lombok.Data;


public class QuotDto {

  @Data
  public static class Create {
    @NotBlank
    @Size(min = 20)
    @MaxByteLength(20)
    private String rfqNo;

    @NotBlank
    @Size(min = 2)
    @MaxByteLength(2)
    private String quotRev;

    @NotBlank
    @Size(min = 10)
    @MaxByteLength(10)
    private String cusCd;

    @Digits(fraction = 4, integer = 15)
    private BigDecimal amt;

    @Digits(fraction = 4, integer = 15)
    private BigDecimal vatAmt;

    @MaxByteLength(5)
    private String taxTypeCd;

    @MaxByteLength(3)
    private String crcyCd;

    @Digits(fraction = 2, integer = 12)
    private BigDecimal crcyRate;

    @DateTimeFormat(pattern = "yyyyMMddHHmmss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    private Date quotSbmtDt;

    @Valid
    private List<QuotFile> quotFileList;

    @Valid
    private List<QuotItemDto> quotItemList;
  }

  @Data
  public static class Response {
    private String rfqNo;
    private String quotRev;
    private String cusCd;
    private BigDecimal amt;
    private BigDecimal vatAmt;
    private String taxTypeCd;
    private String crcyCd;
    private BigDecimal crcyRate;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm",
        timezone = "Asia/Seoul")
    private Date quotSbmtDt;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm",
        timezone = "Asia/Seoul")
    private Date e4uIfDt;
    
    private String rsltYn;
    private String cusNm;
    private String ceoNm;

    private String rsltCd;
    private String rsltMsg;
  }
}

