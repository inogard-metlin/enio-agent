package kr.co.inogard.enio.agent.pr;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import kr.co.inogard.enio.agent.Application;
import kr.co.inogard.enio.agent.commons.constant.IOFlag;
import kr.co.inogard.enio.agent.commons.constant.PrTypeCd;
import kr.co.inogard.enio.agent.commons.constant.TaxTypeCd;
import kr.co.inogard.enio.agent.domain.pr.PrDto;
import kr.co.inogard.enio.agent.domain.pr.PrDto.Response;
import kr.co.inogard.enio.agent.domain.pr.PrDum;
import kr.co.inogard.enio.agent.mappers.pr.PrDumMapper;
import kr.co.inogard.enio.agent.service.pr.PrService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
public class PrServiceTest {

  @Autowired
  private PrService prService;
  
  @Autowired
  private PrDumMapper prDumMapper;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private ModelMapper modelMapper;

  private PrDum prDum1;

  @Before
  public void setUp() throws ParseException {
    SimpleDateFormat formatterYMD = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat formatterYMDHH = new SimpleDateFormat("yyyyMMddHH");

    this.prDum1 = new PrDum();
    prDum1.setErpPrNo("01234567890123456789");
    prDum1.setTtl("구매요청 테스트");
    prDum1.setPrTypecd(PrTypeCd.N.name());
    prDum1.setTaxTypecd(TaxTypeCd.A1000.name());
    prDum1.setIoFlag(IOFlag.D.name());
    prDum1.setGmChrgrCd("S003700001");
    prDum1.setPrChrgrCd("S003700002");
    prDum1.setGrChrgrCd("S003700002");
    prDum1.setChkChrgrCd("S003700002");
    prDum1.setDlvFrYmd(null);
    prDum1.setDlvToYmd(null);
    prDum1.setReqUsrNm("실수요자");
    prDum1.setReqUsrTel("02-2121-2121");
    prDum1.setPrRmrk("구매요청비고");
    prDum1.setDlvLoc("납품장소");
    prDum1.setDlvReqYmd(formatterYMD.parse("20170918"));
    prDum1.setSpotDscrYn("N");
    prDum1.setSpotDscrDt(formatterYMDHH.parse("2017091817"));
    prDum1.setSpotDscrUsrnm(null);
    prDum1.setSpotDscrUsrtel(null);
    prDum1.setSpotDscrLoc(null);
    prDum1.setPrebidYn("N");
    prDum1.setE4uPrNo("S0037109999010100002");
  }

  @Test
  public void getAll() throws JsonProcessingException {
    // Given
    prDumMapper.add(prDum1);
    DataTablesInput mockDataTablesInput = mock(DataTablesInput.class);
    Mockito.when(mockDataTablesInput.getStart()).thenReturn(0);
    Mockito.when(mockDataTablesInput.getLength()).thenReturn(10);

    // When
    PrDto.Search search = new PrDto.Search();
    // search.setE4uIfSt("SR");
    DataTablesOutput<PrDto.Response> result = prService.getAllSendPr(search, mockDataTablesInput);
    
    // Then
    verify(mockDataTablesInput, times(2)).getStart();
    assertNotNull(result);

    log.debug("data : {}", result.getData());
  }

  @Test
  public void jsonViewTest() throws JsonProcessingException {
    // Given
    PrDto.Response response = modelMapper.map(prDum1, PrDto.Response.class);
    List<Response> datas = new ArrayList<Response>();
    datas.add(response);
    
    // When
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    String result1 =
        objectMapper.writerWithView(PrDto.Views.ApiView.class).writeValueAsString(response);

    String result2 =
        objectMapper.writerWithView(PrDto.Views.PublicView.class).writeValueAsString(response);

    // Then
    log.debug("result1 : {}", result1);
    log.debug("result2 : {}", result2);
    assertThat(result1, hasJsonPath("$.prNo", equalTo(prDum1.getE4uPrNo())));
    assertThat(result1, hasJsonPath("$.erpPrNo", equalTo(prDum1.getErpPrNo())));
    
  }

}
