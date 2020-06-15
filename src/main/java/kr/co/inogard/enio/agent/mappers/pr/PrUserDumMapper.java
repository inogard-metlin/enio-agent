package kr.co.inogard.enio.agent.mappers.pr;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.inogard.enio.agent.domain.pr.PrUserDum;

@Mapper
public interface PrUserDumMapper {

  List<PrUserDum> findAllByErpPrNo(String erpPrNo);

  List<PrUserDum> findAllByErpPrNoAgtCd(@Param("erpPrNo") String erpPrNo, @Param("agtCd") String agtCd);
  
  void add(PrUserDum prUser);

  void deleteByErpPrNo(String erpPrNo);

}
