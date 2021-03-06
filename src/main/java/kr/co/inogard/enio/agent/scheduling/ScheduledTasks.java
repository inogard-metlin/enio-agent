package kr.co.inogard.enio.agent.scheduling;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import kr.co.inogard.enio.agent.domain.agent.AgentConfig;
import kr.co.inogard.enio.agent.domain.rtlog.RtLog;
import kr.co.inogard.enio.agent.scheduling.tasks.ItemTaskService;
import kr.co.inogard.enio.agent.scheduling.tasks.PrReqCancelTaskService;
import kr.co.inogard.enio.agent.scheduling.tasks.PrTaskService;
import kr.co.inogard.enio.agent.service.rtlog.RtLogService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ScheduledTasks {

	// @Autowired
	// private AgentMapper agentMapper;

	@Autowired
    private ItemTaskService itemTaskService;

	@Autowired
	private PrTaskService prTaskService;

	@Autowired
	private PrReqCancelTaskService prReqCancelTaskService;

	// @Autowired
	// private RestTemplateFactory restTemplateFactory;

	@Autowired
	private RtLogService rtLogService;

	@Autowired
	private AgentConfig agentConfig;

	int failedCount = 0;
	String failedMessage = "";
	boolean bRecovered = true;

	@Scheduled(cron = "${enio.scheduled.cron}")
	public void execute() {
		log.info("========================= Scheduled Tasks START =========================");
		log.info("fail-count : {}", failedCount);

		if (failedCount > 10) {
			bRecovered = false; // 복구가 필요함
			log.info("10번 연결을 시도했으나 계속접속이 안됨 접속상태 확인이 필요합니다. 추후 10번의 스케쥴은 무시됩니다.");
			rtLogService.create(RtLog.LogLevel.ERROR, "ENIO-API연결실패",
					"10번 연결을 시도했으나 계속접속이 안됨 접속상태 확인이 필요합니다. 추후 10번의 스케쥴은 무시됩니다.");
		}
		if (!bRecovered) {
			failedCount--;
			if (failedCount == 0) {
				bRecovered = true;
				log.info("이제 연결이 복구되었다고 가정합니다.");
				rtLogService.create(RtLog.LogLevel.INFO, "ENIO-API 연결복구됨(가정)", "이제 연결이 복구되었다고 가정합니다.");
			}
			return;
		}

		if (!this.isAlive(agentConfig.getAgent().getApiConnUrl())) {
			failedCount++;
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				log.error(e.getMessage(), e);
			}
			return;
		}
		failedCount = 0;

		itemTaskService.execute();
		prReqCancelTaskService.execute();
		prTaskService.execute();

		log.info("========================= Scheduled Tasks END =========================");

		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
		}
	}

	private boolean isAlive(String sUrl) {
		URL u = null;

		try {
			u = new URL(sUrl);
		} catch (Exception e) {
			return false;
		}
		String hostName = u.getHost();
		int port = u.getPort();
		return this.isAlive(hostName, port);
	}

	private boolean isAlive(String hostName, int port) {
		boolean isAlive = false;
		SocketAddress socketAddress = new InetSocketAddress(hostName, port);
		Socket socket = new Socket();
		int timeout = 2000;// milliseconds
		try {
			socket.connect(socketAddress, timeout);
			socket.close();
			isAlive = true;
		} catch (SocketTimeoutException ex) {
			log.info("SocketTimeoutException : {} : {}. {}", hostName, port, ex.getMessage());
		} catch (IOException ex) {
			log.info("IOException - Unable to connect to {} : {}. {}", hostName, port, ex.getMessage());
		}
		log.info("isAlive : {}", isAlive);
		return isAlive;
	}
}
