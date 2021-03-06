package kr.co.inogard.enio.agent.mappers.quot;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import kr.co.inogard.enio.agent.domain.quot.Quot;
import kr.co.inogard.enio.agent.domain.quot.QuotSrv;

@Mapper
public interface QuotSrvMapper {

  List<Quot> findByRfqNoAndQuotRevAndCusCdAndItemSeq(@Param("rfqNo") String rfqNo,
      @Param("quotRev") String quotRev, @Param("cusCd") String cusCd,
      @Param("itemSeq") String itemSeq);

  void add(QuotSrv quotSrv);
  
  void delQuotService(@Param("rfqNo") String rfqNo);

}
