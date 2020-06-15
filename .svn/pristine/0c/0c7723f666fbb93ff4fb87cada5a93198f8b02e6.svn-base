package kr.co.inogard.enio.agent.rfq;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import kr.co.inogard.enio.agent.domain.rfq.RfqDto;
import kr.co.inogard.enio.agent.domain.rfq.RfqItemDto;

public class RfqTestFixture {
  public static RfqDto.Create getRfqCreateFixture() throws ParseException {
    RfqDto.Create createDto = new RfqDto.Create();

    createDto.setRfqNo("A019M202017091500004");
    createDto.setTtl("안녕하세요1차테스트");
    createDto.setQuotRev("00");

    RfqItemDto.Create rfqItemDto = new RfqItemDto.Create();
    rfqItemDto.setRfqNo("A019M202017091500004");
    rfqItemDto.setItemSeq("00001");
    rfqItemDto.setPrNo("01234567890123456789");
    rfqItemDto.setPrItemSeq("00001");
    rfqItemDto.setQty(new BigDecimal("1"));

    List<RfqItemDto.Create> rfqItemList = new ArrayList<RfqItemDto.Create>();
    rfqItemList.add(rfqItemDto);
    createDto.setRfqItemList(rfqItemList);

    return createDto;
  }
}
