package kr.co.inogard.enio.agent.file;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import kr.co.inogard.enio.agent.Application;
import kr.co.inogard.enio.agent.domain.file.CmmFile;
import kr.co.inogard.enio.agent.mappers.file.FileMapper;
import kr.co.inogard.enio.agent.service.file.FileService;
import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
/*@Transactional */
@Slf4j
public class FileServiceTest {
	
	@Autowired
	private FileMapper fileMapper;
	
	@Autowired
	private FileService fileService;
	
	
	@Test
	public void createTest() {
		
		File uploadedFile=new File("d:/a.txt");
		String docType="22";		
		CmmFile file=fileService.store(uploadedFile, docType);
		
		log.debug("new-file-no:{}",file.getFileNo());
		log.debug("svr-file-path : {}", file.getSvrFilePath());
		log.debug("svr-file-nm : {}",file.getSvrFileNm());
	}
}
