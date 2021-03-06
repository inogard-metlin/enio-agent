package kr.co.inogard.enio.agent.service.pr;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import kr.co.inogard.enio.agent.commons.EnioFileSystemResource;
import kr.co.inogard.enio.agent.commons.constant.EnioMediaType;
import kr.co.inogard.enio.agent.commons.constant.RsltCd;
import kr.co.inogard.enio.agent.commons.constant.SendStatus;
import kr.co.inogard.enio.agent.commons.constant.UserKind;
import kr.co.inogard.enio.agent.domain.agent.AgentConfig;
import kr.co.inogard.enio.agent.domain.dept.DeptDum;
import kr.co.inogard.enio.agent.domain.file.CmmFile;
import kr.co.inogard.enio.agent.domain.pr.Pr;
import kr.co.inogard.enio.agent.domain.pr.PrDto;
import kr.co.inogard.enio.agent.domain.pr.PrDum;
import kr.co.inogard.enio.agent.domain.pr.PrFile;
import kr.co.inogard.enio.agent.domain.pr.PrFileDum;
import kr.co.inogard.enio.agent.domain.pr.PrItem;
import kr.co.inogard.enio.agent.domain.pr.PrSrv;
import kr.co.inogard.enio.agent.domain.pr.PrUserDum;
import kr.co.inogard.enio.agent.domain.user.UserDum;
import kr.co.inogard.enio.agent.mappers.dept.DeptDumMapper;
import kr.co.inogard.enio.agent.mappers.pr.PrDumMapper;
import kr.co.inogard.enio.agent.mappers.pr.PrFileDumMapper;
import kr.co.inogard.enio.agent.mappers.pr.PrFileMapper;
import kr.co.inogard.enio.agent.mappers.pr.PrItemMapper;
import kr.co.inogard.enio.agent.mappers.pr.PrMapper;
import kr.co.inogard.enio.agent.mappers.pr.PrSrvMapper;
import kr.co.inogard.enio.agent.mappers.pr.PrUserDumMapper;
import kr.co.inogard.enio.agent.mappers.user.UserDumMapper;
import kr.co.inogard.enio.agent.service.file.FileService;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class PrSendService {

	@Autowired
	private PrService prService;

	@Autowired
	private FileService fileService;

	@Autowired
	private PrMapper prMapper;

	@Autowired
	private PrDumMapper prDumMapper;

	@Autowired
	private PrItemMapper prItemMapper;

	@Autowired
	private PrFileMapper prFileMapper;

	@Autowired
	private PrFileDumMapper prFileDumMapper;

	@Autowired
	private PrSrvMapper prSrvMapper;

	@Autowired
	private PrUserDumMapper prUserDumMapper;

	@Autowired
	private UserDumMapper userDumMapper;

	@Autowired
	private DeptDumMapper deptDumMapper;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private AgentConfig agentConfig;

	@Value("${enio.api.context-path}/${enio.api.version}${enio.api.uri-path.pr}")
	private String prApiUriPath;	
	
	@Value("${enio.univ-cd}")
	private String agtCd;
	
	public List<PrDum> getPrNotSend() {
		List<PrDum> notSendPrDumList = prDumMapper.findAllNotSend();
		List<PrDum> prDumList = new ArrayList<PrDum>();
		for (PrDum prDum : notSendPrDumList) {
			prDumList.add(prService.getDummyPr(prDum.getErpPrNo()));
		}
		return prDumList;
	}

	public void checkSendPr(String erpPrNo, List<DeptDum> sendDeptList, List<UserDum> sendUserList) {
		//List<PrUserDum> prUserDumList = prUserDumMapper.findAllByErpPrNo(erpPrNo);		
		List<PrUserDum> prUserDumList = prUserDumMapper.findAllByErpPrNoAgtCd(erpPrNo, agtCd);
		
		Map<String, DeptDum> deptDumInfo = new HashMap<String, DeptDum>();
		Map<String, UserDum> userDumInfo = new HashMap<String, UserDum>();

		for (PrUserDum prUserDum : prUserDumList) {

			if (StringUtils.isEmpty(prUserDum.getErpDeptCd())) {
				DeptDum deptDum = deptDumMapper.findByDeptNm(prUserDum.getDeptNm(), agtCd);
				if (deptDum == null) {
					deptDum = new DeptDum();
					deptDum.setDeptNm(prUserDum.getDeptNm());
					deptDum.setDeptTel(prUserDum.getUserTel());
					deptDum.setAgtCd(agtCd);
					deptDumMapper.add(deptDum);
				}
				prUserDum.setErpDeptCd(deptDum.getErpDeptCd());
			}

			if (StringUtils.isEmpty(prUserDum.getErpUserCd())) {
				UserDum userDum = new UserDum();
				userDum.setUserNm(prUserDum.getUserNm());
				userDum.setUserEmail(prUserDum.getUserEmail());
				userDum.setAgtCd(agtCd);
				
				userDum = userDumMapper.findByUserNmAndUserEmail(userDum);
				if (userDum == null) {
					userDum = new UserDum();
					userDum.setUserNm(prUserDum.getUserNm());
					userDum.setUserTel(prUserDum.getUserTel());
					userDum.setUserEmail(prUserDum.getUserEmail());
					userDum.setErpDeptCd(prUserDum.getErpDeptCd());
					userDum.setUserSms(prUserDum.getUserSms());
					userDum.setAgtCd(agtCd);
					
					userDumMapper.add(userDum);
				}
				prUserDum.setErpUserCd(userDum.getErpUserCd());
			}

			PrDum prDum = new PrDum();
			prDum.setErpPrNo(erpPrNo);
			if (UserKind.valueOf(prUserDum.getUserKind()).isGmUser()) {
				prDum.setGmChrgrCd(prUserDum.getErpUserCd());
				prDumMapper.updateGmChrgrCd(prDum);
			}

			if (UserKind.valueOf(prUserDum.getUserKind()).isPrUser()) {
				prDum.setPrChrgrCd(prUserDum.getErpUserCd());
				prDumMapper.updatePrChrgrCd(prDum);
			}

			if (UserKind.valueOf(prUserDum.getUserKind()).isGrUser()) {
				prDum.setGrChrgrCd(prUserDum.getErpUserCd());
				prDumMapper.updateGrChrgrCd(prDum);
			}

			if (UserKind.valueOf(prUserDum.getUserKind()).isChkUser()) {
				prDum.setChkChrgrCd(prUserDum.getErpUserCd());
				prDumMapper.updateChkChrgrCd(prDum);
			}

			if (StringUtils.isEmpty(prUserDum.getE4uDeptCd())) {
				DeptDum deptDum = deptDumMapper.findByErpDeptCd(prUserDum.getErpDeptCd());
				if (deptDumInfo.get(prUserDum.getErpDeptCd()) == null) {
					deptDumInfo.put(prUserDum.getErpDeptCd(), deptDum);
					sendDeptList.add(deptDum);
				}
			}

			if (StringUtils.isEmpty(prUserDum.getE4uUserCd())) {
				UserDum userDum = userDumMapper.findByErpUserCd(prUserDum.getErpUserCd());
				if (userDumInfo.get(prUserDum.getErpUserCd()) == null) {
					userDumInfo.put(prUserDum.getErpUserCd(), userDum);
					sendUserList.add(userDum);
				}
			}

		} // for
	}

	public SendStatus send(String erpPrNo) {
		log.info("Send Pr Start erpPrNo : {}", erpPrNo);

		PrDum prDum = prService.getDummyPr(erpPrNo);
		PrDto.Create createPr = modelMapper.map(prDum, PrDto.Create.class);

		HttpHeaders jsonPartHeader = new HttpHeaders();
		jsonPartHeader.setContentType(EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType());
		HttpEntity<PrDto.Create> jsonPart = new HttpEntity<PrDto.Create>(createPr, jsonPartHeader);

		MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<String, Object>();
		multiValueMap.add("data", jsonPart);

		List<PrFileDum> fileList = prDum.getFileList();
		for (PrFileDum prFileDum : fileList) {
			//파일링크가 있을 경우에 지정한 파일을 저장한다.
			if (!StringUtils.isEmpty(prFileDum.getSvrFileLink())) {
				prService.saveFileFromLink(prFileDum);
			}
			
			File file = new File(prFileDum.getSvrFilePath() + "/" + prFileDum.getSvrFileNm());
			multiValueMap.add("files", new EnioFileSystemResource(file, prFileDum.getSvrFileNm()));
		}

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
		httpHeaders.setAccept(Collections.singletonList(EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType()));

		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(multiValueMap, httpHeaders);

		SendStatus status = SendStatus.SEND_FAIL;
		String url = agentConfig.getAgent().getApiConnUrl() + prApiUriPath;
		
		PrDto.Response res = restTemplate.postForObject(url, requestEntity, PrDto.Response.class);
		
		if (RsltCd.valueOf(res.getRsltCd()).isSuccess()) {
			this.sendComplete(res);
			status = SendStatus.SEND_COMPLETE;
		}
		log.info("Send Pr End erpPrNo : {}", erpPrNo);
		return status;
	}

	private void sendComplete(PrDto.Response res) {
		PrDum prDum = new PrDum();
		prDum.setE4uPrNo(res.getPrNo());
		prDum.setErpPrNo(res.getErpPrNo());
		prDum.setSubOrgCd(res.getSubOrgCd());
		prDumMapper.updateE4uPrNo(prDum);

		Pr pr = new Pr();
		pr.setErpPrNo(prDum.getErpPrNo());
		prMapper.addFromDummy(pr);

		PrItem prItem = new PrItem();
		prItem.setErpPrNo(prDum.getErpPrNo());
		prItemMapper.addFromDummy(prItem);

		PrSrv prSrv = new PrSrv();
		prSrv.setErpPrNo(prDum.getErpPrNo());
		prSrvMapper.addFromDummy(prSrv);

		List<PrFileDum> prFileDumList = prFileDumMapper.findAllByErpPrNo(prDum.getErpPrNo());

		for (PrFileDum prFileDum : prFileDumList) {
			File uploadedFile = new File(prFileDum.getSvrFilePath() + "/" + prFileDum.getSvrFileNm());
			CmmFile cmmFile = fileService.store(uploadedFile, "10");

			PrFile prFile = new PrFile();
			prFile.setPrNo(res.getPrNo());
			prFile.setFileNo(cmmFile.getFileNo());
			prFileMapper.add(prFile);
		}
	}

	public void updateSendStatus(PrDum prDum, SendStatus status) {
		String e4uIfSt = status.getCode();
		prDum.setE4uIfSt(e4uIfSt);
		prDumMapper.updateE4uIfSt(prDum);
	}
}
