package kr.co.inogard.enio.agent.mappers.event;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import kr.co.inogard.enio.agent.domain.event.CmmEvent;
import kr.co.inogard.enio.agent.domain.event.CmmEventDto;

@Mapper
public interface EventMapper {

  CmmEvent findByEvtNo(String evtNo);

  List<CmmEvent> findAll(@Param("search") CmmEventDto.Search search, @Param("pageable") Pageable pageable);

  void add(CmmEvent event);

  void updateReq(CmmEvent event);

  void updateRes(CmmEvent event);
  
  Long count(String agtCd);

}
