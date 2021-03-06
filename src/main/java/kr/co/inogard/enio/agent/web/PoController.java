package kr.co.inogard.enio.agent.web;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import kr.co.inogard.enio.agent.commons.handler.EnioFileHandler;
import kr.co.inogard.enio.agent.domain.po.PoFile;
import kr.co.inogard.enio.agent.service.po.PoService;

@Controller
@RequestMapping("/po")
public class PoController {

	@Autowired
	private PoService poService;

	@Autowired
	private EnioFileHandler fileHandler;

	@Value("${enio.file.pds-dir}")
	private String pdsDir;

	@RequestMapping(method = RequestMethod.GET)
  public ModelAndView getPo() {
    ModelAndView model = new ModelAndView("po/list");
    
		return model;
	}

	@RequestMapping(value = "/{poNo}/files/{fileSeq}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public void getPoFile(@PathVariable String poNo, @PathVariable String fileSeq) {
		PoFile poFile = poService.getPoFile(poNo, fileSeq);
		File file = new File(pdsDir + poFile.getSvrFilePath() + File.separator + poFile.getSvrFileNm());
		fileHandler.downloadFile(file, poFile.getCliFileNm());
	}

}
