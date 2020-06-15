package kr.co.inogard.enio.agent.domain.user;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
	private String userCd;
	private String userNm;
	private String userTel;
	private String userEmail;
	private String deptCd;
	private String userSms;
	private Date regDt;
	private Date e4uIfDt;
	private String erpUserCd;
	private String orgCd;
	private String cusCd;
}
