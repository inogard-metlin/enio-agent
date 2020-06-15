package kr.co.inogard.enio.agent.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import kr.co.inogard.enio.agent.domain.agent.Agent;

@SuppressWarnings("serial")
public class AgentUserDetailsImpl extends User {
  final private Agent agt;

  public AgentUserDetailsImpl(String username, String password, Agent agt) {
    super(username, password, authorities(agt));
    this.agt = agt;
  }

  private static Collection<? extends GrantedAuthority> authorities(Agent agt) {
    List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    authorities.add(new SimpleGrantedAuthority("ROLE_AGENT"));
    return authorities;
  }

  public Agent getAgent() {
    return this.agt;
  }
}
