package kr.co.inogard.enio.agent.po;

import java.text.ParseException;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.co.inogard.enio.agent.Application;
import kr.co.inogard.enio.agent.domain.po.Po;
import kr.co.inogard.enio.agent.domain.po.PoDto;
import kr.co.inogard.enio.agent.domain.po.PoFile;
import kr.co.inogard.enio.agent.domain.po.PoItem;
import kr.co.inogard.enio.agent.domain.po.PoSrv;
import kr.co.inogard.enio.agent.mappers.po.PoFileMapper;
import kr.co.inogard.enio.agent.mappers.po.PoItemMapper;
import kr.co.inogard.enio.agent.mappers.po.PoMapper;
import kr.co.inogard.enio.agent.mappers.po.PoSrvMapper;
import kr.co.inogard.enio.agent.service.po.PoService;
import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
@Slf4j
public class PoServiceTest {

  @Autowired
  private PoService poService;

  @Autowired
  private PoMapper poMapper;

  @Autowired
  private PoFileMapper poFileMapper;

  @Autowired
  private PoItemMapper poItemMapper;

  @Autowired
  private PoSrvMapper poSrvMapper;

  @Autowired
  private ModelMapper modelMapper;

  @Test
  public void create() throws ParseException {
	  List<MultipartFile> files = null;  
    Po po = poService.create(PoTestFixture.getPoCreateFixture(), files);
    log.debug("Create Po No : {}", po);
  }

  @Test
  public void createPoTest() throws ParseException {
    Po po = modelMapper.map(PoTestFixture.getPoCreateFixture(), Po.class);

    poMapper.add(po);

    log.debug("============ CREATE PO SUCCESS!!!! ============");
  }

  @Test
  public void createPoFileTest() throws ParseException {
    PoDto.Create poDto = PoTestFixture.getPoCreateFixture();

    //List<PoFile> createPoFileList = modelMapper.map(poDto.getPoFile(), new TypeToken<List<PoFile>>() {}.getType());

    log.debug("============ CREATE PO FILE SUCCESS!!!! ============");
  }

  @Test
  public void createPoItemTest() throws ParseException {
    PoDto.Create poDto = PoTestFixture.getPoCreateFixture();

    List<PoItem> createPoItemList =
        modelMapper.map(poDto.getPoItem(), new TypeToken<List<PoItem>>() {}.getType());
    for (PoItem xPoItem : createPoItemList) {
      poItemMapper.add(xPoItem);
    }

    log.debug("============ CREATE PO ITEM SUCCESS!!!! ============");
  }

  @Test
  public void createPoSrvTest() throws ParseException {
    PoDto.Create poDto = PoTestFixture.getPoCreateFixture();

    List<PoItem> createPoItemList =
        modelMapper.map(poDto.getPoItem(), new TypeToken<List<PoItem>>() {}.getType());
    List<PoSrv> createPoSrvList = null;
    for (PoItem xPoItem : createPoItemList) {
      // poItemMapper.addPoItem(xPoItem);

      createPoSrvList =
          modelMapper.map(xPoItem.getSrvList(), new TypeToken<List<PoSrv>>() {}.getType());
      for (PoSrv xPoSrv : createPoSrvList) {
        poSrvMapper.add(xPoSrv);
      }
    }

    log.debug("============ CREATE PO SRV SUCCESS!!!! ============");
  }
}


