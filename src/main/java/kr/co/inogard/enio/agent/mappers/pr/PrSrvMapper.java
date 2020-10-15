package kr.co.inogard.enio.agent.mappers.pr;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import kr.co.inogard.enio.agent.domain.pr.PrSrv;

@Mapper
public interface PrSrvMapper {

  List<PrSrv> findAllByPrNoAndItemSeq(@Param("prNo") String prNo, @Param("itemSeq") String itemSeq);

  void addFromDummy(PrSrv prSrv);

  void addByPrebid(PrSrv prSrv);

  void delPrService(@Param("prNo") String prNo);
}
