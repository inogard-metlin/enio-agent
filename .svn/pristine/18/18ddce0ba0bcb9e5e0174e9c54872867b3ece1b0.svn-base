package kr.co.inogard.enio.agent.mappers.rfq;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.inogard.enio.agent.domain.rfq.RfqDegree;

@Mapper
public interface RfqDegreeMapper {
  
  List<RfqDegree> findAllByRfqNo(String rfqNo);
  
  void add(RfqDegree rfqDegree);

  void delRfqdegree(@Param("rfqNo") String rfqNo);
}
