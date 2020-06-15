package kr.co.inogard.enio.agent.item;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
import kr.co.inogard.enio.agent.domain.item.ItemDto;
import kr.co.inogard.enio.agent.domain.item.ItemDum;
import kr.co.inogard.enio.agent.mappers.item.ItemDumMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
public class ItemDumMapperTest {

  @Autowired
  private ItemDumMapper itemDumMapper;

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
  public void add() {
    // Given
    // When
    itemDumMapper.add(itemDum1);

    // Then
    assertThat(itemDumMapper.findByErpItemCd(itemDum1.getErpItemCd()).getItemNm(),
        is(itemDum1.getItemNm()));
  }

  @Test
  public void getNotSend() {
    // Given
    // When
    itemDumMapper.add(itemDum1);

    // Then
    assertThat(itemDumMapper.findAllNotSend().size(), is(1));
  }

  @Test
  public void getAll() {
    // Given
    // When
    itemDumMapper.add(itemDum1);
    itemDumMapper.add(itemDum2);
    itemDumMapper.add(itemDum3);

    // Then
    ItemDto.Search search = new ItemDto.Search();
    search.setE4uIfSt("SR");
    assertThat(itemDumMapper.findAll(search, null).size(), is(3));
  }

  @Test
  public void getAllByDataTablesInput() {
    // Given
    DataTablesInput mockDataTablesInput = mock(DataTablesInput.class);
    Mockito.when(mockDataTablesInput.getStart()).thenReturn(0);
    Mockito.when(mockDataTablesInput.getLength()).thenReturn(10);

    // When
    itemDumMapper.add(itemDum1);
    itemDumMapper.add(itemDum2);
    itemDumMapper.add(itemDum3);

    // Then
    ItemDto.Search search = new ItemDto.Search();
    search.setE4uIfSt("SR");
    verify(mockDataTablesInput, times(2)).getStart();
    assertThat(
        itemDumMapper
            .findAll(search, new PageRequest(0, 20, Direction.fromString("desc"), "reg_dt")).size(),
        is(3));
  }

  @Test
  public void getAllByOrders() {
    // Given
    // When
    itemDumMapper.add(itemDum1);
    itemDumMapper.add(itemDum2);
    itemDumMapper.add(itemDum3);

    // Then
    ItemDto.Search search = new ItemDto.Search();
    search.setE4uIfSt("SR");
    assertThat(
        itemDumMapper
            .findAll(search, new PageRequest(0, 20, Direction.fromString("desc"), "reg_dt")).size(),
        is(3));
  }

}
