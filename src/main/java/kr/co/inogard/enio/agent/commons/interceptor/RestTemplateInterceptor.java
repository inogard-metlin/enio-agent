package kr.co.inogard.enio.agent.commons.interceptor;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.fileupload.MultipartStream;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.inogard.enio.agent.commons.constant.EnioHeader;
import kr.co.inogard.enio.agent.commons.constant.EnioMediaType;
import kr.co.inogard.enio.agent.commons.constant.EnioServerType;
import kr.co.inogard.enio.agent.commons.constant.EvtIOType;
import kr.co.inogard.enio.agent.commons.constant.EvtSt;
import kr.co.inogard.enio.agent.commons.constant.RsltCd;
import kr.co.inogard.enio.agent.commons.constant.ScheKind;
import kr.co.inogard.enio.agent.commons.util.CryptoUtil;
import kr.co.inogard.enio.agent.commons.util.Utils;
import kr.co.inogard.enio.agent.domain.agent.Agent;
import kr.co.inogard.enio.agent.domain.agent.AgentConfig;
import kr.co.inogard.enio.agent.domain.event.CmmEvent;
import kr.co.inogard.enio.agent.domain.event.CmmEventCmdParam;
import kr.co.inogard.enio.agent.domain.event.CmmEventReqContent;
import kr.co.inogard.enio.agent.domain.event.CmmEventResContent;
import kr.co.inogard.enio.agent.service.event.EventService;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {

  @Autowired
  private AgentConfig agentConfig;

  @Autowired
  private EventService eventService;

  @Autowired
  private CryptoUtil cryptoUtil;

  @Autowired
  private ObjectMapper objectMapper;

  @Override
  public ClientHttpResponse intercept(HttpRequest request, byte[] body,
      ClientHttpRequestExecution execution) throws IOException {
    log.info("============================ Request Logging START ============================");

    Agent agt = agentConfig.getAgent();
    String agtCd = agt.getAgtCd();
    String cryptoKey = cryptoUtil.getCryptoKey();
    MediaType enioJsonMediaType = EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType();

    String newEvtNo = null;
    String evtCmdParam = null;
    String reqCntn = null;
    String rsltCd = null;
    String rsltMsg = null;
    String rcvCntn = null;

    try {
      HttpHeaders reqHeaders = request.getHeaders();
      reqHeaders.add(EnioHeader.EnioServerType.getName(), EnioServerType.AGENT.name());
      
      Map<String, String> headers = request.getHeaders().toSingleValueMap();
      String uri = request.getURI().toString();
      String method = request.getMethod().toString();
      MediaType contentType = request.getHeaders().getContentType();
      String bodyStr = null;

      log.info("Request uri : {}", uri);
      log.info("Request mehtod : {}", method);
      log.info("Request headers : {}", headers);
      log.debug("Request contentType : {}", contentType);

      boolean isApplicationEnioJson =
          enioJsonMediaType.isCompatibleWith(request.getHeaders().getContentType());
      boolean isMultipartFormData =
          MediaType.MULTIPART_FORM_DATA.isCompatibleWith(request.getHeaders().getContentType());

      log.info("isApplicationEnioJson : {}", isApplicationEnioJson);
      log.info("isMultipartFormData : {}", isMultipartFormData);

      List<Map<String, String>> partHeaderList = new ArrayList<Map<String, String>>();
      if (isApplicationEnioJson || isMultipartFormData) {
        String encryptedEnioJsonStr = null;
        if (isMultipartFormData) {
          InputStream is = new ByteArrayInputStream(body);
          String boundary = request.getHeaders().getContentType().getParameter("boundary");
          boundary = (boundary != null ? boundary : "");
          MultipartStream multipartStream =
              new MultipartStream(is, boundary.getBytes(), 2048, null);

          boolean nextPart = multipartStream.skipPreamble();
          while (nextPart) {
            String multipartHeaders = multipartStream.readHeaders();
            log.debug("Multipart headers : {}", multipartHeaders);

            partHeaderList.add(Utils.multipartHeadersToMap(multipartHeaders));

            ByteArrayOutputStream output = null;
            try {
              output = new ByteArrayOutputStream();
              multipartStream.readBodyData(output);
              String partBodyStr = new String(output.toByteArray());
              if (multipartHeaders
                  .indexOf(EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getValue()) != -1) {
                encryptedEnioJsonStr = partBodyStr;
              }
              nextPart = multipartStream.readBoundary();
            } finally {
              IOUtils.closeQuietly(output);
            }
          }
        } else {
          encryptedEnioJsonStr = new String(body);
        }

        log.debug("encryptedEnioJsonStr : {}", encryptedEnioJsonStr);
        if (encryptedEnioJsonStr != null) {
          bodyStr = cryptoUtil.decryptString(cryptoKey, encryptedEnioJsonStr);
        }
      } else {
        bodyStr = new String(body);
      }

      Object bodyObj = null;
      try {
        bodyObj = objectMapper.readValue(bodyStr, Object.class);
      } catch (Exception e) {
        bodyObj = bodyStr;
      }

      CmmEventCmdParam cmmEventCmdParam = new CmmEventCmdParam();
      cmmEventCmdParam.setUri(uri);
      cmmEventCmdParam.setHttpMethod(method);

      CmmEventReqContent cmmEventReqContent = new CmmEventReqContent();
      cmmEventReqContent.setHeaders(headers);
      cmmEventReqContent.setPartHeaderList(partHeaderList);
      cmmEventReqContent.setContent(bodyObj);

      evtCmdParam = objectMapper.writeValueAsString(cmmEventCmdParam);
      reqCntn = objectMapper.writeValueAsString(cmmEventReqContent);

      log.debug("evtCmdParam : {}", evtCmdParam);
      log.debug("reqCntn : {}", reqCntn);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      StringWriter errors = new StringWriter();
      e.printStackTrace(new PrintWriter(errors));
      rsltCd = RsltCd.ERR0000.name();
      rsltMsg = e.getMessage();
      rcvCntn = errors.toString();

      if (e instanceof IOException) {
        throw (IOException) e;
      } else {
        throw new RuntimeException(e);
      }
    } finally {
      try {
        CmmEvent evt = new CmmEvent();
        evt.setEvtIoType(EvtIOType.OUT.name());
        evt.setEvtCmdParam(evtCmdParam);
        evt.setEvtSt(EvtSt.W.name());
        evt.setReqCntn(reqCntn);
        evt.setAgtCd(agtCd);
        evt.setScheKind(ScheKind.S.name());

        eventService.create(evt);
        newEvtNo = evt.getEvtNo();
        log.info("newEvtNo : {}", newEvtNo);

        if (StringUtils.hasText(newEvtNo) && StringUtils.hasText(rsltCd)
            && RsltCd.ERR0000.name().equals(rsltCd)) {
          CmmEvent resEvt = new CmmEvent();
          resEvt.setEvtNo(newEvtNo);
          resEvt.setEvtSt(EvtSt.C.name());
          resEvt.setRsltCd(rsltCd);
          resEvt.setRsltMsg(rsltMsg);
          resEvt.setRcvCntn(rcvCntn);
          eventService.updateRes(evt);
        }
      } catch (Exception e) {
        log.error("Request Logging Data Input Exception", e);
      }
      log.info("============================ Request Logging END ============================");
    }

    ClientHttpResponse response = null;
    try {
      log.info("============================ Response Logging START ============================");
      response = execution.execute(request, body);
      
      StringBuilder inputStringBuilder = new StringBuilder();
      BufferedReader bufferedReader =
          new BufferedReader(new InputStreamReader(response.getBody(), "UTF-8"));
      String line = bufferedReader.readLine();
      while (line != null) {
        inputStringBuilder.append(line);
        inputStringBuilder.append('\n');
        line = bufferedReader.readLine();
      }

      Map<String, String> headers = response.getHeaders().toSingleValueMap();
      int status = response.getRawStatusCode();
      MediaType contentType = response.getHeaders().getContentType();
      String bodyStr = inputStringBuilder.toString();

      log.info("Response headers : {}", headers);
      log.info("Response statusCode : {}", status);
      log.info("Response contentType : {}", contentType);
      log.debug("Response body : {}", bodyStr);

      rsltCd = RsltCd.UNKNOWN.name();
      rsltMsg = RsltCd.UNKNOWN.getCodeNm();
      if (contentType != null) {
        boolean isApplicationEnioJson =
            enioJsonMediaType.isCompatibleWith(response.getHeaders().getContentType());
        log.info("isApplicationEnioJson : {}", isApplicationEnioJson);

        if (isApplicationEnioJson) {
          bodyStr = cryptoUtil.decryptString(cryptoKey, bodyStr);
          log.debug("decryptedEnioJsonStr : {}", bodyStr);
          Map<String, Object> resBodyMap =
              objectMapper.readValue(bodyStr, new TypeReference<Map<String, Object>>() {});
          rsltCd = (String) resBodyMap.get("rsltCd");
          rsltMsg = (String) resBodyMap.get("rsltMsg");
        }
      }

      Object bodyObj = null;
      try {
        bodyObj = objectMapper.readValue(bodyStr, Object.class);
      } catch (Exception e) {
        bodyObj = bodyStr;
      }

      CmmEventResContent cmmEventResContent = new CmmEventResContent();
      cmmEventResContent.setHeaders(headers);
      cmmEventResContent.setStatus(status);
      cmmEventResContent.setContent(bodyObj);

      rcvCntn = objectMapper.writeValueAsString(cmmEventResContent);
      log.debug("rcvCntn : {}", rcvCntn);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      StringWriter errors = new StringWriter();
      e.printStackTrace(new PrintWriter(errors));
      rsltCd = RsltCd.ERR1000.name();
      rsltMsg = e.getMessage();
      rcvCntn = errors.toString();

      if (e instanceof IOException) {
        throw (IOException) e;
      } else {
        throw new RuntimeException(e);
      }
    } finally {
      try {
        log.info("newEvtNo : {}", newEvtNo);
        if (StringUtils.hasText(newEvtNo)) {
          CmmEvent evt = new CmmEvent();
          evt.setEvtNo(newEvtNo);
          evt.setEvtSt(EvtSt.C.name());
          evt.setRsltCd(rsltCd);
          evt.setRsltMsg(rsltMsg);
          evt.setRcvCntn(rcvCntn);
          eventService.updateRes(evt);
        }
      } catch (Exception e) {
        log.error("Response Logging Data Input Exception", e);
      }
      log.info("============================ Response Logging END ============================");
    }
    return response;
  }
}