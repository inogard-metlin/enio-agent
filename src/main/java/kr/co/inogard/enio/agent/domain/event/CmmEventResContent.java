package kr.co.inogard.enio.agent.domain.event;

import java.util.Map;

import lombok.Data;

@Data
public class CmmEventResContent {
	private Map<String, String> headers;
	private int status;
	private Object content;
}
