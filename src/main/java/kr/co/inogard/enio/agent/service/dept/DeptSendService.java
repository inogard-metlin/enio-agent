package kr.co.inogard.enio.agent.service.dept;

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
import kr.co.inogard.enio.agent.domain.dept.Dept;
import kr.co.inogard.enio.agent.domain.dept.DeptDto;
import kr.co.inogard.enio.agent.domain.dept.DeptDum;
import kr.co.inogard.enio.agent.mappers.dept.DeptDumMapper;
import kr.co.inogard.enio.agent.mappers.dept.DeptMapper;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class DeptSendService {

	@Autowired
	private DeptMapper deptMapper;

	@Autowired
	private DeptDumMapper deptDumMapper;

	@Autowired
	private ModelMapper modelMapper;

	@Value("${enio.api.context-path}/${enio.api.version}${enio.api.uri-path.dept}")
	private String deptApiUriPath;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private AgentConfig agentConfig;

	public List<DeptDum> getAllNotSend() {
		return deptDumMapper.findAllNotSend();
	}

	public SendStatus send(List<DeptDum> deptList) {
		List<DeptDto.CreateEntry> createDeptList = modelMapper.map(deptList, new TypeToken<List<DeptDto.CreateEntry>>() {}.getType());
		DeptDto.Create createDept = new DeptDto.Create();
		createDept.setDatas(createDeptList);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType());
		httpHeaders.setAccept(Collections.singletonList(EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType()));
		
		HttpEntity<DeptDto.Create> requestEntity = new HttpEntity<DeptDto.Create>(createDept, httpHeaders);

		SendStatus status = SendStatus.SEND_FAIL;
		String url = agentConfig.getAgent().getApiConnUrl() + deptApiUriPath;
		DeptDto.Response res = restTemplate.postForObject(url, requestEntity, DeptDto.Response.class);
		if (RsltCd.valueOf(res.getRsltCd()).isSuccess()) {
			List<DeptDto.ResponseEntry> rcvList = res.getDatas();
			for (DeptDto.ResponseEntry resEntry : rcvList) {
				this.sendComplete(resEntry);
			}
			status = SendStatus.SEND_COMPLETE;
		}
		log.info("Send Dept End");
		return status;
	}

	private void sendComplete(DeptDto.ResponseEntry res) {
		DeptDum deptDum = new DeptDum();
		deptDum.setE4uDeptCd(res.getE4uDeptCd());
		deptDum.setErpDeptCd(res.getErpDeptCd());
		deptDumMapper.updateE4uDeptCd(deptDum);

		Dept dept = new Dept();
		dept.setErpDeptCd(deptDum.getErpDeptCd());
		deptMapper.add(dept);
	}

	public void updateSendStatus(List<DeptDum> deptDumList, SendStatus status) {
		String e4uIfSt = status.getCode();
		for (DeptDum deptDum : deptDumList) {
			deptDum.setE4uIfSt(e4uIfSt);
			deptDumMapper.updateE4uIfSt(deptDum);
		}
	}
}
