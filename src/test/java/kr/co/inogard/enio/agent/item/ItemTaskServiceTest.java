package kr.co.inogard.enio.agent.item;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import kr.co.inogard.enio.agent.Application;
import kr.co.inogard.enio.agent.commons.constant.SendStatus;
import kr.co.inogard.enio.agent.domain.item.Item;
import kr.co.inogard.enio.agent.domain.item.ItemDum;
import kr.co.inogard.enio.agent.mappers.item.ItemDumMapper;
import kr.co.inogard.enio.agent.mappers.item.ItemMapper;
import kr.co.inogard.enio.agent.scheduling.tasks.ItemTaskService;
import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
@Slf4j
public class ItemTaskServiceTest {

  @Autowired
  private ItemDumMapper itemDumMapper;

  @Autowired
  private ItemMapper itemMapper;

  @Autowired
  private ItemTaskService itemTaskService;

  @Test
  @Sql
  public void sendItem() {
    // Given
    // When
    itemTaskService.execute();

    
    // Then
    verifySendItem(itemDumMapper.findByErpItemCd("ITM00009999010100001"));
    verifySendItem(itemDumMapper.findByErpItemCd("ITM00009999010100002"));
    verifySendItem(itemDumMapper.findByErpItemCd("ITM00009999010100003"));
    verifySendItem(itemDumMapper.findByErpItemCd("ITM00009999010100004"));
  }

  private void verifySendItem(ItemDum itemDum) {
    Item item = itemMapper.findByItemCd(itemDum.getE4uItemCd());

    assertThat(itemDum.getE4uIfSt(), is(SendStatus.SEND_COMPLETE.getCode()));
    assertThat(itemDum.getErpItemCd(), is(item.getErpItemCd()));
    assertThat(itemDum.getItemNm(), is(item.getItemNm()));
  }
}