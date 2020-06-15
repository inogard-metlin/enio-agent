package kr.co.inogard.enio.agent.mappers.item;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import kr.co.inogard.enio.agent.domain.item.ItemDto;
import kr.co.inogard.enio.agent.domain.item.ItemDum;

@Mapper
public interface ItemDumMapper {

  ItemDum findByErpItemCd(String erpItemCd);

  List<ItemDum> findAllNotSend();

  List<ItemDum> findAll(@Param("search") ItemDto.Search search, @Param("pageable") Pageable pageable);
  
  void add(ItemDum itemDum);

  void updateE4uItemCd(ItemDum itemDum);

  void updateE4uIfSt(ItemDum item);

  Long count(String agtCd);

}
