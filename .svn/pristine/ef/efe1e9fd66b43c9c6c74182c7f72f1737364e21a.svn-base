package kr.co.inogard.enio.agent.commons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

  @Value("${enio.welcome.message}")
  private String welcomeMessage;

  @Autowired
  private Environment environment;

  @Override
  public void onApplicationEvent(final ApplicationReadyEvent event) {
    log.info("welcomeMessage : {}", welcomeMessage);
    log.info("activeProfiles : {}", (Object) environment.getActiveProfiles());
    log.info("Application startup complete!");
  }
}
