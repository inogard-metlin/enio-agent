package kr.co.inogard.enio.agent.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import kr.co.inogard.enio.agent.commons.constant.SendStatus;

@Controller
@RequestMapping("/send/items")
public class ItemSendController {

  @RequestMapping(method = RequestMethod.GET)
  public ModelAndView getSendItems() {
    ModelAndView model = new ModelAndView("send/items/list");
    model.addObject("sendStatuses", SendStatus.values());
    return model;
  }

}

