package kr.co.inogard.enio.agent.domain.file;

import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

public class CmmFileDto {

	@Data
	public static class Create {
		private String fileNo;
		@NotBlank
		private String cliFileNm;
		private String svrFileNm;
		private String svrFilePath;
		private Date regDt;
	}

}
