package kr.co.inogard.enio.agent.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/bid")
public class BidController {

  @RequestMapping(value = "/rfq", method = RequestMethod.GET)
  public ModelAndView getRfq() {
    ModelAndView model = new ModelAndView("bid/rfq/list");
    return model;
  }
  
}