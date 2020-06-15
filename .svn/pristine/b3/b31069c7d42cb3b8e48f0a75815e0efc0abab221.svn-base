package kr.co.inogard.enio.agent.mappers.dept;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.inogard.enio.agent.domain.dept.DeptDum;
import kr.co.inogard.enio.agent.domain.item.ItemDum;

@Mapper
public interface DeptDumMapper {
	void add(DeptDum deptDum);

	DeptDum findByErpDeptCd(String erpDeptCd);

	DeptDum findByDeptNm(@Param("deptNm") String deptNm, @Param("agtCd") String agtCd);

	List<DeptDum> findAllNotSend();

	void updateE4uDeptCd(DeptDum deptDum);
	
	void updateE4uIfSt(DeptDum deptDum);
}