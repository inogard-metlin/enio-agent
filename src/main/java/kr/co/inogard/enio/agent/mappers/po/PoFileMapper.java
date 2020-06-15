package kr.co.inogard.enio.agent.mappers.po;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import kr.co.inogard.enio.agent.domain.po.PoFile;

@Mapper
public interface PoFileMapper {

  List<PoFile> findAllByPoNo(String poNo);

  PoFile findByPoNoAndFileSeq(@Param("poNo") String prNo, @Param("fileSeq") String fileSeq);

  public void add(PoFile poFile);

  void delPoFile(@Param("poNo") String poNo);
}
