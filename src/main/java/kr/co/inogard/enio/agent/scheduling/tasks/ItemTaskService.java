package kr.co.inogard.enio.agent.scheduling.tasks;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import kr.co.inogard.enio.agent.commons.constant.SendStatus;
import kr.co.inogard.enio.agent.domain.item.ItemDum;
import kr.co.inogard.enio.agent.mappers.item.ItemDumMapper;
import kr.co.inogard.enio.agent.service.item.ItemSendService;
import kr.co.inogard.enio.agent.service.rtlog.RtLogService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ItemTaskService {

	@Autowired
	private ItemDumMapper itemDumMapper;

	@Autowired
	private ItemSendService itemSendService;

	@Autowired
	private RtLogService rtLogService;

	public void execute() {
		log.info("========================= Send Item START =========================");
		List<ItemDum> itemDumList = itemDumMapper.findAllNotSend();
		if (CollectionUtils.isEmpty(itemDumList)) {
			log.info("No data to send");
			return;
		}

		itemSendService.updateSendStatus(itemDumList, SendStatus.SEND_WORK);

		try {
			SendStatus status = itemSendService.send(itemDumList);
			itemSendService.updateSendStatus(itemDumList, status);
		} catch (RuntimeException e) {
			itemSendService.updateSendStatus(itemDumList, SendStatus.SEND_WORK_ERR);
			rtLogService.create(e);
			throw e;
		}
		log.info("========================= Send Item END =========================");
	}
}
