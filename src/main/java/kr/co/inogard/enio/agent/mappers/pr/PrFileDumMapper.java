package kr.co.inogard.enio.agent.mappers.pr;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import kr.co.inogard.enio.agent.domain.pr.PrFileDum;

@Mapper
public interface PrFileDumMapper {

  List<PrFileDum> findAllByErpPrNo(String erpPrNo);

  PrFileDum findByErpPrNoAndFileSeq(@Param("erpPrNo") String erpPrNo,
      @Param("fileSeq") String fileSeq);

  void add(PrFileDum prFileDum);

  void deleteByErpPrNo(String erpPrNo);

}
