package kr.co.inogard.enio.agent.service.rtlog;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import kr.co.inogard.enio.agent.domain.rtlog.RtLog;
import kr.co.inogard.enio.agent.mappers.rtlog.RtLogMapper;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RtLogService {

  @Autowired
  private RtLogMapper rtLogMapper;
  
  @Value("${enio.univ-cd}")
  private String agtCd;
  
  public RtLogService() {
    // TODO Auto-generated constructor stub
  }
  
  @Transactional(propagation = Propagation.REQUIRES_NEW)  
  public RtLog create(Throwable ex) {
    String message="";    
    try {
      StringWriter sw = new StringWriter();
      PrintWriter pw=new PrintWriter(sw);
      ex.printStackTrace(pw);
      
      message=sw.toString();
      pw.close();sw.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return this.logToDB(RtLog.LogLevel.ERROR, ex.getMessage(), message);
  }
  
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public RtLog create(RtLog.LogLevel logLvl, String ttl, String message) {
    return this.logToDB(logLvl, ttl, message);
  }
  private RtLog logToDB(RtLog.LogLevel logLvl, String ttl, String message) {
    RtLog rtLog = new RtLog();
    rtLog.setLogLvl(logLvl.name());
    rtLog.setTtl(ttl);
    rtLog.setMessage(message);
    rtLog.setSolveYn("N");
    rtLog.setSolveDt(null);
    rtLog.setAgtCd(agtCd);
    
    this.rtLogMapper.add(rtLog);
    return rtLog;
  }

  @Transactional
  public void updateSolved(String logNo, String solveYn) {
    RtLog rtLog = new RtLog();
    rtLog.setLogNo(logNo);
    rtLog.setSolveYn(solveYn);
    rtLog.setAgtCd(agtCd);
    
    this.rtLogMapper.updateSolved(rtLog);
  }

  @Transactional
  public RtLog getByLogNo(String logNo) {
    return this.rtLogMapper.findByLogNo(logNo);
  }  

}
