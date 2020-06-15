package kr.co.inogard.enio.agent.service.cus;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.inogard.enio.agent.domain.cus.Customer;
import kr.co.inogard.enio.agent.mappers.cus.CusMapper;

@Service
@Transactional
public class CusService {

  @Autowired
  private CusMapper cusMapper;

  public void create(Customer cus) {
    Customer oldCus = cusMapper.findByCusCd(cus.getCusCd());
    if (oldCus == null) {
      cusMapper.add(cus);
    } else {
      cusMapper.update(cus);
    }
  }
}