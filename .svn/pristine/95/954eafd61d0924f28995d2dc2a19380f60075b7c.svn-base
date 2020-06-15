package kr.co.inogard.enio.agent.mappers.rfq;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.inogard.enio.agent.domain.rfq.RfqCus;

@Mapper
public interface RfqCusMapper {
  
  List<RfqCus> findAllByRfqNo(String rfqNo);
  
  void add(RfqCus rfqCus);

	void delRfqcus(@Param("rfqNo") String rfqNo);
}
