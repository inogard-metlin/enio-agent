package kr.co.inogard.enio.agent.mappers.pr;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import kr.co.inogard.enio.agent.domain.pr.PrItemDum;

@Mapper
public interface PrItemDumMapper {

  List<PrItemDum> findAllByErpPrNo(String erpPrNo);

  void add(PrItemDum prItemDum);

  void deleteByErpPrNo(String erpPrNo);

}
