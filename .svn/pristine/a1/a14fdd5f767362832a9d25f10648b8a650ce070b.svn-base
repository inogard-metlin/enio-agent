package kr.co.inogard.enio.agent.web;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import java.io.IOException;
import java.util.ArrayList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import kr.co.inogard.enio.agent.commons.constant.RsltCd;
import kr.co.inogard.enio.agent.domain.cus.Customer;
import kr.co.inogard.enio.agent.domain.cus.CustomerDto;
import kr.co.inogard.enio.agent.service.cus.CusService;


@RestController
@RequestMapping("/agent/v1/cus")
public class CusRestController {

  @Autowired
  private CusService cusService;

  @Autowired
  private ModelMapper modelMapper;

  @RequestMapping(method = POST)
  @ResponseStatus(HttpStatus.CREATED)
  public CustomerDto.Response create(@RequestBody CustomerDto.Create creCus) throws IOException {
    CustomerDto.Response res = new CustomerDto.Response();
    res.setRsltCd(RsltCd.SUC0000.name());
    res.setRsltMsg(RsltCd.SUC0000.getCodeNm());
    res.setDatas(new ArrayList<CustomerDto.ResponseEntry>());

    for (CustomerDto.CreateEntry cr : creCus.getDatas()) {
      Customer cus = modelMapper.map(cr, Customer.class);
      cusService.create(cus);
      CustomerDto.ResponseEntry resEntry = new CustomerDto.ResponseEntry();
      resEntry.setErpCusCd(cus.getCusCd());
      resEntry.setE4uCusCd(cr.getCusCd());
      res.getDatas().add(resEntry);
    }

    return res;
  }
}
