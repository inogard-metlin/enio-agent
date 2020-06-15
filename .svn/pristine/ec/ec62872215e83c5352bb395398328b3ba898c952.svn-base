package kr.co.inogard.enio.agent.rtlog;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import kr.co.inogard.enio.agent.Application;
import kr.co.inogard.enio.agent.commons.constant.RsltCd;
import kr.co.inogard.enio.agent.domain.rtlog.RtLog;
import kr.co.inogard.enio.agent.service.rtlog.RtLogService;
import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
@Slf4j
public class RtLogServiceTest {

	@Autowired
	private RtLogService rtLogService;

	@Test
	public void logLevelTest() {
		
	    String ttl="index-outofbound exception"
	        ,message="exception trace content";
	    
		RtLog rtLog=this.rtLogService.create(RtLog.LogLevel.ERROR, ttl, message);

		String logNo = rtLog.getLogNo();
		log.debug("new-log-no:" + logNo);

		rtLog=rtLogService.getByLogNo(logNo);
        log.debug("after create--->SolveInfo:solve-yn=" + rtLog.getSolveYn()+",solve-dt="+rtLog.getSolveDt());		
		
		String solveYn="Y";
		rtLogService.updateSolved(logNo, solveYn);
		
		rtLog=rtLogService.getByLogNo(logNo);
		log.debug("updateSolveInfo:solve-yn=" + rtLog.getSolveYn()+",solve-dt="+rtLog.getSolveDt());
		
        try {
          int r=0;
          r=3/0;
        }catch(Exception ex) {
          rtLog=this.rtLogService.create(ex);
          logNo = rtLog.getLogNo();
          log.debug("new-log-no:" + logNo);

          rtLog=rtLogService.getByLogNo(logNo);
          log.debug("after create--->SolveInfo:solve-yn=" + rtLog.getSolveYn()+",solve-dt="+rtLog.getSolveDt());      

        }
		
        

        
		
		ttl="디버그TTL";
		message="디버그상세내용";
		
		rtLog=this.rtLogService.create(RtLog.LogLevel.DEBUG, ttl, message);

        logNo = rtLog.getLogNo();
        log.debug("new-log-no:" + logNo);

        rtLog=rtLogService.getByLogNo(logNo);
        log.debug("after create--->SolveInfo:solve-yn=" + rtLog.getSolveYn()+",solve-dt="+rtLog.getSolveDt());      
		
        ttl="INFO-TTL";
        message="INFO-상세내용";
        
        rtLog=this.rtLogService.create(RtLog.LogLevel.INFO, ttl, message);

        logNo = rtLog.getLogNo();
        log.debug("new-log-no:" + logNo);

        rtLog=rtLogService.getByLogNo(logNo);
        log.debug("after create--->SolveInfo:solve-yn=" + rtLog.getSolveYn()+",solve-dt="+rtLog.getSolveDt());      

        
	}

}
