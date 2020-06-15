package kr.co.inogard.enio.agent.mappers.rtlog;

import org.apache.ibatis.annotations.Mapper;
import kr.co.inogard.enio.agent.domain.rtlog.RtLog;

@Mapper
public interface RtLogMapper {

	void add(RtLog rtLog);
	void updateSolved(RtLog rtLog);
	RtLog findByLogNo(String logNo);	
}
