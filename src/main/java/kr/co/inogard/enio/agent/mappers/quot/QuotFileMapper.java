package kr.co.inogard.enio.agent.mappers.quot;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import kr.co.inogard.enio.agent.domain.quot.QuotFile;

@Mapper
public interface QuotFileMapper {

  List<QuotFile> findByRfqNoAndQuotRevAndCusCd(@Param("rfqNo") String rfqNo,
      @Param("quotRev") String quotRev, @Param("cusCd") String cusCd);

  void addQuotFile(QuotFile quotFile);

  void delQuotFile(@Param("rfqNo") String rfqNo);
}
