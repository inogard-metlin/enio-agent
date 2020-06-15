package kr.co.inogard.enio.agent.dept;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import kr.co.inogard.enio.agent.Application;
import kr.co.inogard.enio.agent.domain.dept.DeptDum;
import kr.co.inogard.enio.agent.service.dept.DeptSendService;
import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
@Slf4j
public class DeptServiceTest {

  @Autowired
  private DeptSendService deptSendService;

  @Test
  public void findListNotSendTest() {
    List<DeptDum> list = deptSendService.getAllNotSend();
    log.debug("list size : {}", list.size());
    assertThat(3, is(list.size()));
  }
}