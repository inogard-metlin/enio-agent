package kr.co.inogard.enio.agent.mappers.pr;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import kr.co.inogard.enio.agent.domain.pr.PrFile;

@Mapper
public interface PrFileMapper {
  List<PrFile> findAllByPrNo(String prNo);

  PrFile findByPrNoAndFileSeq(@Param("prNo") String prNo, @Param("fileSeq") String fileSeq);

  void addFromDummy(PrFile prFile);

  void add(PrFile prFile);
}
