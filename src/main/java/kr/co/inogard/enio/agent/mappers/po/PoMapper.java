package kr.co.inogard.enio.agent.mappers.po;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import kr.co.inogard.enio.agent.domain.po.Po;
import kr.co.inogard.enio.agent.domain.po.PoDto;

@Mapper
public interface PoMapper {

  Po findByPoNo(String poNo);
  
  List<Po> findAll(@Param("search") PoDto.Search search, @Param("pageable") Pageable pageable);
  
  Long count(String agtCd);
  
  void add(Po po);

  void updatePoNoInPrItem(String poNo);

  void updateCallSyncToErp(Map<String, String> info);

  void delPo(@Param("poNo") String poNo);
}
