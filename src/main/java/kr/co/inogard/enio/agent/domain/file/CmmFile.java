package kr.co.inogard.enio.agent.domain.file;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class CmmFile {
	private String fileNo;
	private String cliFileNm;
	private String svrFileNm;
	private String svrFilePath;
	private BigDecimal fileSz;
	private Date regDt;
	private String docType;
}
