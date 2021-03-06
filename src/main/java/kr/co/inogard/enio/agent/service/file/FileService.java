package kr.co.inogard.enio.agent.service.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.co.inogard.enio.agent.commons.exception.EnioRunTimeException;
import kr.co.inogard.enio.agent.domain.file.CmmFile;
import kr.co.inogard.enio.agent.mappers.file.FileMapper;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class FileService {

	@Autowired
	private FileMapper fileMapper;

	@Value("${enio.file.pds-dir}")
	private String rootSavePath;
	
//	@Value("${enio.file.upload-dir}")
//	private String fileUploadPath;

	public CmmFile store(File uploadedFile, String docType) {
		CmmFile cmmFile = new CmmFile();
		cmmFile.setCliFileNm(uploadedFile.getName());
		cmmFile.setDocType(docType);
		cmmFile.setFileSz(new BigDecimal(uploadedFile.length()));

		fileMapper.add(cmmFile);
		try {
			this.store(cmmFile.getSvrFilePath() + "/" + cmmFile.getSvrFileNm(), uploadedFile);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new EnioRunTimeException(e);
		}

		return cmmFile;
	}

//	public String uploadAgtDumPrFile(List<MultipartFile> files) {
//		String srcFileName;
//		String srcFileNameExt;
//		String destFileName;
//		File destFile;
//		
//		for(MultipartFile file : files) {
//			if (file.isEmpty()) continue;
//			
//			srcFileName = FilenameUtils.getName(file.getOriginalFilename());			
//			srcFileNameExt = FilenameUtils.getExtension(srcFileName).toLowerCase();
//			
//			destFileName = srcFileName.substring(0, srcFileName.lastIndexOf(srcFileNameExt)-1);
//			destFile = new File(fileUploadPath + "/" + destFileName + "." + srcFileNameExt);				
//			if (destFile.exists()) {
//				destFile.delete();
//			}
//			
//			if (!destFile.getParentFile().exists()) {
//				destFile.getParentFile().mkdirs();
//			}			
//
//			try {
//				file.transferTo(destFile);
//			} catch (IllegalStateException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}			
//			
//		}
//		
//		return "success";
//	}
	
	public File retrieve(CmmFile cmmFile) {
		return this.retrieve(cmmFile.getSvrFilePath() + "/" + cmmFile.getSvrFileNm());
	}

	public File retrieve(String lpath) {
		return this.getPhysicalPath(lpath);
	}

	private void store(String logicalPath, File file) throws Exception {
		InputStream is = null;
		try {
			this.store(logicalPath, is = new FileInputStream(file));
		} catch (Exception ex) {
			throw ex;
		} finally {
			if (is != null)
				try {
					is.close();
				} catch (Exception iex) {
				}
		}
	}

	private void store(String logicalPath, InputStream is) throws Exception {
		File saveFile = getPhysicalPath(logicalPath);
		saveFile.getParentFile().mkdirs();

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(saveFile);
			copy(is, fos);
		} catch (Exception e) {
			throw e;
		} finally {
			if (fos != null)
				try {
					fos.close();
				} catch (Exception iex) {
				}
		}
	}

	private static void copy(InputStream is, OutputStream os) throws IOException {
		byte[] buffer = new byte[65536];
		int n;

		while ((n = is.read(buffer)) > 0)
			os.write(buffer, 0, n);
	}

	private File getPhysicalPath(String logicalPath) {
		StringBuffer sb = new StringBuffer();
		sb.append(this.rootSavePath);
		char c = logicalPath.charAt(0);
		if (!(c == '/' || c == '\\'))
			sb.append("/");
		sb.append(logicalPath);
		return new File(sb.toString());
	}
}
