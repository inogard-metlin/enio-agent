package kr.co.inogard.enio.agent.web;

import java.util.List;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.annotation.JsonView;
import kr.co.inogard.enio.agent.domain.item.ItemDto;
import kr.co.inogard.enio.agent.domain.item.ItemDum;
import kr.co.inogard.enio.agent.service.item.ItemService;

@RestController
@RequestMapping(value = {"/send/items", "/agent/v1/send/items"},
    produces = {"application/json;charset=UTF-8", "application/enio-json;charset=UTF-8"})
public class ItemSendRestController {

  @Autowired
  private ItemService itemService;

  @Autowired
  private ModelMapper modelMapper;

  // @JsonView(DataTablesOutput.View.class)
  // @JsonView 사용시 아래에 경우 ItemDto.Response 에 각 필드에 @JsonView(DataTablesOutput.View.class)를 선언해야 함.
  // 여기에선 ItemDto.Response 객체에 응답 할 필드만 필터링 했기 때문에 여기서는 사용 하지 않음.
  @RequestMapping(params = {"draw", "start", "length"}, method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  @JsonView(ItemDto.Views.PublicView.class)
  public DataTablesOutput<ItemDto.Response> getSendItems(@Valid ItemDto.Search search,
      @Valid DataTablesInput input) {
    return itemService.getAllSendItems(search, input);
  }

  @RequestMapping(method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  @JsonView(ItemDto.Views.PublicView.class)
  public PageImpl<ItemDto.Response> getSendItems(@Valid ItemDto.Search search, Pageable pageable) {
    Page<ItemDum> page = itemService.getAllSendItems(search, pageable);
    List<ItemDto.Response> content =
        modelMapper.map(page.getContent(), new TypeToken<List<ItemDto.Response>>() {}.getType());
    return new PageImpl<ItemDto.Response>(content, pageable, page.getTotalElements());
  }
}
