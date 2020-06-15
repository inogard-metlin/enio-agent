package kr.co.inogard.enio.agent.support;

import javax.annotation.Resource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WithMockCustomUserSecurityContextFactory
    implements WithSecurityContextFactory<WithMockAgentUserDetails> {

  @Resource(name = "agentUserDetailsService")
  private UserDetailsService userDetailsService;

  @Override
  public SecurityContext createSecurityContext(WithMockAgentUserDetails user) {
    log.debug("username : {}", user.value());
    UserDetails principal = userDetailsService.loadUserByUsername(user.value());
    Authentication authentication = new UsernamePasswordAuthenticationToken(principal,
        principal.getPassword(), principal.getAuthorities());
    SecurityContext context = SecurityContextHolder.createEmptyContext();
    context.setAuthentication(authentication);
    return context;
  }
}
