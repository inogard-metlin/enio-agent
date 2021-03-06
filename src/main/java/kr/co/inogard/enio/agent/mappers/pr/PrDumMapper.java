package kr.co.inogard.enio.agent.mappers.pr;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import kr.co.inogard.enio.agent.domain.pr.PrDto;
import kr.co.inogard.enio.agent.domain.pr.PrDum;

@Mapper
public interface PrDumMapper {

  PrDum findByErpPrNo(String erpPrNo);

  List<PrDum> findAllNotSend();

  List<PrDum> findAllReqCancelNotSend();

  List<PrDum> findAll(@Param("search") PrDto.Search search, @Param("pageable") Pageable pageable);

  Long count(String agtCd);

  void add(PrDum prDum);

  void updateGmChrgrCd(PrDum prDum);

  void updatePrChrgrCd(PrDum prDum);

  void updateGrChrgrCd(PrDum prDum);

  void updateChkChrgrCd(PrDum prDum);

  void updateE4uPrNo(PrDum prDum);

  void updateE4uIfSt(PrDum prDum);

  void updateCnclFlag(PrDum prDum);

  void deleteByErpPrNo(String erpPrNo);

}
