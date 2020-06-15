package kr.co.inogard.enio.agent.mappers.quot;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import kr.co.inogard.enio.agent.domain.quot.Quot;

@Mapper
public interface QuotMapper {

  Quot findByRfqNoAndQuotRevAndCusCd(@Param("rfqNo") String rfqNo, @Param("quotRev") String quotRev,
      @Param("cusCd") String cusCd);

  List<Quot> findAllByRfqNoAndQuotRev(@Param("rfqNo") String rfqNo,
      @Param("quotRev") String quotRev);

  void add(Quot quot);

  void delQuot(@Param("rfqNo") String rfqNo);
}
