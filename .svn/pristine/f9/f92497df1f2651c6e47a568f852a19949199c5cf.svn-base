package kr.co.inogard.enio.agent.quot;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import kr.co.inogard.enio.agent.domain.quot.QuotDto;
import kr.co.inogard.enio.agent.domain.quot.QuotFileDto;
import kr.co.inogard.enio.agent.domain.quot.QuotItemDto;
import kr.co.inogard.enio.agent.domain.quot.QuotSrvDto;

public class QuotTestFixture {
	public static QuotDto.Create getQuotCreateFixture() throws ParseException{
//		SimpleDateFormat formatterYMD = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat formatterYMDHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");
		
		QuotDto.Create quotDto = new QuotDto.Create();
		quotDto.setRfqNo("A0004202009060300001");
		quotDto.setQuotRev("00");
		quotDto.setCusCd("1000201506");
		quotDto.setAmt(new BigDecimal("3263636.00"));
		quotDto.setVatAmt(new BigDecimal("326364.00"));
		quotDto.setTaxTypeCd("A1000");
		quotDto.setCrcyCd("KRW");
		quotDto.setCrcyRate(new BigDecimal("1.00"));
		quotDto.setQuotSbmtDt(formatterYMDHHmmss.parse("20090608160328"));

		List <QuotFileDto> quotFileDtoList = new ArrayList<QuotFileDto>();
		QuotFileDto quotfileDto1 = new QuotFileDto();
		QuotFileDto quotfileDto2 = new QuotFileDto();
		
		quotfileDto1.setRfqNo("A0004202009060300001");
		quotfileDto1.setQuotRev("00");
		quotfileDto1.setCusCd("1000201506");
		quotfileDto1.setFileSeq("00001");
		quotfileDto1.setFileNo("22201404150000000001");
//		quotfileDto1.setCliFileNm("ClientFileNm1");
//		quotfileDto1.setSvrFileNm("SvrFileNm1");
//		quotfileDto1.setSvrFilePath("SvrFilePath1");

		quotfileDto2.setRfqNo("A0004202009060300001");
		quotfileDto2.setQuotRev("00");
		quotfileDto2.setCusCd("1000201506");
		quotfileDto2.setFileSeq("00002");
		quotfileDto2.setFileNo("22201404150000000002");
//		quotfileDto1.setCliFileNm("ClientFileNm2");
//		quotfileDto1.setSvrFileNm("SvrFileNm2");
//		quotfileDto1.setSvrFilePath("SvrFilePath2");

		quotFileDtoList.add(quotfileDto1);
		quotFileDtoList.add(quotfileDto2);
		
//		quotDto.setQuotFileList(quotFileDtoList);
		
		List <QuotItemDto> quotItemDtoList = new ArrayList<QuotItemDto>();
		QuotItemDto quotItemDto1 = new QuotItemDto();
		QuotItemDto quotItemDto2 = new QuotItemDto();
		QuotItemDto quotItemDto3 = new QuotItemDto();

		quotItemDto1.setRfqNo("A0004202009060300001");
		quotItemDto1.setQuotRev("00");
		quotItemDto1.setCusCd("1000201506");
		quotItemDto1.setItemSeq("00001");
		quotItemDto1.setRfqItemSeq("00001");
		quotItemDto1.setQty(new BigDecimal("1.00"));
		quotItemDto1.setUnitPrc(new BigDecimal("1.00"));
		quotItemDto1.setAmt(new BigDecimal("1.00"));

		quotItemDto2.setRfqNo("A0004202009060300001");
		quotItemDto2.setQuotRev("00");
		quotItemDto2.setCusCd("1000201506");
		quotItemDto2.setItemSeq("00002");
		quotItemDto2.setRfqItemSeq("00002");
		quotItemDto2.setQty(new BigDecimal("2.00"));
		quotItemDto2.setUnitPrc(new BigDecimal("2.00"));
		quotItemDto2.setAmt(new BigDecimal("2.00"));

		quotItemDto3.setRfqNo("A0004202009060300001");
		quotItemDto3.setQuotRev("00");
		quotItemDto3.setCusCd("1000201506");
		quotItemDto3.setItemSeq("00003");
		quotItemDto3.setRfqItemSeq("00003");
		quotItemDto3.setQty(new BigDecimal("3.00"));
		quotItemDto3.setUnitPrc(new BigDecimal("3.00"));
		quotItemDto3.setAmt(new BigDecimal("3.00"));

		List <QuotSrvDto> quotSrvDtoList1 = new ArrayList<QuotSrvDto>();
		List <QuotSrvDto> quotSrvDtoList2 = new ArrayList<QuotSrvDto>();
		List <QuotSrvDto> quotSrvDtoList3 = new ArrayList<QuotSrvDto>();
		QuotSrvDto quotSrvDto1 = new QuotSrvDto();
		QuotSrvDto quotSrvDto2 = new QuotSrvDto();
		QuotSrvDto quotSrvDto3 = new QuotSrvDto();
		
		quotSrvDto1.setRfqNo("A0004202009060300001");
		quotSrvDto1.setQuotRev("00");
		quotSrvDto1.setCusCd("1000201506");
		quotSrvDto1.setItemSeq("00001");
		quotSrvDto1.setSrvSeq("00001");
		quotSrvDto1.setRfqItemSeq("00001");
		quotSrvDto1.setRfqSrvSeq("00001");
		quotSrvDto1.setQty(new BigDecimal("1.00"));
		quotSrvDto1.setUnitPrc(new BigDecimal("111111.00"));
		quotSrvDto1.setAmt(new BigDecimal("111111.00"));
		
		quotSrvDto2.setRfqNo("A0004202009060300001");
		quotSrvDto2.setQuotRev("00");
		quotSrvDto2.setCusCd("1000201506");
		quotSrvDto2.setItemSeq("00002");
		quotSrvDto2.setSrvSeq("00002");
		quotSrvDto2.setRfqItemSeq("00002");
		quotSrvDto2.setRfqSrvSeq("00002");
		quotSrvDto2.setQty(new BigDecimal("1.00"));
		quotSrvDto2.setUnitPrc(new BigDecimal("222222.00"));
		quotSrvDto2.setAmt(new BigDecimal("222222.00"));
		
		quotSrvDto3.setRfqNo("A0004202009060300001");
		quotSrvDto3.setQuotRev("00");
		quotSrvDto3.setCusCd("1000201506");
		quotSrvDto3.setItemSeq("00003");
		quotSrvDto3.setSrvSeq("00003");
		quotSrvDto3.setRfqItemSeq("00003");
		quotSrvDto3.setRfqSrvSeq("00003");
		quotSrvDto3.setQty(new BigDecimal("1.00"));
		quotSrvDto3.setUnitPrc(new BigDecimal("333333.00"));
		quotSrvDto3.setAmt(new BigDecimal("333333.00"));
		
		quotSrvDtoList1.add(quotSrvDto1);
		quotSrvDtoList2.add(quotSrvDto2);
		quotSrvDtoList3.add(quotSrvDto3);
		
		quotItemDto1.setQuotSrvList(quotSrvDtoList1);
		quotItemDto2.setQuotSrvList(quotSrvDtoList2);
		quotItemDto3.setQuotSrvList(quotSrvDtoList3);
		
		quotItemDtoList.add(quotItemDto1);
		quotItemDtoList.add(quotItemDto2);
		quotItemDtoList.add(quotItemDto3);
		
		quotDto.setQuotItemList(quotItemDtoList);
		
		return quotDto;
	}
}
