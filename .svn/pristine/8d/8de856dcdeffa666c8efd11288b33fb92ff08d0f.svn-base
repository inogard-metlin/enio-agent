package kr.co.inogard.enio.agent.commons.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class SystemArchitecture {

  @Pointcut("within(kr.co.inogard.enio.agent.web..*)")
  public void inWebLayer() {}

  @Pointcut("within(kr.co.inogard.enio.agent.service..*)")
  public void inServiceLayer() {}

  @Pointcut("within(kr.co.inogard.enio.agent.mappers..*)")
  public void inDataAccessLayer() {}
  
}