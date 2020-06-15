package kr.co.inogard.enio.agent.mappers.pr;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import kr.co.inogard.enio.agent.domain.pr.PrItem;

@Mapper
public interface PrItemMapper {

  List<PrItem> findAllByPrNo(String prNo);

  void addFromDummy(PrItem prItem);

  void addByPrebid(PrItem prItem);

  void updateItemCdByPrebid(PrItem prItem);

}
