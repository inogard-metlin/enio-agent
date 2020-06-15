package kr.co.inogard.enio.agent.pr;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.text.SimpleDateFormat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import kr.co.inogard.enio.agent.Application;
import kr.co.inogard.enio.agent.commons.constant.IOFlag;
import kr.co.inogard.enio.agent.commons.constant.PrTypeCd;
import kr.co.inogard.enio.agent.commons.constant.TaxTypeCd;
import kr.co.inogard.enio.agent.domain.pr.PrDto;
import kr.co.inogard.enio.agent.domain.pr.PrDum;
import kr.co.inogard.enio.agent.mappers.pr.PrDumMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
public class PrDumMapperTest {

  @Autowired
  private PrDumMapper prDumMapper;

  private PrDum prDum1;

  @Before
  public void setUp() throws Exception {
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
  }

  @Test
  public void add() {
    // Given
    // When
    prDumMapper.add(prDum1);

    // Then
    assertThat(prDumMapper.findByErpPrNo(prDum1.getErpPrNo()).getTtl(), is(prDum1.getTtl()));
  }

  @Test
  public void findAllNotSend() {
    // Given
    // When
    // Then
    prDumMapper.findAllNotSend();
  }

  @Test
  public void findAllReqCancelNotSend() {
    // Given
    // When
    // Then
    prDumMapper.findAllReqCancelNotSend();
  }

  @Test
  public void getAll() {
    // Given
    // When
    prDumMapper.add(prDum1);

    // Then
    PrDto.Search search = new PrDto.Search();
    search.setE4uIfSt("SR");
    assertThat(prDumMapper.findAll(search, null).size(), is(3));
  }

  @Test
  public void getAllByDataTablesInput() {
    // Given
    DataTablesInput mockDataTablesInput = mock(DataTablesInput.class);
    Mockito.when(mockDataTablesInput.getStart()).thenReturn(0);
    Mockito.when(mockDataTablesInput.getLength()).thenReturn(10);

    // When
    prDumMapper.add(prDum1);

    // Then
    PrDto.Search search = new PrDto.Search();
    search.setE4uIfSt("SR");


    verify(mockDataTablesInput, times(2)).getStart();
    assertThat(prDumMapper
        .findAll(search, new PageRequest(0, 20, Direction.fromString("desc"), "reg_dt")).size(),
        is(3));
  }

  @Test
  public void getAllByOrders() {
    // Given
    // When
    prDumMapper.add(prDum1);

    // Then
    PrDto.Search search = new PrDto.Search();
    assertThat(prDumMapper
        .findAll(search, new PageRequest(0, 20, Direction.fromString("desc"), "reg_dt")).size(),
        is(3));
  }

}
