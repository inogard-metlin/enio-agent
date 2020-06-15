package kr.co.inogard.enio.agent.mappers.agent;

import org.apache.ibatis.annotations.Mapper;

import kr.co.inogard.enio.agent.domain.agent.Agent;

@Mapper
public interface AgentMapper {
  Agent findOne(String agtCd);
}
