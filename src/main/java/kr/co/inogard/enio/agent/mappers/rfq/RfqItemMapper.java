package kr.co.inogard.enio.agent.mappers.rfq;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.inogard.enio.agent.domain.rfq.RfqItem;

@Mapper
public interface RfqItemMapper {
  
  List<RfqItem> findAllByRfqNo(String rfqNo);
  
  void add(RfqItem rfqItem);

	void delRfqItem(@Param("rfqNo") String rfqNo);
}
