package kr.co.inogard.enio.agent.service.quot;

import java.io.File;
import java.util.List;

import javax.mail.internet.MimeUtility;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import kr.co.inogard.enio.agent.commons.exception.EnioRunTimeException;
import kr.co.inogard.enio.agent.commons.handler.EnioFileHandler;
import kr.co.inogard.enio.agent.domain.file.CmmFile;
import kr.co.inogard.enio.agent.domain.quot.Quot;
import kr.co.inogard.enio.agent.domain.quot.QuotDto;
import kr.co.inogard.enio.agent.domain.quot.QuotFile;
import kr.co.inogard.enio.agent.domain.quot.QuotItem;
import kr.co.inogard.enio.agent.domain.quot.QuotSrv;
import kr.co.inogard.enio.agent.mappers.quot.QuotFileMapper;
import kr.co.inogard.enio.agent.mappers.quot.QuotItemMapper;
import kr.co.inogard.enio.agent.mappers.quot.QuotMapper;
import kr.co.inogard.enio.agent.mappers.quot.QuotSrvMapper;
import kr.co.inogard.enio.agent.service.file.FileService;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class QuotService {

	@Autowired
	private QuotMapper quotMapper;

	@Autowired
	private QuotFileMapper quotFileMapper;

	@Autowired
	private QuotItemMapper quotItemMapper;

	@Autowired
	private QuotSrvMapper quotSrvMapper;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private EnioFileHandler enioFileHandler;

	@Autowired
	private FileService fileService;

	public Quot create(QuotDto.Create quotDto, List<MultipartFile> files) {
		Quot quot = modelMapper.map(quotDto, Quot.class);
		quotMapper.add(quot);

		List<QuotItem> quotItemList = quot.getItemList();
		List<QuotSrv> quotSrvList = null;
		for (QuotItem quotItem : quotItemList) {
			quotItemMapper.add(quotItem);

			quotSrvList = quotItem.getSrvList();
			for (QuotSrv quotSrv : quotSrvList) {
				quotSrvMapper.add(quotSrv);
			}
		}

		if (!CollectionUtils.isEmpty(files)) {
			File ctmTempDir = enioFileHandler.createTempDirectory();
			try {
				for (MultipartFile multipartFile : files) {
					log.debug("multipartFile name : {}", multipartFile.getName());
					log.debug("multipartFile originalFilename : {}", MimeUtility.decodeText(multipartFile.getOriginalFilename()));

					File file = new File(ctmTempDir, MimeUtility.decodeText(multipartFile.getOriginalFilename()));
					multipartFile.transferTo(file);

					File uploadedFile = file;
					String docType = "22";
					CmmFile cmmFile = fileService.store(uploadedFile, docType);
					String fileNo = cmmFile.getFileNo();

					QuotFile quotFile = new QuotFile();
					quotFile.setCusCd(quot.getCusCd());
					quotFile.setFileNo(fileNo);
					quotFile.setQuotRev(quot.getQuotRev());
					quotFile.setRfqNo(quot.getRfqNo());
					quotFileMapper.addQuotFile(quotFile);
				}

			} catch (Exception e) {
				throw new EnioRunTimeException(e);
			} finally {
				enioFileHandler.cleanUpDirectory(ctmTempDir);
			}
		}

		return quot;
	}

}
