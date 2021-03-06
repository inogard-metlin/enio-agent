package kr.co.inogard.enio.agent.domain.rtlog;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RtLog {
  private String logNo;
  private String logLvl;
  private String ttl;
  private String message;
  private String solveYn;
  private Date solveDt;
  private Date regDt;
  private String agtCd;

  public static enum LogLevel {
    ERROR,DEBUG,INFO;
  }
}
