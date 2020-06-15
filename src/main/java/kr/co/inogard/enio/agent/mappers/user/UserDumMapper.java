package kr.co.inogard.enio.agent.mappers.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.inogard.enio.agent.domain.dept.DeptDum;
import kr.co.inogard.enio.agent.domain.user.UserDum;

@Mapper
public interface UserDumMapper {
	void add(UserDum userDum);

	UserDum findByErpUserCd(String eprUserCd);

	UserDum findByUserNmAndUserEmail(UserDum userDum);

	List<UserDum> findAllNotSend();

	void updateE4uUserCd(UserDum userDum);
	
	void updateE4uIfSt(UserDum userDum);	
}