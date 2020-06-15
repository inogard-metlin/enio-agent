package kr.co.inogard.enio.agent.domain.event;

import lombok.Data;

@Data
public class CmmEventCmdParam {
	private String uri;
	private String HttpMethod;
}
