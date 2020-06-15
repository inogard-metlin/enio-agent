package kr.co.inogard.enio.agent.pr;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;
import kr.co.inogard.enio.agent.Application;
import kr.co.inogard.enio.agent.commons.EnioContext;
import kr.co.inogard.enio.agent.commons.util.RestTemplateFactory;
import kr.co.inogard.enio.agent.domain.agent.Agent;
import kr.co.inogard.enio.agent.mappers.agent.AgentMapper;
import kr.co.inogard.enio.agent.scheduling.tasks.PrReqCancelTaskService;
import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
// @Transactional
@Slf4j
public class PrReqCancelTaskServiceTest {

  @Autowired
  private AgentMapper agentMapper;

  @Autowired
  private PrReqCancelTaskService prReqCancelTaskService;

  @Autowired
  private RestTemplateFactory restTemplateFactory;

  @Value("${enio.univ-cd}")
	private String agtCd;
  
  @Test
  @Sql
  public void sendPrCancel() {
    log.debug("============================== Send Pr START ==============================");

    Agent agt = agentMapper.findOne(agtCd);
    log.debug("agtCd : {}", agt.getAgtCd());
    log.debug("licenceKey : {}", agt.getRawLicenceKey());
    log.debug("agtConnUrl : {}", agt.getApiConnUrl());
    
    prReqCancelTaskService.execute();

    log.debug("============================== Send Pr END ==============================");
  }
}
