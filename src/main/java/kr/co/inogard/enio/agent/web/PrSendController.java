package kr.co.inogard.enio.agent.web;

import java.io.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import kr.co.inogard.enio.agent.commons.constant.SendStatus;
import kr.co.inogard.enio.agent.commons.handler.EnioFileHandler;
import kr.co.inogard.enio.agent.domain.pr.PrFileDum;
import kr.co.inogard.enio.agent.service.pr.PrService;

@Controller
@RequestMapping("/send/pr")
public class PrSendController {

  @Autowired
  private PrService prService;

  @Autowired
  private EnioFileHandler fileHandler;

  @RequestMapping(method = RequestMethod.GET)
  public ModelAndView getSendPr() {
    ModelAndView model = new ModelAndView("/send/pr/list");
    model.addObject("sendStatuses", SendStatus.values());
    return model;
  }

  @RequestMapping(value = "/{erpPrNo}/files/{fileSeq}", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public void getSendPrFile(@PathVariable String erpPrNo, @PathVariable String fileSeq) {
    PrFileDum prFileDum = prService.getSendPrFile(erpPrNo, fileSeq);
    File file = new File(prFileDum.getSvrFilePath() + File.separator + prFileDum.getSvrFileNm());
    fileHandler.downloadFile(file, prFileDum.getSvrFileNm());
  }

}
