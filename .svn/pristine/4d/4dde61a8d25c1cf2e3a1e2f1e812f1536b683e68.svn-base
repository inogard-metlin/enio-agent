package kr.co.inogard.enio.agent.commons.handler;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import kr.co.inogard.enio.agent.commons.exception.EnioRunTimeException;
import kr.co.inogard.enio.agent.commons.util.Utils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EnioFileHandler {

  @Value("${enio.file.temp-dir}")
  private String tempDir;

  public File createTempDirectory() {
    File file = new File(tempDir + File.separator + String.valueOf(System.currentTimeMillis()));
    if (!makeDirs(file)) {
      throw new EnioRunTimeException("Failed to make directory in temp directory");
    }
    return file;
  }

  public void cleanUpDirectory(File dir) {
    if (dir == null || !dir.isDirectory()) {
      return;
    }

    File[] fileList = dir.listFiles();
    if (dir.listFiles().length < 1) {
      deleteFile(dir);
    } else {
      for (File f : fileList) {
        deleteFile(f);
      }
    }
  }

  private boolean makeDirs(File dir) {
    if (dir == null) {
      return false;
    }

    return (dir.exists() || dir.mkdirs());
  }

  public void deleteFile(File f) {
    if (f != null) {
      f.delete();
    }
  }

  public void downloadFile(File f, String clientFileName) {
    if (f == null || !f.exists()) {
      throw new RuntimeException("File does not exist");
    }
    log.debug("fileName : {}", f.getName());
    ServletRequestAttributes servletRequestAttributes =
        (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    HttpServletRequest request = servletRequestAttributes.getRequest();
    HttpServletResponse response = servletRequestAttributes.getResponse();

    String userAgent = request.getHeader("User-Agent");
    String fileName = Utils.getFileNameByBrower(userAgent, clientFileName);
    BufferedInputStream is = null;
    try {
      is = new BufferedInputStream(new FileInputStream(f));

      response.setContentType("application/octet-stream");
      response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
      response.setHeader("Content-Transfer-Encoding", "binary");
      IOUtils.copy(is, response.getOutputStream());
      response.flushBuffer();

      is.close();
    } catch (IOException ex) {
      log.error("Error writing file to output stream. Filename was '{}'", f.getName(), ex);
      throw new RuntimeException(ex);
    } finally {
      IOUtils.closeQuietly(is);
    }
  }
}
