package kr.co.inogard.enio.agent.mappers.po;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.inogard.enio.agent.domain.po.PoItem;

@Mapper
public interface PoItemMapper {
  
  List<PoItem> findAllByPoNo(String poNo);

  public void add(PoItem poItem);

  void delPoItem(@Param("poNo") String poNo);
}
