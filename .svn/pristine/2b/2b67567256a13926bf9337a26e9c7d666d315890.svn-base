package kr.co.inogard.enio.agent.mappers.pr;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import kr.co.inogard.enio.agent.domain.pr.PrSrvDum;

@Mapper
public interface PrSrvDumMapper {

  List<PrSrvDum> findAllByErpPrNoAndItemSeq(@Param("erpPrNo") String erpPrNo,
      @Param("itemSeq") String itemSeq);

  void deleteByErpPrNo(String erpPrNo);
}
