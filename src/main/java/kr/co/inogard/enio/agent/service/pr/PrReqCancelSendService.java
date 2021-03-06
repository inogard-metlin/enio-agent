package kr.co.inogard.enio.agent.service.pr;

import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import kr.co.inogard.enio.agent.commons.constant.EnioMediaType;
import kr.co.inogard.enio.agent.commons.constant.RsltCd;
import kr.co.inogard.enio.agent.commons.constant.SendStatus;
import kr.co.inogard.enio.agent.domain.agent.AgentConfig;
import kr.co.inogard.enio.agent.domain.pr.PrDto;
import kr.co.inogard.enio.agent.domain.pr.PrDto.ReqCancelResponse;
import kr.co.inogard.enio.agent.domain.pr.PrDum;
import kr.co.inogard.enio.agent.mappers.pr.PrDumMapper;
import kr.co.inogard.enio.agent.mappers.pr.PrFileDumMapper;
import kr.co.inogard.enio.agent.mappers.pr.PrItemDumMapper;
import kr.co.inogard.enio.agent.mappers.pr.PrSrvDumMapper;
import kr.co.inogard.enio.agent.mappers.pr.PrUserDumMapper;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class PrReqCancelSendService {

	@Autowired
	private PrDumMapper prDumMapper;

	@Autowired
	private PrItemDumMapper prItemDumMapper;

	@Autowired
	private PrSrvDumMapper prSrvDumMapper;

	@Autowired
	private PrUserDumMapper prUserDumMapper;

	@Autowired
	private PrFileDumMapper prFileDumMapper;

	@Autowired
	private PrService prService;

	@Value("${enio.api.context-path}/${enio.api.version}${enio.api.uri-path.pr}")
	private String prApiUriPath;

	@Value("${enio.univ-cd}")
	private String univCd;
	
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private AgentConfig agentConfig;

	public void send(PrDum prDum) {
		PrDto.ReqCancelCreate cre = new PrDto.ReqCancelCreate();
		cre.setPrNo(prDum.getE4uPrNo());
		cre.setCnclReqDt(prDum.getCnclReqDt());

		HttpHeaders header = new HttpHeaders();
		header.setContentType(EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType());
		header.setAccept(Collections.singletonList(EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType()));
		HttpEntity<PrDto.ReqCancelCreate> requestEntity = new HttpEntity<PrDto.ReqCancelCreate>(cre, header);

		String url = agentConfig.getAgent().getApiConnUrl() + prApiUriPath + "/cancel/" + prDum.getE4uPrNo();

		ResponseEntity<ReqCancelResponse> resEntity = restTemplate.exchange(url, HttpMethod.PATCH, requestEntity, PrDto.ReqCancelResponse.class);
		ReqCancelResponse res = resEntity.getBody();
		if (RsltCd.valueOf(res.getRsltCd()).isSuccess()) {
			this.updateCnclFlag(prDum.getErpPrNo(), res.getCnclFlag(), res.getCnclReqDt(), res.getCnclRsltDt(), res.getCnclRsltMsg());

			this.sendComplete(res);
		}
	}

	private void sendComplete(PrDto.ReqCancelResponse res) {
		prService.updateCallSyncToErp(res.getPrNo(), "PR_CNCL");
	}

	public void updateCnclFlag(String erpPrNo, String cnclFlag, Date cnclReqDt, Date cnclRsltDt, String cnclRsltMsg) {
		PrDum prDum = new PrDum();
		prDum.setErpPrNo(erpPrNo);
		prDum.setCnclFlag(cnclFlag);
		prDum.setCnclReqDt(cnclReqDt);
		prDum.setCnclRsltDt(cnclRsltDt);
		prDum.setCnclRsltMsg(cnclRsltMsg);
		prDumMapper.updateCnclFlag(prDum);
	}

	public void deleteByNotSendPr(List<PrDum> prDumList) {
		Iterator<PrDum> prDumiter = prDumList.iterator();
		while (prDumiter.hasNext()) {
			PrDum prDum = prDumiter.next();
			if (SendStatus.codeOf(prDum.getE4uIfSt()).isReady()) {
				String erpPrNo = prDum.getErpPrNo();
				prSrvDumMapper.deleteByErpPrNo(erpPrNo);
				prItemDumMapper.deleteByErpPrNo(erpPrNo);
				prFileDumMapper.deleteByErpPrNo(erpPrNo);
				prUserDumMapper.deleteByErpPrNo(erpPrNo);
				prDumMapper.deleteByErpPrNo(erpPrNo);

				prDumiter.remove();
			}
		}
	}
}
