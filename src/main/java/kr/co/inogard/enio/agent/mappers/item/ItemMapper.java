package kr.co.inogard.enio.agent.mappers.item;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import kr.co.inogard.enio.agent.domain.item.Item;
import kr.co.inogard.enio.agent.domain.item.ItemDto;

@Mapper
public interface ItemMapper {
  Item findByItemCd(String itemCd);

  List<Item> findAll(@Param("search") ItemDto.Search search, @Param("pageable") Pageable pageable);
  
  Long count(String agtCd);

  void add(Item item);
}
