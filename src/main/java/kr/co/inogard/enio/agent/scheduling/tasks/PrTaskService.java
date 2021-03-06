package kr.co.inogard.enio.agent.scheduling.tasks;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.inogard.enio.agent.commons.constant.SendStatus;
import kr.co.inogard.enio.agent.domain.dept.DeptDum;
import kr.co.inogard.enio.agent.domain.pr.PrDum;
import kr.co.inogard.enio.agent.domain.user.UserDum;
import kr.co.inogard.enio.agent.service.dept.DeptSendService;
import kr.co.inogard.enio.agent.service.pr.PrSendService;
import kr.co.inogard.enio.agent.service.rtlog.RtLogService;
import kr.co.inogard.enio.agent.service.user.UserSendService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PrTaskService {

  @Autowired
  private PrSendService prSendService;

  @Autowired
  private DeptSendService deptSendService;

  @Autowired
  private UserSendService userSendService;

  @Autowired
  private RtLogService rtLogService;

  public void execute() {
    log.info("========================= Send Pr START =========================");
    List<PrDum> notSendPrDumList = prSendService.getPrNotSend();
    if (notSendPrDumList.size() < 1) {
      log.info("No data to send");
      return;
    }

    log.info("Not send pr dummy count : {}", notSendPrDumList.size());

    for (PrDum prDum : notSendPrDumList) {
      ArrayList<DeptDum> sendDeptList = new ArrayList<DeptDum>();
      ArrayList<UserDum> sendUserList = new ArrayList<UserDum>();

      prSendService.checkSendPr(prDum.getErpPrNo(), sendDeptList, sendUserList);

      SendStatus status = SendStatus.SEND_READY;
      if (sendDeptList.size() > 0) {
        log.info("Start send depts count : {}", sendDeptList.size());

        status = SendStatus.SEND_WORK;
        deptSendService.updateSendStatus(sendDeptList, status);

        try {
          status = deptSendService.send(sendDeptList);
          deptSendService.updateSendStatus(sendDeptList, status);
        } catch (RuntimeException e) {
          status = SendStatus.SEND_WORK_ERR;
          deptSendService.updateSendStatus(sendDeptList, status);
          rtLogService.create(e);
          throw e;
        }
        log.info("End send depts");
      }

      if (!status.isOk())
        continue;

      status = SendStatus.SEND_READY;
      if (sendUserList.size() > 0) {
        log.info("Start send users count : {}", sendUserList.size());
        status = SendStatus.SEND_WORK;
        userSendService.updateSendStatus(sendUserList, status);
        try {
          status = userSendService.send(sendUserList);
          userSendService.updateSendStatus(sendUserList, status);
        } catch (RuntimeException e) {
          status = SendStatus.SEND_WORK_ERR;
          userSendService.updateSendStatus(sendUserList, status);
          rtLogService.create(e);
          throw e;
        }
        log.info("End send users");
      }

      if (!status.isOk())
        continue;

      status = SendStatus.SEND_WORK;
      prSendService.updateSendStatus(prDum, status);

      try {
        status = prSendService.send(prDum.getErpPrNo());
        prSendService.updateSendStatus(prDum, status);
      } catch (RuntimeException e) {
        status = SendStatus.SEND_WORK_ERR;
        prSendService.updateSendStatus(prDum, status);
        rtLogService.create(e);
        throw e;
      }
    } // for
    log.info("========================= Send Pr END =========================");
  }
}
