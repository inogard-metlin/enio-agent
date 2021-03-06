package kr.co.inogard.enio.agent.mappers.po;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import kr.co.inogard.enio.agent.domain.po.PoSrv;

@Mapper
public interface PoSrvMapper {

  List<PoSrv> findAllByPoNoAndItemSeq(@Param("poNo") String poNo,
      @Param("itemSeq") String itemSeq);
  
  public void add(PoSrv poSrv);

  void delPoService(@Param("poNo") String poNo);
}
