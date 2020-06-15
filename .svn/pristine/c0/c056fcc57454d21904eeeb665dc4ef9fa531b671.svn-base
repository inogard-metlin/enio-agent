package kr.co.inogard.enio.agent.mappers.quot;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import kr.co.inogard.enio.agent.domain.quot.QuotItem;

@Mapper
public interface QuotItemMapper {

  List<QuotItem> findByRfqNoAndQuotRevAndCusCd(@Param("rfqNo") String rfqNo,
      @Param("quotRev") String quotRev, @Param("cusCd") String cusCd);
  
  void add(QuotItem quotItem);

  void delQuotItem(@Param("rfqNo") String rfqNo);
}
