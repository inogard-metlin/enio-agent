package kr.co.inogard.enio.agent.service.event;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import kr.co.inogard.enio.agent.commons.util.Utils;
import kr.co.inogard.enio.agent.domain.event.CmmEvent;
import kr.co.inogard.enio.agent.domain.event.CmmEventDto;
import kr.co.inogard.enio.agent.mappers.event.EventMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EventService {

  @Autowired
  private EventMapper eventMapper;

  @Autowired
  private ModelMapper modelMapper;

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void create(CmmEvent evt) {
    eventMapper.add(evt);
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void updateRes(CmmEvent evt) {
    eventMapper.updateRes(evt);
  }

  public DataTablesOutput<CmmEventDto.Response> getAll(CmmEventDto.Search search, DataTablesInput input) {
    DataTablesOutput<CmmEventDto.Response> output = new DataTablesOutput<CmmEventDto.Response>();
    output.setDraw(input.getDraw());

    try {
      search.setValue(input.getSearch().getValue());
      List<CmmEvent> events = eventMapper.findAll(search, Utils.dataTablesInputToPageable(input));
      List<CmmEventDto.Response> data = modelMapper.map(events, new TypeToken<List<CmmEventDto.Response>>() {}.getType());

      output.setData(data);
      output.setRecordsFiltered(
          CollectionUtils.isEmpty(events) ? 0 : events.get(0).getFilteredCount());
      output.setRecordsTotal(eventMapper.count(search.getAgtCd()));
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      output.setError(e.getMessage());
    }
    return output;
  }

  public Page<CmmEvent> getAll(CmmEventDto.Search search, Pageable pageable) {
    List<CmmEvent> events = eventMapper.findAll(search, pageable);
    return new PageImpl<CmmEvent>(events, pageable,
        CollectionUtils.isEmpty(events) ? 0 : events.get(0).getFilteredCount());
  }

  public CmmEvent getEvent(String evtNo) {
    return eventMapper.findByEvtNo(evtNo);
  }

}
