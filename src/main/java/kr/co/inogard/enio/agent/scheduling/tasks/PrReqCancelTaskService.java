package kr.co.inogard.enio.agent.scheduling.tasks;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import kr.co.inogard.enio.agent.commons.constant.CnclFlag;
import kr.co.inogard.enio.agent.domain.pr.PrDum;
import kr.co.inogard.enio.agent.mappers.pr.PrDumMapper;
import kr.co.inogard.enio.agent.service.pr.PrReqCancelSendService;
import kr.co.inogard.enio.agent.service.rtlog.RtLogService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PrReqCancelTaskService {

  @Autowired
  private PrDumMapper prDumMapper;

  @Autowired
  private PrReqCancelSendService prReqCancelSendService;

  @Autowired
  private RtLogService rtLogService;
  
  public void execute() {
    log.info("========================= Send Cancel Pr START =========================");
    List<PrDum> prDumList = prDumMapper.findAllReqCancelNotSend();
    if (CollectionUtils.isEmpty(prDumList)) {
      log.info("No data to send");
      return;
    }

    prReqCancelSendService.deleteByNotSendPr(prDumList);

    for (PrDum prDum : prDumList) {
      prReqCancelSendService.updateCnclFlag(prDum.getErpPrNo(), CnclFlag.CW.name(),
          prDum.getCnclReqDt(), null, CnclFlag.CW.getCodeNm());

      try {
        prReqCancelSendService.send(prDum);
      } catch (RuntimeException e) {
        prReqCancelSendService.updateCnclFlag(prDum.getErpPrNo(), CnclFlag.CWE.name(),
            prDum.getCnclReqDt(), new Date(), CnclFlag.CWE.getCodeNm());
        rtLogService.create(e);
        throw e;
      }
    }
    log.info("========================= Send Cancel Pr END =========================");
  }
}
