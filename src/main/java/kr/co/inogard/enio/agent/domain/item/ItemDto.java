package kr.co.inogard.enio.agent.domain.item;

import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import kr.co.inogard.enio.agent.commons.validator.MaxByteLength;
import lombok.Data;

public class ItemDto {

  @Data
  public static class Create {
    @NotBlank
    @MaxByteLength(30)
    private String itemCd;

    @NotBlank
    @MaxByteLength(150)
    private String itemNm;

    @MaxByteLength(120)
    private String sizeNm;

    @MaxByteLength(150)
    private String modelNm;
    
    @MaxByteLength(6)
    private String unitCd;

    @MaxByteLength(120)
    private String mkCompNm;

    @NotBlank
    @MaxByteLength(10)
    private String clsCd;
  }

  @Data
  public static class CreateWrapper {
    @Valid
    private List<Create> datas;
  }

  @Data
  //@JsonInclude(JsonInclude.Include.NON_EMPTY)
  public static class Response {
    @JsonView(Views.ApiView.class)
    private String itemCd;

    @JsonView(Views.ApiView.class)
    private String itemNm;

    @JsonView(Views.ApiView.class)
    private String sizeNm;

    @JsonView(Views.ApiView.class)
    private String modelNm;

    @JsonView(Views.ApiView.class)
    private String unitCd;

    @JsonView(Views.ApiView.class)
    private String clsCd;

    @JsonView(Views.ApiView.class)
    private String mkCompNm;

    @JsonView(Views.ApiView.class)
    private String erpItemCd;

    @JsonView(Views.ApiView.class)
    private String rsltCd;

    @JsonView(Views.ApiView.class)
    private String rsltMsg;

    @JsonView(Views.PublicView.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",
        timezone = "Asia/Seoul")
    private Date regDt;

    @JsonView(Views.PublicView.class)
    private String useYn;

    @JsonView(Views.PublicView.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",
        timezone = "Asia/Seoul")
    private Date e4uIfDt;

    @JsonView(Views.PublicView.class)
    private String e4uIfSt;
  }

  @Data
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  public static class ResponseWrapper {
    @JsonView(Views.ApiView.class)
    private String rsltCd;

    @JsonView(Views.ApiView.class)
    private String rsltMsg;

    @JsonView(Views.ApiView.class)
    private List<Response> datas;
  }

  @Data
  public static class Search {
    private String value;
    private String agtCd;

    @MaxByteLength(3)
    private String e4uIfSt;
  }

  public static class Views {
    public static interface ApiView {}

    public static interface PublicView extends ApiView, DataTablesOutput.View {}
  }
}
