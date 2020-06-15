package kr.co.inogard.enio.agent.pr;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import kr.co.inogard.enio.agent.Application;
import kr.co.inogard.enio.agent.mappers.pr.PrFileDumMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
public class PrFileDumMapperTest {
  
  @Autowired
  private PrFileDumMapper prFileDumMapper;
  
  @Test
  public void findAllByErpPrNo() {
    prFileDumMapper.findAllByErpPrNo("111");
  }
  
  @Test
  public void findByErpPrNoAndFileSeq() {
    prFileDumMapper.findByErpPrNoAndFileSeq("111", "222");
  }

}
