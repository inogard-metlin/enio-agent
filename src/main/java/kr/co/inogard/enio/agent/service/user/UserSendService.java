package kr.co.inogard.enio.agent.service.user;

import java.util.Collections;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import kr.co.inogard.enio.agent.commons.constant.EnioMediaType;
import kr.co.inogard.enio.agent.commons.constant.RsltCd;
import kr.co.inogard.enio.agent.commons.constant.SendStatus;
import kr.co.inogard.enio.agent.domain.agent.Agent;
import kr.co.inogard.enio.agent.domain.agent.AgentConfig;
import kr.co.inogard.enio.agent.domain.user.User;
import kr.co.inogard.enio.agent.domain.user.UserDto;
import kr.co.inogard.enio.agent.domain.user.UserDum;
import kr.co.inogard.enio.agent.mappers.user.UserDumMapper;
import kr.co.inogard.enio.agent.mappers.user.UserMapper;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class UserSendService {

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private UserDumMapper userDumMapper;

  @Autowired
  private ModelMapper modelMapper;
  
  @Value("${enio.api.context-path}/${enio.api.version}${enio.api.uri-path.user}")
  private String userApiUriPath;
  
  @Autowired
  private RestTemplate restTemplate;
  
  @Autowired
  private AgentConfig agentConfig;

  public List<UserDum> getAllNotSend() {
    return userDumMapper.findAllNotSend();
  }

  public SendStatus send(List<UserDum> userList) {
    List<UserDto.CreateEntry> createUserList = modelMapper.map(userList, new TypeToken<List<UserDto.CreateEntry>>() {}.getType());
    UserDto.Create createUser = new UserDto.Create();
    createUser.setDatas(createUserList);

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType());
    httpHeaders.setAccept(Collections.singletonList(EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType()));
    
    HttpEntity<UserDto.Create> requestEntity = new HttpEntity<UserDto.Create>(createUser, httpHeaders);

    SendStatus status = SendStatus.SEND_FAIL;
    String url = agentConfig.getAgent().getApiConnUrl() + userApiUriPath;
    UserDto.Response res = restTemplate.postForObject(url, requestEntity, UserDto.Response.class);
    if (RsltCd.valueOf(res.getRsltCd()).isSuccess()) {
      List<UserDto.ResponseEntry> rcvList = res.getDatas();
      for (UserDto.ResponseEntry resEntry : rcvList) {
        this.sendComplete(resEntry);
      }
      status = SendStatus.SEND_COMPLETE;
    }

    log.info("Send User End");
    return status;
  }

  private void sendComplete(UserDto.ResponseEntry res) {
    UserDum userDum = new UserDum();
    userDum.setE4uUserCd(res.getE4uUserCd());
    userDum.setErpUserCd(res.getErpUserCd());
    userDumMapper.updateE4uUserCd(userDum);

    User user = new User();
    user.setUserCd(userDum.getE4uUserCd());
    user.setErpUserCd(userDum.getErpUserCd());
    userMapper.add(user);
  }

  public void updateSendStatus(List<UserDum> userDumList, SendStatus status) {
    String e4uIfSt = status.getCode();
    for (UserDum userDum : userDumList) {
      userDum.setE4uIfSt(e4uIfSt);
      userDumMapper.updateE4uIfSt(userDum);
    }
  }
}
