package kr.co.inogard.enio.agent.mappers.rfq;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import kr.co.inogard.enio.agent.domain.rfq.RfqSrv;

@Mapper
public interface RfqSrvMapper {

	List<RfqSrv> findAllByRfqNoAndItemSeq(@Param("rfqNo") String rfqNo, @Param("itemSeq") String itemSeq);

	void add(RfqSrv rfqSrv);
	
	void delRfqService(@Param("rfqNo") String rfqNo);

}
