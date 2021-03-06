package kr.co.inogard.enio.agent.quot;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.co.inogard.enio.agent.Application;
import kr.co.inogard.enio.agent.domain.ftp.FtpFileDto;
import kr.co.inogard.enio.agent.domain.quot.Quot;
import kr.co.inogard.enio.agent.domain.quot.QuotDto;
import kr.co.inogard.enio.agent.domain.quot.QuotFile;
import kr.co.inogard.enio.agent.domain.quot.QuotItem;
import kr.co.inogard.enio.agent.domain.quot.QuotSrv;
import kr.co.inogard.enio.agent.mappers.quot.QuotFileMapper;
import kr.co.inogard.enio.agent.mappers.quot.QuotItemMapper;
import kr.co.inogard.enio.agent.mappers.quot.QuotMapper;
import kr.co.inogard.enio.agent.mappers.quot.QuotSrvMapper;
import kr.co.inogard.enio.agent.service.quot.QuotService;
import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
@Slf4j
public class QuotServiceTest {

	@Autowired
	private QuotService quotService;
	
	@Autowired
	private QuotMapper quotMapper;
	
	@Autowired
	private QuotFileMapper quotFileMapper;
	
	@Autowired
	private QuotItemMapper quotItemMapper;
	
	@Autowired
	private QuotSrvMapper quotSrvMapper;
	
	@Autowired
	private ModelMapper modelMapper;

	@Test
	public void create() throws ParseException{
		QuotDto.Create datas = QuotTestFixture.getQuotCreateFixture();
		List<MultipartFile> files = new ArrayList<MultipartFile>();	
		Quot quot = quotService.create(datas, files);
		log.debug("===========> QUOT CREATE : {}", quot.getRfqNo());
	}
	
	@Test
	public void createQuot() throws ParseException{
		Quot quot = modelMapper.map(QuotTestFixture.getQuotCreateFixture(), Quot.class);
		quotMapper.add(quot);
	}
	
	@Test
	public void createQuotFile() throws ParseException{
		Quot quot = modelMapper.map(QuotTestFixture.getQuotCreateFixture(), Quot.class);
		
		List<MultipartFile> files = new ArrayList<MultipartFile>();
//		if(null != files && !files.isEmpty()) {
			try {
				List<FtpFileDto.Store> storeList = new ArrayList<FtpFileDto.Store>();
				for(MultipartFile multipartFile : files) {
					log.debug("## multipartFile.getName : {}", multipartFile.getName());
					log.debug("## multipartFile.getOriginalFilename : {}", multipartFile.getOriginalFilename());
					
				}
				
				
			}catch(Exception e) {
				e.printStackTrace();
			}
//		}
		
		
//		List<QuotFile> quotFileList = quot.getFileList();
//		for(QuotFile quotFile : quotFileList) {
//			quotFileMapper.addQuotFile(quotFile);
//		}
	}
	
	@Test
	public void createQuotItem() throws ParseException{
		Quot quot = modelMapper.map(QuotTestFixture.getQuotCreateFixture(), Quot.class);
		
		List<QuotItem> quotItemList = quot.getItemList();
		for(QuotItem quotItem : quotItemList) {
			quotItemMapper.add(quotItem);
		}
	}
	@Test
	public void createQuotServie() throws ParseException{
		Quot quot = modelMapper.map(QuotTestFixture.getQuotCreateFixture(), Quot.class);
		
		List<QuotItem> quotItemList = quot.getItemList();
		List<QuotSrv> quotSrvList = null;
		for(QuotItem quotItem : quotItemList) {
//			quotItemMapper.addQuotItem(quotItem);
			
			quotSrvList = quotItem.getSrvList();
			for(QuotSrv quotSrv : quotSrvList) {
				quotSrvMapper.add(quotSrv);
			}
		}
	}
}
