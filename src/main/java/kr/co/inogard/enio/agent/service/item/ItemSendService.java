package kr.co.inogard.enio.agent.service.item;

import java.util.Collections;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import kr.co.inogard.enio.agent.commons.constant.EnioMediaType;
import kr.co.inogard.enio.agent.commons.constant.RsltCd;
import kr.co.inogard.enio.agent.commons.constant.SendStatus;
import kr.co.inogard.enio.agent.domain.agent.AgentConfig;
import kr.co.inogard.enio.agent.domain.item.Item;
import kr.co.inogard.enio.agent.domain.item.ItemDto;
import kr.co.inogard.enio.agent.domain.item.ItemDum;
import kr.co.inogard.enio.agent.mappers.item.ItemDumMapper;
import kr.co.inogard.enio.agent.mappers.item.ItemMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class ItemSendService {

	@Autowired
	private ItemMapper itemMapper;

	@Autowired
	private ItemDumMapper itemDumMapper;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private AgentConfig agentConfig;

	@Value("${enio.api.context-path}/${enio.api.version}${enio.api.uri-path.item}")
	private String itemApiUriPath;

	public SendStatus send(List<ItemDum> itemDumList) {
		List<ItemDto.Create> sendItemList = modelMapper.map(itemDumList, new TypeToken<List<ItemDto.Create>>() {
		}.getType());

		ItemDto.CreateWrapper createWrapper = new ItemDto.CreateWrapper();
		createWrapper.setDatas(sendItemList);

		HttpHeaders header = new HttpHeaders();
		header.setContentType(EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType());
		header.setAccept(Collections.singletonList(EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType()));

		HttpEntity<ItemDto.CreateWrapper> requestEntity = new HttpEntity<ItemDto.CreateWrapper>(createWrapper, header);

		SendStatus status = SendStatus.SEND_FAIL;
		String url = agentConfig.getAgent().getApiConnUrl() + itemApiUriPath;

		ItemDto.ResponseWrapper res = restTemplate.postForObject(url, requestEntity, ItemDto.ResponseWrapper.class);
		if (RsltCd.valueOf(res.getRsltCd()).isSuccess()) {
			this.sendComplete(res.getDatas());
			status = SendStatus.SEND_COMPLETE;
		}
		return status;
	}

	private void sendComplete(List<ItemDto.Response> resItemList) {
		for (ItemDto.Response resItem : resItemList) {
			String erpItemCd = resItem.getErpItemCd();
			String e4uItemCd = resItem.getItemCd();

			ItemDum itemDum = new ItemDum();
			itemDum.setErpItemCd(erpItemCd);
			itemDum.setE4uItemCd(e4uItemCd);
			itemDumMapper.updateE4uItemCd(itemDum);

			Item item = new Item();
			item.setErpItemCd(erpItemCd);
			itemMapper.add(item);
		}
	}

	public void updateSendStatus(List<ItemDum> itemDumList, SendStatus status) {
		String e4uIfSt = status.getCode();
		for (ItemDum item : itemDumList) {
			item.setE4uIfSt(e4uIfSt);
			itemDumMapper.updateE4uIfSt(item);
		}
	}
}
