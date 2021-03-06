package kr.co.inogard.enio.agent.po;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import kr.co.inogard.enio.agent.domain.po.PoDto;
import kr.co.inogard.enio.agent.domain.po.PoFileDto;
import kr.co.inogard.enio.agent.domain.po.PoItemDto;
import kr.co.inogard.enio.agent.domain.po.PoSrvDto;

public class PoTestFixture {
  public static PoDto.Create getPoCreateFixture() throws ParseException {
    SimpleDateFormat formatterYMD = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat formatterYmdHHmmss = new SimpleDateFormat("yyyyMMdd");

    // String stringPoYmd = "2017-05-30 00:00:00.000";
    String stringPoYmd = "2017-05-30";
    String stringRegDt = "2017-05-30 15:56:26.047";

    SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    Date datePoYmd = sf1.parse(stringPoYmd);
    Date dateRegDt = sf2.parse(stringRegDt);

    System.out.println("datePoYmd.toString : " + datePoYmd.toString());
    System.out.println("datePoYmd.toString : " + datePoYmd.toString());
    System.out.println("datePoYmd : " + datePoYmd);
    System.out.println("datePoYmd : " + datePoYmd);

    PoDto.Create poDto = new PoDto.Create();
    poDto.setPoNo("A0190302017053000001");
    poDto.setTtl("[엄]첨단소재 사무용 책상 구입");
    poDto.setPoYmd(datePoYmd);
    poDto.setPoAmt(new BigDecimal("5800000.00"));
    poDto.setVatAmt(new BigDecimal("580000.00"));
    poDto.setPrTypecd("N");
    poDto.setTaxTypecd("A1000");
    poDto.setIoFlag("D");
    poDto.setRegDt(dateRegDt);
    poDto.setCusCd("1000210B57");
    poDto.setCrcyCd("KRW");
    poDto.setCrcyRate(new BigDecimal("1.00"));

    PoFileDto.Create poFileDto1 = new PoFileDto.Create();
    poFileDto1.setPoNo("A0190302017053000001");
    poFileDto1.setFileSeq("00001");
    poFileDto1.setFileNo("30201708310000000002");
    PoFileDto.Create poFileDto2 = new PoFileDto.Create();
    poFileDto2.setPoNo("A0190302017053000001");
    poFileDto2.setFileSeq("00002");
    poFileDto2.setFileNo("30201708310000000003");
    PoFileDto.Create poFileDto3 = new PoFileDto.Create();
    poFileDto3.setPoNo("A0190302017053000001");
    poFileDto3.setFileSeq("00003");
    poFileDto3.setFileNo("30201708310000000004");
    List<PoFileDto.Create> poFileDtoList = new ArrayList<PoFileDto.Create>();
    poFileDtoList.add(poFileDto1);
    poFileDtoList.add(poFileDto2);
    poFileDtoList.add(poFileDto3);

    PoItemDto.Create poItemDto = new PoItemDto.Create();
    poItemDto.setPoNo("A0190302017053000001");
    poItemDto.setItemSeq("00001");
    poItemDto.setPrNo("A0190102016020100001");
    poItemDto.setPrItemSeq("00001");
    poItemDto.setQty(new BigDecimal("1.00"));
    poItemDto.setUnitPrc(new BigDecimal("5800000.00"));
    poItemDto.setAmt(new BigDecimal("5800000.00"));

    PoSrvDto.Create poSrvDto1 = new PoSrvDto.Create();
    poSrvDto1.setPoNo("A0190302017053000001");
    poSrvDto1.setItemSeq("00001");
    poSrvDto1.setSrvSeq("00001");
    poSrvDto1.setPrNo("A0190102016020100001");
    poSrvDto1.setPrItemSeq("00001");
    poSrvDto1.setPrSrvSeq("00001");
    poSrvDto1.setQty(new BigDecimal("1.00"));
    poSrvDto1.setUnitPrc(new BigDecimal("3263636.00"));
    poSrvDto1.setAmt(new BigDecimal("3263636.00"));
    PoSrvDto.Create poSrvDto2 = new PoSrvDto.Create();
    poSrvDto2.setPoNo("A0190302017053000001");
    poSrvDto2.setItemSeq("00001");
    poSrvDto2.setSrvSeq("00002");
    poSrvDto2.setPrNo("A0190102016020100001");
    poSrvDto2.setPrItemSeq("00002");
    poSrvDto2.setPrSrvSeq("00002");
    poSrvDto2.setQty(new BigDecimal("1.00"));
    poSrvDto2.setUnitPrc(new BigDecimal("3263636.00"));
    poSrvDto2.setAmt(new BigDecimal("3263636.00"));

    List<PoSrvDto.Create> poSrvDtoList = new ArrayList<PoSrvDto.Create>();
    poSrvDtoList.add(poSrvDto1);
    poSrvDtoList.add(poSrvDto2);
    poItemDto.setPoSrv(poSrvDtoList);

    List<PoItemDto.Create> poItemDtoList = new ArrayList<PoItemDto.Create>();
    poItemDtoList.add(poItemDto);

   // poDto.setPoFile(poFileDtoList);
    poDto.setPoItem(poItemDtoList);
    return poDto;
  }
}
