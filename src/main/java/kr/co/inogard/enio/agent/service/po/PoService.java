package kr.co.inogard.enio.agent.service.po;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeUtility;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import kr.co.inogard.enio.agent.commons.exception.EnioRunTimeException;
import kr.co.inogard.enio.agent.commons.handler.EnioFileHandler;
import kr.co.inogard.enio.agent.commons.util.Utils;
import kr.co.inogard.enio.agent.domain.file.CmmFile;
import kr.co.inogard.enio.agent.domain.po.Po;
import kr.co.inogard.enio.agent.domain.po.PoDto;
import kr.co.inogard.enio.agent.domain.po.PoFile;
import kr.co.inogard.enio.agent.domain.po.PoItem;
import kr.co.inogard.enio.agent.domain.po.PoSrv;
import kr.co.inogard.enio.agent.mappers.po.PoFileMapper;
import kr.co.inogard.enio.agent.mappers.po.PoItemMapper;
import kr.co.inogard.enio.agent.mappers.po.PoMapper;
import kr.co.inogard.enio.agent.mappers.po.PoSrvMapper;
import kr.co.inogard.enio.agent.service.file.FileService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class PoService {

	@Autowired
	private PoMapper poMapper;

	@Autowired
	private PoItemMapper poItemMapper;

	@Autowired
	private PoSrvMapper poSrvMapper;

	@Autowired
	private PoFileMapper poFileMapper;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private EnioFileHandler enioFileHandler;

	@Autowired
	private FileService fileService;	

	@Value("${enio.univ-cd}")
	private String univCd;	
	
	public Po create(PoDto.Create poDto, List<MultipartFile> files) {
		Po po = modelMapper.map(poDto, Po.class);
		
		// Agent 테이블에 계약정보가 있는 경우 확인한 후 모두 삭제해 줌.
		if (getPo(po.getPoNo()) != null) {
			poFileMapper.delPoFile(po.getPoNo());
			poSrvMapper.delPoService(po.getPoNo());
			poItemMapper.delPoItem(po.getPoNo());
			poMapper.delPo(po.getPoNo());
		}
		
		poMapper.add(po);

		List<PoItem> createPoItemList = modelMapper.map(poDto.getPoItem(), new TypeToken<List<PoItem>>() {}.getType());
		List<PoSrv> createPoSrvList = null;
		for (PoItem xPoItem : createPoItemList) {
			poItemMapper.add(xPoItem);

			createPoSrvList = modelMapper.map(xPoItem.getSrvList(), new TypeToken<List<PoSrv>>() {}.getType());
			for (PoSrv xPoSrv : createPoSrvList) {
				poSrvMapper.add(xPoSrv);
			}
		}

		poMapper.updatePoNoInPrItem(po.getPoNo());
		
		if (!CollectionUtils.isEmpty(files)) {
			File ctmTempDir = enioFileHandler.createTempDirectory();
			try {
				for (MultipartFile multipartFile : files) {
					log.debug("multipartFile name : {}", multipartFile.getName());
					log.debug("multipartFile originalFilename : {}", MimeUtility.decodeText(multipartFile.getOriginalFilename()));

					File file = new File(ctmTempDir, MimeUtility.decodeText(multipartFile.getOriginalFilename()));
					multipartFile.transferTo(file);

					File uploadedFile = file;
					String docType = "30";
					CmmFile cmmFile = fileService.store(uploadedFile, docType);
					String fileNo = cmmFile.getFileNo();

					PoFile poFile = new PoFile();
					poFile.setFileNo(fileNo);
					poFile.setPoNo(po.getPoNo());
					poFileMapper.add(poFile);
				}

			} catch (Exception e) {
				throw new EnioRunTimeException(e);
			} finally {
				enioFileHandler.cleanUpDirectory(ctmTempDir);
			}
		}		
		
		try {
			if(!"S0001".equals(univCd)) {  // 울산 Legacy  동기화 제외
				this.updateCallSyncToErp(po.getPoNo(), po.getRfqNo(), "PO_END");
			}	
		} catch (Exception e) {
			log.error("ERP동기화(po_no=" + po.getPoNo() + ",PO_END):작업오류발생", e);
		}

		return po;
	}

	public void updateCallSyncToErp(String poNo, String rfqNo, String notiKind) {
		Map<String, String> info = new HashMap<String, String>();
		info.put("poNo", poNo);
		info.put("notiKind", notiKind);
		info.put("rfqNo", rfqNo);
		info.put("univCd", univCd);
		info.put("outCd", "");
		info.put("outMsg", "");
		
		poMapper.updateCallSyncToErp(info);
		
		log.debug("outCd : {}", info.get("outCd") );
		log.debug("outMsg : {}", info.get("outMsg") );
		
		if ( !"1".equals(info.get("outCd")) ) { 
			throw new RuntimeException(info.get("outMsg"));
		}
	}

	public DataTablesOutput<PoDto.Response> getAllPo(PoDto.Search search, DataTablesInput input) {
		DataTablesOutput<PoDto.Response> output = new DataTablesOutput<PoDto.Response>();
		output.setDraw(input.getDraw());

		try {
			search.setValue(input.getSearch().getValue());
			search.setAgtCd(univCd);
			List<Po> poList = poMapper.findAll(search, Utils.dataTablesInputToPageable(input));
			List<PoDto.Response> data = modelMapper.map(poList, new TypeToken<List<PoDto.Response>>() {
			}.getType());

			output.setData(data);
			output.setRecordsFiltered(CollectionUtils.isEmpty(poList) ? 0 : poList.get(0).getFilteredCount());
			output.setRecordsTotal(poMapper.count(search.getAgtCd()));
		} catch (Exception e) {
			output.setError(e.getMessage());
			log.error(e.getMessage(), e);
		}
		return output;
	}

	public Page<Po> getAllPo(PoDto.Search search, Pageable pageable) {
		search.setAgtCd(univCd);
		List<Po> pr = poMapper.findAll(search, pageable);
		return new PageImpl<Po>(pr, pageable, CollectionUtils.isEmpty(pr) ? 0 : pr.get(0).getFilteredCount());
	}

	public Po getPo(String poNo) {
		return poMapper.findByPoNo(poNo);
	}

	public List<PoItem> getPoItems(String poNo) {
		return poItemMapper.findAllByPoNo(poNo);
	}

	public List<PoSrv> getPoServices(String poNo, String itemSeq) {
		return poSrvMapper.findAllByPoNoAndItemSeq(poNo, itemSeq);
	}

	public List<PoFile> getPoFiles(String poNo) {
		return poFileMapper.findAllByPoNo(poNo);
	}

	public PoFile getPoFile(String poNo, String fileSeq) {
		return poFileMapper.findByPoNoAndFileSeq(poNo, fileSeq);
	}

}
