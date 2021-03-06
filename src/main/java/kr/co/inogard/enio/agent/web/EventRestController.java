package kr.co.inogard.enio.agent.web;

import java.util.List;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import kr.co.inogard.enio.agent.domain.event.CmmEvent;
import kr.co.inogard.enio.agent.domain.event.CmmEventDto;
import kr.co.inogard.enio.agent.service.event.EventService;

@RestController
@RequestMapping(value = {"/events", "/agent/v1/events"},
    produces = {"application/json;charset=UTF-8", "application/enio-json;charset=UTF-8"})
public class EventRestController {

  @Autowired
  private EventService eventService;

  @Autowired
  private ModelMapper modelMapper;
  
  @Value("${enio.univ-cd}")
  private String agtCd;

  @RequestMapping(params = {"draw", "start", "length"}, method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public DataTablesOutput<CmmEventDto.Response> getEvents(@Valid CmmEventDto.Search search, @Valid DataTablesInput input) {
	  search.setAgtCd(agtCd);
	  return eventService.getAll(search, input);
  }

  @RequestMapping(method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public PageImpl<CmmEventDto.Response> getEvents(@Valid CmmEventDto.Search search, Pageable pageable) {
	  search.setAgtCd(agtCd);
	  Page<CmmEvent> page = eventService.getAll(search, pageable);
	  List<CmmEventDto.Response> content = modelMapper.map(page.getContent(),
	  new TypeToken<List<CmmEventDto.Response>>() {}.getType());
	  return new PageImpl<CmmEventDto.Response>(content, pageable, page.getTotalElements());
  }

  @RequestMapping(value = "/{evtNo}", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public CmmEventDto.Response getEvent(@PathVariable String evtNo) {
    return modelMapper.map(eventService.getEvent(evtNo), CmmEventDto.Response.class);
  }
}
