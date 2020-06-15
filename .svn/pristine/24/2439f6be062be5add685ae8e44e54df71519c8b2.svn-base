package kr.co.inogard.enio.agent.item;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasNoJsonPath;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
import kr.co.inogard.enio.agent.Application;
import kr.co.inogard.enio.agent.domain.item.ItemDto;
import kr.co.inogard.enio.agent.domain.item.ItemDto.Response;
import kr.co.inogard.enio.agent.domain.item.ItemDum;
import kr.co.inogard.enio.agent.service.item.ItemService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
public class ItemServiceTest {

  @Autowired
  private ItemService itemService;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private ModelMapper modelMapper;

  private ItemDum itemDum1;
  private ItemDum itemDum2;
  private ItemDum itemDum3;

  @Before
  public void setUp() {
    itemDum1 = new ItemDum();
    itemDum1.setErpItemCd("100000000001");
    itemDum1.setItemNm("책상");
    itemDum1.setSizeNm("1024*2048");
    itemDum1.setModelNm("이케아 책상");
    itemDum1.setUnitCd("EA");
    itemDum1.setMkCompNm("이케아");
    itemDum1.setUseYn("Y");
    itemDum1.setClsCd("9999");
    itemDum1.setE4uItemCd("99999999991");

    itemDum2 = new ItemDum();
    itemDum2.setErpItemCd("100000000002");
    itemDum2.setItemNm("의자");
    itemDum2.setSizeNm("1024*2048");
    itemDum2.setModelNm("이케아 의자");
    itemDum2.setUnitCd("EA");
    itemDum2.setMkCompNm("이케아");
    itemDum2.setUseYn("Y");
    itemDum2.setClsCd("9999");

    itemDum3 = new ItemDum();
    itemDum3.setErpItemCd("100000000003");
    itemDum3.setItemNm("소파");
    itemDum3.setSizeNm("1024*2048");
    itemDum3.setModelNm("이케아 소파");
    itemDum3.setUnitCd("EA");
    itemDum3.setMkCompNm("이케아");
    itemDum3.setUseYn("Y");
    itemDum3.setClsCd("9999");
  }

  @Test
  public void getAll() {
    // Given
    DataTablesInput mockDataTablesInput = mock(DataTablesInput.class);
    Mockito.when(mockDataTablesInput.getStart()).thenReturn(0);
    Mockito.when(mockDataTablesInput.getLength()).thenReturn(10);

    // When
    ItemDto.Search search = new ItemDto.Search();
    // search.setE4uIfSt("SR");
    DataTablesOutput<ItemDto.Response> result = itemService.getAllSendItems(search, mockDataTablesInput);

    // Then
    verify(mockDataTablesInput, times(2)).getStart();
    assertNotNull(result);

    log.debug("data : {}", result.getData());
  }

  @Test
  public void jsonViewTest() throws JsonProcessingException {
    // Given
    ItemDto.Response response = modelMapper.map(itemDum1, ItemDto.Response.class);
    List<Response> datas = new ArrayList<Response>();
    datas.add(response);

    ItemDto.ResponseWrapper responseWrapper = new ItemDto.ResponseWrapper();
    responseWrapper.setDatas(datas);

    // When
    String result1 =
        objectMapper.writerWithView(ItemDto.Views.ApiView.class).writeValueAsString(response);

    String result2 =
        objectMapper.writerWithView(ItemDto.Views.PublicView.class).writeValueAsString(response);

    String result3 = objectMapper.writerWithView(ItemDto.Views.ApiView.class)
        .writeValueAsString(responseWrapper);

    String result4 = objectMapper.writerWithView(ItemDto.Views.PublicView.class)
        .writeValueAsString(responseWrapper);
    
    // Then
    log.debug("result1 : {}", result1);
    log.debug("result2 : {}", result2);
    log.debug("result3 : {}", result3);
    log.debug("result4 : {}", result4);
    assertThat(result1, hasNoJsonPath("$.useYn"));
    assertThat(result2, hasJsonPath("$.useYn"));
    assertThat(result3, hasNoJsonPath("$.useYn"));
    assertThat(result4, hasJsonPath("$.datas[*].useYn"));
    assertThat(result4, hasJsonPath("$.datas[*].e4uItemCd"));
  }

}
