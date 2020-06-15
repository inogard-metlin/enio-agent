package kr.co.inogard.enio.agent.mappers.cus;

import org.apache.ibatis.annotations.Mapper;

import kr.co.inogard.enio.agent.domain.cus.Customer;

@Mapper
public interface CusMapper {
	void add(Customer cus);
	void update(Customer cus);
	Customer findByCusCd(String cusCd);
	
}
