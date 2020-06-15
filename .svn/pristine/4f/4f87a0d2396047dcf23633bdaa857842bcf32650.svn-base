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
import kr.co.inogard.enio.agent.commons.constant.SendStatus;
import kr.co.inogard.enio.agent.commons.handler.EnioFileHandler;
import kr.co.inogard.enio.agent.domain.pr.PrFile;
import kr.co.inogard.enio.agent.service.pr.PrService;

@Controller
@RequestMapping("/pr")
public class PrController {

  @Autowired
  private PrService prService;

  @Autowired
  private EnioFileHandler fileHandler;
  
  @Value("${enio.file.pds-dir}")
  private String pdsDir;

  @RequestMapping(method = RequestMethod.GET)
  public ModelAndView getPr() {
    ModelAndView model = new ModelAndView("pr/list");
    model.addObject("sendStatuses", SendStatus.values());
    return model;
  }

  @RequestMapping(value = "/{prNo}/files/{fileSeq}", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public void getPrFile(@PathVariable String prNo, @PathVariable String fileSeq) {
    PrFile prFile = prService.getPrFile(prNo, fileSeq);
    File file = new File(pdsDir + prFile.getSvrFilePath() + File.separator + prFile.getSvrFileNm());
    fileHandler.downloadFile(file, prFile.getCliFileNm());
  }

}
