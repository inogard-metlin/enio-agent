package kr.co.inogard.enio.agent.mappers.pr;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import kr.co.inogard.enio.agent.domain.pr.Pr;
import kr.co.inogard.enio.agent.domain.pr.PrDto;

@Mapper
public interface PrMapper {

  Pr findByPrNo(String prNo);
  
  List<Pr> findAll(@Param("search") PrDto.Search search, @Param("pageable") Pageable pageable);

  Long count(String agtCd);
  
  String findPrNoFromRfqNo(String rfqNo);

  String findPrNoFromPoNo(String poNo);

  void addFromDummy(Pr pr);

  void addByPrebid(Pr pr);

  void updateAllChrgrCdByPrebid(Pr pr);

  void updateCallSyncToErp(Map<String, String> info);

  void delPr(@Param("prNo") String prNo);
}
