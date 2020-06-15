package kr.co.inogard.enio.agent.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public ModelAndView index() {
    ModelAndView model = new ModelAndView("index");
    return model;
  }
}
