package kr.co.inogard.enio.agent.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import kr.co.inogard.enio.agent.domain.agent.Agent;
import kr.co.inogard.enio.agent.domain.agent.AgentConfig;
import lombok.extern.slf4j.Slf4j;

@Service("agentUserDetailsService")
@Slf4j
public class AgentUserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private AgentConfig agentConfig;

  @Override
  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
    Agent agt = agentConfig.getAgent();
    if (!agentConfig.isApi(userName)) {
      throw new UsernameNotFoundException(userName);
    }
    return new AgentUserDetailsImpl(agt.getApiCd(), agt.getApiRawLicenceKey(), agt);
  }
}
