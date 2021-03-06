package kr.co.inogard.enio.agent.service.pr;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeUtility;

import org.apache.commons.io.FileUtils;
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

import kr.co.inogard.enio.agent.commons.constant.PrTypeCd;
import kr.co.inogard.enio.agent.commons.exception.EnioRunTimeException;
import kr.co.inogard.enio.agent.commons.handler.EnioFileHandler;
import kr.co.inogard.enio.agent.commons.util.Utils;
import kr.co.inogard.enio.agent.domain.file.CmmFile;
import kr.co.inogard.enio.agent.domain.pr.Pr;
import kr.co.inogard.enio.agent.domain.pr.PrDto;
import kr.co.inogard.enio.agent.domain.pr.PrDum;
import kr.co.inogard.enio.agent.domain.pr.PrFile;
import kr.co.inogard.enio.agent.domain.pr.PrFileDum;
import kr.co.inogard.enio.agent.domain.pr.PrItem;
import kr.co.inogard.enio.agent.domain.pr.PrItemDto;
import kr.co.inogard.enio.agent.domain.pr.PrItemDum;
import kr.co.inogard.enio.agent.domain.pr.PrSrv;
import kr.co.inogard.enio.agent.domain.pr.PrSrvDto;
import kr.co.inogard.enio.agent.domain.pr.PrSrvDum;
import kr.co.inogard.enio.agent.mappers.pr.PrDumMapper;
import kr.co.inogard.enio.agent.mappers.pr.PrFileDumMapper;
import kr.co.inogard.enio.agent.mappers.pr.PrFileMapper;
import kr.co.inogard.enio.agent.mappers.pr.PrItemDumMapper;
import kr.co.inogard.enio.agent.mappers.pr.PrItemMapper;
import kr.co.inogard.enio.agent.mappers.pr.PrMapper;
import kr.co.inogard.enio.agent.mappers.pr.PrSrvDumMapper;
import kr.co.inogard.enio.agent.mappers.pr.PrSrvMapper;
import kr.co.inogard.enio.agent.service.file.FileService;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class PrService {

	@Autowired
	private PrMapper prMapper;

	@Autowired
	private PrDumMapper prDumMapper;

	@Autowired
	private PrItemMapper prItemMapper;

	@Autowired
	private PrItemDumMapper prItemDumMapper;

	@Autowired
	private PrFileMapper prFileMapper;

	@Autowired
	private PrFileDumMapper prFileDumMapper;

	@Autowired
	private PrSrvMapper prSrvMapper;

	@Autowired
	private PrSrvDumMapper prSrvDumMapper;

	@Autowired
	private EnioFileHandler enioFileHandler;

	@Autowired
	private FileService fileService;

	@Autowired
	private ModelMapper modelMapper;

	@Value("${enio.univ-cd}")
	private String univCd;

	public PrDum getDummyPr(String erpPrNo) {
		PrDum prDum = prDumMapper.findByErpPrNo(erpPrNo);
		List<PrItemDum> prItemDumList = prItemDumMapper.findAllByErpPrNo(erpPrNo);
		prDum.setItemList(prItemDumList);

		if (!PrTypeCd.valueOf(prDum.getPrTypecd()).isNormal()) {
			for (PrItemDum prItemDum : prItemDumList) {
				List<PrSrvDum> prSrvList = prSrvDumMapper.findAllByErpPrNoAndItemSeq(erpPrNo, prItemDum.getItemSeq());
				prItemDum.setSrvList(prSrvList);
			}
		}

		List<PrFileDum> prFileList = prFileDumMapper.findAllByErpPrNo(erpPrNo);
		prDum.setFileList(prFileList);

		return prDum;
	}

	public Pr createPrByPrebid(PrDto.Create prDto, List<MultipartFile> files) {
		Pr pr = modelMapper.map(prDto, Pr.class);
		pr.setPrebidYn("Y");
		String prNo = pr.getPrNo();

		//이미 구매의뢰정보가 있는지 확인한 후 모두 삭제해 줌.
		if (getPr(prNo) != null) {
			prFileMapper.delPrFile(prNo);
			prSrvMapper.delPrService(prNo);
			prItemMapper.delPrItem(prNo);
			prMapper.delPr(prNo);
		}

		prMapper.addByPrebid(pr);

		for (PrItemDto.Create creItem : prDto.getItemList()) {
			PrItem prItem = modelMapper.map(creItem, PrItem.class);
			prItem.setPrNo(prNo);
			prItemMapper.addByPrebid(prItem);
			String itemSeq = prItem.getItemSeq();

			for (PrSrvDto.Create creSrv : creItem.getSrvList()) {
				PrSrv prSrv = modelMapper.map(creSrv, PrSrv.class);
				prSrv.setPrNo(prNo);
				prSrv.setItemSeq(itemSeq);
				prSrvMapper.addByPrebid(prSrv);
			}
		}

		if (!CollectionUtils.isEmpty(files)) {
			File ctmTempDir = enioFileHandler.createTempDirectory();
			try {
				for (MultipartFile multipartFile : files) {
					log.debug("multipartFile name : {}", multipartFile.getName());
					log.debug("multipartFile originalFilename : {}",
							MimeUtility.decodeText(multipartFile.getOriginalFilename()));

					File file = new File(ctmTempDir, MimeUtility.decodeText(multipartFile.getOriginalFilename()));
					multipartFile.transferTo(file);

					File uploadedFile = file;
					String docType = "10";
					CmmFile cmmFile = fileService.store(uploadedFile, docType);
					String fileNo = cmmFile.getFileNo();

					PrFile prFile = new PrFile();
					prFile.setPrNo(pr.getPrNo());
					prFile.setFileNo(fileNo);
					prFileMapper.add(prFile);
				}
			} catch (Exception e) {
				throw new EnioRunTimeException(e);
			} finally {
				enioFileHandler.cleanUpDirectory(ctmTempDir);
			}
		}

		return pr;
	}

	public void updateCallSyncToErpByPoNo(String poNo, String notiKind) {
		String prNo = prMapper.findPrNoFromPoNo(poNo);
		this.updateCallSyncToErp(prNo, notiKind);
	}

	public void updateCallSyncToErpByRfqNo(String rfqNo, String notiKind) {
		String prNo = prMapper.findPrNoFromRfqNo(rfqNo);
		this.updateCallSyncToErp(prNo, notiKind);
	}

	public void updateCallSyncToErp(String prNo, String notiKind) {
		if (!"S0001".equals(univCd)) {
			try {
				Map<String, String> info = new HashMap<String, String>();
				info.put("prNo", prNo);
				info.put("univCd", univCd);
				info.put("notiKind", notiKind);
				info.put("outCd", "");
				info.put("outMsg", "");
				
				prMapper.updateCallSyncToErp(info);
				
				log.debug("outCd : {}", info.get("outCd"));
				log.debug("outMsg : {}", info.get("outMsg"));
				
				if (!"1".equals(info.get("outCd"))) {
					throw new RuntimeException(info.get("outMsg"));
				}				
			} catch (Exception e) {
				log.error("ERP동기화  작업(PR_CNCL) 중 오류 발생 prNo : {}", prNo, e);
			}
		}
	}

	public DataTablesOutput<PrDto.Response> getAllSendPr(PrDto.Search search, DataTablesInput input) {
		DataTablesOutput<PrDto.Response> output = new DataTablesOutput<PrDto.Response>();
		output.setDraw(input.getDraw());

		try {
			search.setValue(input.getSearch().getValue());
			search.setAgtCd(univCd);
			List<PrDum> prList = prDumMapper.findAll(search, Utils.dataTablesInputToPageable(input));
			List<PrDto.Response> data = modelMapper.map(prList, new TypeToken<List<PrDto.Response>>() {
			}.getType());

			output.setData(data);
			output.setRecordsFiltered(CollectionUtils.isEmpty(prList) ? 0 : prList.get(0).getFilteredCount());
			output.setRecordsTotal(prDumMapper.count(univCd));
		} catch (Exception e) {
			output.setError(e.getMessage());
		}
		return output;
	}

	public Page<PrDum> getAllSendPr(PrDto.Search search, Pageable pageable) {
		search.setAgtCd(univCd);
		List<PrDum> pr = prDumMapper.findAll(search, pageable);
		return new PageImpl<PrDum>(pr, pageable, CollectionUtils.isEmpty(pr) ? 0 : pr.get(0).getFilteredCount());
	}

	public PrDum getSendPr(String erpPrNo) {
		return prDumMapper.findByErpPrNo(erpPrNo);
	}

	public List<PrItemDum> getSendPrItems(String erpPrNo) {
		return prItemDumMapper.findAllByErpPrNo(erpPrNo);
	}

	public List<PrSrvDum> getSendPrServices(String erpPrNo, String itemSeq) {
		return prSrvDumMapper.findAllByErpPrNoAndItemSeq(erpPrNo, itemSeq);
	}

	public List<PrFileDum> getSendPrFiles(String erpPrNo) {
		return prFileDumMapper.findAllByErpPrNo(erpPrNo);
	}

	public PrFileDum getSendPrFile(String erpPrNo, String fileSeq) {
		return prFileDumMapper.findByErpPrNoAndFileSeq(erpPrNo, fileSeq);
	}

	public DataTablesOutput<PrDto.Response> getAllPr(PrDto.Search search, DataTablesInput input) {
		DataTablesOutput<PrDto.Response> output = new DataTablesOutput<PrDto.Response>();
		output.setDraw(input.getDraw());

		try {
			search.setValue(input.getSearch().getValue());
			search.setAgtCd(univCd);
			List<Pr> prList = prMapper.findAll(search, Utils.dataTablesInputToPageable(input));
			List<PrDto.Response> data = modelMapper.map(prList, new TypeToken<List<PrDto.Response>>() {
			}.getType());

			output.setData(data);
			output.setRecordsFiltered(CollectionUtils.isEmpty(prList) ? 0 : prList.get(0).getFilteredCount());
			output.setRecordsTotal(prMapper.count(search.getAgtCd()));
		} catch (Exception e) {
			output.setError(e.getMessage());
		}
		return output;
	}

	public Page<Pr> getAllPr(PrDto.Search search, Pageable pageable) {
		search.setAgtCd(univCd);
		List<Pr> pr = prMapper.findAll(search, pageable);
		return new PageImpl<Pr>(pr, pageable, CollectionUtils.isEmpty(pr) ? 0 : pr.get(0).getFilteredCount());
	}

	public Pr getPr(String prNo) {
		return prMapper.findByPrNo(prNo);
	}

	public List<PrItem> getPrItems(String prNo) {
		return prItemMapper.findAllByPrNo(prNo);
	}

	public List<PrSrv> getPrServices(String prNo, String itemSeq) {
		return prSrvMapper.findAllByPrNoAndItemSeq(prNo, itemSeq);
	}

	public List<PrFile> getPrFiles(String prNo) {
		return prFileMapper.findAllByPrNo(prNo);
	}

	public PrFile getPrFile(String prNo, String fileSeq) {
		return prFileMapper.findByPrNoAndFileSeq(prNo, fileSeq);
	}

	public void saveFileFromLink(PrFileDum prFileDum) {
		try {

			log.info("SvrFileLink:  {}",  prFileDum.getSvrFileLink());
			log.info("destFile:  {}", prFileDum.getSvrFilePath() + "/" + prFileDum.getSvrFileNm());

			URL fileLink = new URL(prFileDum.getSvrFileLink());
			File destFile = new File(prFileDum.getSvrFilePath() + "/" + prFileDum.getSvrFileNm());

			if (!destFile.getParentFile().exists()) destFile.getParentFile().mkdirs();

			ReadableByteChannel rbc = Channels.newChannel(fileLink.openStream());

			FileOutputStream fos = new FileOutputStream(destFile);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);

			fos.close();
			rbc.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
}
