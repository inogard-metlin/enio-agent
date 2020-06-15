package kr.co.inogard.enio.agent.mappers.rfq;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import kr.co.inogard.enio.agent.domain.rfq.Rfq;
import kr.co.inogard.enio.agent.domain.rfq.RfqDto;

@Mapper
public interface RfqMapper {

  Rfq findByRfqNo(String rfqNo);

  List<Rfq> findAll(@Param("search") RfqDto.Search search, @Param("pageable") Pageable pageable);

  Long count(String agtCd);

  void add(Rfq rfq);

  void updateRfqNoInPrItem(String rfqNo);

  void updateErpRfqNo(Rfq rfq);

  void updateCallSyncToErp(Map<String, String> info);

  void delRfq(@Param("rfqNo") String rfqNo);
}
