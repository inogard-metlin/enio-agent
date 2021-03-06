package kr.co.inogard.enio.agent.agent;

import static org.junit.Assert.assertTrue;

import java.util.Base64;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import kr.co.inogard.enio.agent.Application;
import kr.co.inogard.enio.agent.domain.agent.AgentConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
public class AgentTest {

  @Autowired
  private AgentConfig agentConfig;
  
  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder; 
  
  @Test
  public void getAgent() {
    log.debug("agtCd : {}", agentConfig.getAgent().getAgtCd());
  }

  @Test
  public void makePassword() {
	  //Given
	  //CharSequence sourcePassword = "ENIOA01BU-201809";		//울산
	  //CharSequence sourcePassword = "ENIOD0001-201807";		//덕성
	  //CharSequence sourcePassword = "ENIOS0037-201906";	 	//성신
	  //CharSequence sourcePassword = "ENIOS0001-201902";		//이노가드
	  //CharSequence sourcePassword = "ENIOS0020-201906";		//호서
	  //CharSequence sourcePassword = "ENIOS0014-201911";	 	//상명
	  //CharSequence sourcePassword = "ENIOC0001-202005";	 	//중앙
	  CharSequence sourcePassword = "ENIOA013Q-202005";	 		//한양사이버대

	  //when
	  String encodedPassword = bCryptPasswordEncoder.encode(sourcePassword);
	  
	  //then
	  System.out.println("암호: " + sourcePassword);
	  System.out.println("인코딩 암호: " + encodedPassword);
	  
	  System.out.println(BCrypt.checkpw(sourcePassword.toString(), encodedPassword));
  }

  @Test
  public void makeLicenseKey() {
	  //Given
	  //String sourceLicenseKey = "ENIOS0037-201906";   //호서
	  //String sourceLicenseKey = "ENIOS0014-201911";   //상명
	  //String sourceLicenseKey = "ENIOC0001-202005";    	//중앙
	  String sourceLicenseKey = "ENIOA013Q-202005";    	//한양사이버대

      //when
	  String encodedLicenseKey = Base64.getEncoder().encodeToString(sourceLicenseKey.getBytes());
	  String decodedLicenseKey = new String (Base64.getDecoder().decode(encodedLicenseKey.getBytes()));
	  
	  //then
	  System.out.println("소스 라이센스 키: " + sourceLicenseKey);
	  System.out.println("인코딩 라이센스 키: " + encodedLicenseKey);
	  System.out.println("디코딩 라이센스 키: " + decodedLicenseKey);
	  
	  assertTrue(sourceLicenseKey.equals(decodedLicenseKey) );
  }
  
}
