package kr.co.inogard.enio.agent.domain.rfq;

import java.util.Date;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import kr.co.inogard.enio.agent.commons.validator.MaxByteLength;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RfqDegreeDto {
  @Data
  public static class Create {
    @NotBlank
    @Size(min = 20)
    @MaxByteLength(20)
    private String rfqNo;

    @NotBlank
    @MaxByteLength(2)
    private String quotRev;

    @DateTimeFormat(pattern = "yyyyMMddHHmmss")
    private Date bidStartDt;

    @DateTimeFormat(pattern = "yyyyMMddHHmmss")
    private Date bidExpireDt;

    @DateTimeFormat(pattern = "yyyyMMddHHmmss")
    private Date bidOpenDt;

    @MaxByteLength(450)
    private String rebidRmrk;
  }
}
