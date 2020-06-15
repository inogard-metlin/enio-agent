package kr.co.inogard.enio.agent.pr;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import kr.co.inogard.enio.agent.Application;
import kr.co.inogard.enio.agent.commons.constant.SendStatus;
import kr.co.inogard.enio.agent.domain.pr.Pr;
import kr.co.inogard.enio.agent.domain.pr.PrDum;
import kr.co.inogard.enio.agent.mappers.pr.PrDumMapper;
import kr.co.inogard.enio.agent.mappers.pr.PrMapper;
import kr.co.inogard.enio.agent.scheduling.tasks.PrTaskService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
public class PrTaskServiceTest {

  @Autowired
  private PrDumMapper prDumMapper;

  @Autowired
  private PrMapper prMapper;

  @Autowired
  private PrTaskService prTaskService;

  @Test
  @Sql
  public void sendPr() {
    // Given
    // When
    prTaskService.execute();

    // Then
    verifySendPr(prDumMapper.findByErpPrNo("ERPPR9999010100001"));
    verifySendPr(prDumMapper.findByErpPrNo("ERPPR9999010100002"));
    verifySendPr(prDumMapper.findByErpPrNo("ERPPR9999010100003"));
    verifySendPr(prDumMapper.findByErpPrNo("ERPPR9999010100004"));
  }

  private void verifySendPr(PrDum prDum) {
    Pr pr = prMapper.findByPrNo(prDum.getE4uPrNo());
    assertThat(prDum.getE4uIfSt(), is(SendStatus.SEND_COMPLETE.getCode()));
    assertThat(prDum.getErpPrNo(), is(pr.getErpPrNo()));
    assertThat(prDum.getTtl(), is(pr.getTtl()));
  }
}
