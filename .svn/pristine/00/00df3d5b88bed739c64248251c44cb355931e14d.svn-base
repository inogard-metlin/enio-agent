package kr.co.inogard.enio.agent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.codec.Base64;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import kr.co.inogard.enio.agent.commons.constant.SendStatus;
import kr.co.inogard.enio.agent.domain.pr.PrDto;
import kr.co.inogard.enio.agent.domain.pr.PrDum;
import kr.co.inogard.enio.agent.domain.pr.PrItemDum;
import kr.co.inogard.enio.agent.domain.pr.PrSrvDum;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleTest {
  
  
    public void getEnd(int start, int size) {
      int end = (start/size + 1) * size;
      log.debug("end : {}", end);
    }
  
	@Test
	public void test() throws JsonParseException, JsonMappingException, IOException {
	  getEnd(0, 10);
	  getEnd(10, 10);
	  getEnd(20, 10);
	  
	  getEnd(0, 50);
      getEnd(50, 50);
      getEnd(100, 50);
	  
	  /*int a = 1;
	  Long b = 0L;
	  
	  b = (long) a;
	  
	  
	  System.out.println(b.SIZE);
	  
	  
	  ArrayList<String> list = new ArrayList<String>(Arrays.asList("a", "b", "c", "d"));
	  Iterator<String> iter = list.iterator();
	  while (iter.hasNext()) {
	      String s = iter.next();
	   
	      if (s.equals("a")) {
	          iter.remove();
	      }
	  }
	  
	  for (String s : list) {
	    System.out.println(s);
	}
	  
	  
	  
		
	  System.out.println(SendStatus.codeOf("SW"));
	  
	  
	  try {
        if (1 == 1) {
          throw new Exception("!@#!@");
        }
      } catch (Exception e) {
        log.error("1", e);
        log.error("1 : {}", "5", e);
      }
	  
	  
	  
		ModelMapper modelMapper = new ModelMapper();
		PrDum prDum = new PrDum();
		prDum.setTtl("제목");
		
		
		List<PrItemDum> itemList = new ArrayList<PrItemDum>();
		PrItemDum prItemDum = new PrItemDum();
		prItemDum.setItemNm("Item");
		
		List<PrSrvDum> srvList = new ArrayList<PrSrvDum>();
		PrSrvDum prSrvDum = new PrSrvDum();
		prSrvDum.setSrvNm("SrvNm");
		srvList.add(prSrvDum);
		
		prItemDum.setSrvList(srvList);
		itemList.add(prItemDum);
		prDum.setItemList(itemList);
		PrDto.Create createPr = modelMapper.map(prDum, PrDto.Create.class);
		System.out.println(createPr.getItemList().size());
		System.out.println(createPr.getItemList().get(0).getItemNm());
		System.out.println(createPr.getItemList().get(0).getSrvList().size());
		System.out.println(createPr.getItemList().get(0).getSrvList().get(0).getSrvNm());
		
		
		
		String passwd = new String(Base64.decode("RU5JT1MwMDM3LTIwMTcwOA==".getBytes()), "UTF-8");
		System.out.println(passwd);
		*/
		
//		List<String> list = new ArrayList<String>();
//		list.add("1");
//		list.add("2");
//		list.add("3");
//		
//		final Iterator<String> listIter = list.iterator();
//		while(listIter.hasNext()) {
//			String str = listIter.next();
//			System.out.println(str);
//			if ("2".equals(str)) {
//				listIter.remove();
//			}
//		}
//		
//		for (String str : list) {
//			System.out.println(str);
//		}
		

//		System.out.println("===========디버깅 시작했다~================");
//		System.out.print("file:" + (new Throwable()).getStackTrace()[0].getClassName() + "  line");
//		System.out.println((new Throwable()).getStackTrace()[0].getLineNumber());
//		System.out.println("===========디버깅 끝났다~================");
//
//		List<String> list = null;
//
//		for (String a : list) {
//			System.out.println(a);
//		}
//
//		ErrorResponse a = new ErrorResponse();
//
//		RsltCd.valueOf("1");
//
//		String multipartHeaders = "Content-Disposition: form-data; name=\"data\"\r\nContent-Type: application/enio-json;charset=UTF-8\r\n\r\n";
//
//		Map<String, String> headers = new HashMap<String, String>();
//		if (StringUtils.hasText(multipartHeaders)) {
//			String[] headersArr = multipartHeaders.split("\r\n");
//			for (String header : headersArr) {
//				String[] arr2 = header.split(":");
//				if (arr2.length > 1) {
//					String k = header.split(":")[0];
//					String v = header.split(":")[1];
//
//					if (StringUtils.hasText(k)) {
//						k = k.trim();
//					}
//
//					if (StringUtils.hasText(v)) {
//						v = v.trim();
//					}
//
//					headers.put(k, v);
//				}
//			}
//		}
//		System.out.println(headers);
	}

}
