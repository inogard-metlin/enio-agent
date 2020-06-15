package kr.co.inogard.enio.agent.commons.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.parameter.ColumnParameter;
import org.springframework.data.jpa.datatables.parameter.OrderParameter;
import org.springframework.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Utils {

  public static String getStringFromInputStream(InputStream is) {
    BufferedReader br = null;
    StringBuilder sb = new StringBuilder();

    String line;
    try {
      br = new BufferedReader(new InputStreamReader(is));
      while ((line = br.readLine()) != null) {
        sb.append(line);
      }
    } catch (IOException e) {
      log.error(e.getMessage(), e);
    } finally {
      if (br != null) {
        try {
          br.close();
        } catch (IOException e) {
          log.error(e.getMessage(), e);
        }
      }
    }
    return sb.toString();
  }

  public static Map<String, String> multipartHeadersToMap(String multipartHeaders) {
    Map<String, String> headers = new HashMap<String, String>();
    if (StringUtils.hasText(multipartHeaders)) {
      String[] headersArr = multipartHeaders.split("\r\n");
      for (String header : headersArr) {
        String[] headersMapArr = header.split(":");
        if (headersMapArr.length > 1) {
          String k = header.split(":")[0];
          String v = header.split(":")[1];

          if (StringUtils.hasText(k)) {
            k = k.trim();
          }

          if (StringUtils.hasText(v)) {
            v = v.trim();
          }

          headers.put(k, v);
        }
      }
    }
    return headers;
  }

  public static Pageable dataTablesInputToPageable(DataTablesInput input) {
    OrderParameter order = input.getOrder().get(0);
    ColumnParameter column = input.getColumns().get(order.getColumn());

    if (column.getOrderable()) {
      String sortColumn = column.getName();
      Direction sortDirection = Direction.fromString(order.getDir());

      return new PageRequest(input.getStart() / input.getLength(), input.getLength(), sortDirection,
          sortColumn);
    } else {
      return new PageRequest(input.getStart() / input.getLength(), input.getLength());
    }
  }

  public static String getBrowser(String userAgent) {
    log.info("userAgent : {}", userAgent);
    if (userAgent.contains("Trident")) {
      return "MSIE";
    } else if (userAgent.contains("Chrome")) {
      return "Chrome";
    } else if (userAgent.contains("Opera")) {
      return "Opera";
    } else if (userAgent.contains("Safari")) {
      return "Safari";
    }
    return "Firefox";
  }

  public static String getFileNameByBrower(String userAgent, String fileName) {
    String browser = getBrowser(userAgent);
    log.info("brower : {}", browser);
    String encodedFilename = fileName;
    try {
      if (browser.equals("MSIE")) {
        encodedFilename = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
      } else if (browser.equals("Firefox")) {
        encodedFilename = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
      } else if (browser.equals("Opera")) {
        encodedFilename = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
      } else if (browser.equals("Chrome")) {
        encodedFilename = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
      }
    } catch (UnsupportedEncodingException e) {
      log.error(e.getMessage(), e);
    }
    return encodedFilename;
  }
}
