package kr.co.inogard.enio.agent.domain.agent;

import lombok.Data;

@Data
public class AgentConfig {

  private final Agent agent;

  public boolean isApi(String apiCd) {
    return this.agent.getApiCd().equals(apiCd);
  }

  public boolean isAdmin(String adminId) {
    return this.agent.getAdminId().equals(adminId);
  }

}