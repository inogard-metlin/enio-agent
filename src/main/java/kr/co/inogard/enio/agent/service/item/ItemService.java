package kr.co.inogard.enio.agent.service.item;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import kr.co.inogard.enio.agent.commons.util.Utils;
import kr.co.inogard.enio.agent.domain.item.Item;
import kr.co.inogard.enio.agent.domain.item.ItemDto;
import kr.co.inogard.enio.agent.domain.item.ItemDum;
import kr.co.inogard.enio.agent.mappers.item.ItemDumMapper;
import kr.co.inogard.enio.agent.mappers.item.ItemMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ItemService {

  @Autowired
  private ItemDumMapper itemDumMapper;
  
  @Autowired
  private ItemMapper itemMapper;

  @Autowired
  private ModelMapper modelMapper;

  @Value("${enio.univ-cd}")
  private String agtCd;  
  
  public DataTablesOutput<ItemDto.Response> getAllSendItems(ItemDto.Search search, DataTablesInput input) {
    DataTablesOutput<ItemDto.Response> output = new DataTablesOutput<ItemDto.Response>();
    output.setDraw(input.getDraw());

    try {
      search.setValue(input.getSearch().getValue());
      search.setAgtCd(agtCd);
      List<ItemDum> items = itemDumMapper.findAll(search, Utils.dataTablesInputToPageable(input));
      List<ItemDto.Response> data = modelMapper.map(items, new TypeToken<List<ItemDto.Response>>() {}.getType());

      output.setData(data);
      output.setRecordsFiltered(CollectionUtils.isEmpty(items) ? 0 : items.get(0).getFilteredCount());
      output.setRecordsTotal(itemDumMapper.count(agtCd));
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      output.setError(e.getMessage());
    }
    return output;
  }

  public Page<ItemDum> getAllSendItems(ItemDto.Search search, Pageable pageable) {
	search.setAgtCd(agtCd);
    List<ItemDum> items = itemDumMapper.findAll(search, pageable);
    return new PageImpl<ItemDum>(items, pageable, CollectionUtils.isEmpty(items) ? 0 : items.get(0).getFilteredCount());
  }

  public ItemDum getSendItem(String erpItemCd) {
    return itemDumMapper.findByErpItemCd(erpItemCd);
  }
  
  public DataTablesOutput<ItemDto.Response> getAllItems(ItemDto.Search search,
      DataTablesInput input) {
    DataTablesOutput<ItemDto.Response> output = new DataTablesOutput<ItemDto.Response>();
    output.setDraw(input.getDraw());

    try {
      search.setValue(input.getSearch().getValue());
      search.setAgtCd(agtCd);
      List<Item> items = itemMapper.findAll(search, Utils.dataTablesInputToPageable(input));
      List<ItemDto.Response> data =
          modelMapper.map(items, new TypeToken<List<ItemDto.Response>>() {}.getType());

      output.setData(data);
      output
          .setRecordsFiltered(CollectionUtils.isEmpty(items) ? 0 : items.get(0).getFilteredCount());
      output.setRecordsTotal(itemMapper.count(agtCd));
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      output.setError(e.getMessage());
    }
    return output;
  }

  public Page<Item> getAllItems(ItemDto.Search search, Pageable pageable) {
	  search.setAgtCd(agtCd);
	  List<Item> items = itemMapper.findAll(search, pageable);
	  return new PageImpl<Item>(items, pageable,
        CollectionUtils.isEmpty(items) ? 0 : items.get(0).getFilteredCount());
  }

  public Item getItem(String itemCd) {
    return itemMapper.findByItemCd(itemCd);
  }
}
