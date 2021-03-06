package kr.co.inogard.enio.agent.domain.user;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDum {
	private String erpUserCd;
	private String userNm;
	private String userTel;
	private String userEmail;
	private String erpDeptCd;
	private String userSms;
	private Date regDt;
	private Date e4uIfDt;
	private String e4uUserCd;
	private String orgCd;
	private String cusCd;
	private String e4uIfSt;
	private String agtCd;
}
