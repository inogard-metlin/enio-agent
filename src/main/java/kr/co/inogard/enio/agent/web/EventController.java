package kr.co.inogard.enio.agent.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import kr.co.inogard.enio.agent.commons.constant.EvtIOType;
import kr.co.inogard.enio.agent.commons.constant.EvtSt;

@Controller
@RequestMapping("/events")
public class EventController {

  @RequestMapping(method = RequestMethod.GET)
  public ModelAndView getEvents() {
    ModelAndView model = new ModelAndView("events/list");
    model.addObject("evtIOTypes", EvtIOType.values());
    model.addObject("evtSts", EvtSt.values());
    return model;
  }
}
