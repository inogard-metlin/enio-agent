package kr.co.inogard.enio.agent.commons.util;

import java.io.InputStream;
import java.io.OutputStream;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Component;
import kr.co.inogard.enio.agent.domain.agent.AgentConfig;
import kr.co.inogard.enio.agent.security.AgentUserDetailsImpl;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("cryptoUtil")
public class CryptoUtil {

  @Autowired
  private AgentConfig agentConfig;

  // 문자열 암호화
  public String encryptString(String key, String value) throws Exception {
    try {
      byte[] arrKey = key.getBytes("UTF-8");
      IvParameterSpec iv = new IvParameterSpec(arrKey);
      SecretKeySpec skeySpec = new SecretKeySpec(arrKey, "AES");

      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
      cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

      byte[] encrypted = cipher.doFinal(value.getBytes("UTF-8"));

      return new String(Base64.encode(encrypted));
    } catch (Exception ex) {
      ex.printStackTrace();
      throw ex;
    }
  }

  // 문자열 복호화
  public String decryptString(String key, String encrypted) throws Exception {
    try {
      byte[] arrKey = key.getBytes("UTF-8");
      IvParameterSpec iv = new IvParameterSpec(arrKey);
      SecretKeySpec skeySpec = new SecretKeySpec(arrKey, "AES");


      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
      cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

      byte[] original = cipher.doFinal(Base64.decode(encrypted.getBytes()));

      return new String(original, "UTF-8");
    } catch (Exception ex) {
      ex.printStackTrace();
      throw ex;
    }
  }

  // 파일암호화
  public void encryptFile(String key, InputStream in, OutputStream out) throws Exception {
    try {
      byte[] arrKey = key.getBytes("UTF-8");
      IvParameterSpec iv = new IvParameterSpec(arrKey);
      SecretKeySpec skeySpec = new SecretKeySpec(arrKey, "AES");

      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
      cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

      byte[] buffer = new byte[65536];
      int n;
      CipherOutputStream cos = new CipherOutputStream(out, cipher);

      while ((n = in.read(buffer)) > 0)
        cos.write(buffer, 0, n);
      cos.close();
      in.close();
    } catch (Exception ex) {
      ex.printStackTrace();
      throw ex;
    }

  }

  // 파일복호화
  public void decryptFile(String key, InputStream in, OutputStream out) throws Exception {
    try {
      byte[] arrKey = key.getBytes("UTF-8");
      IvParameterSpec iv = new IvParameterSpec(arrKey);
      SecretKeySpec skeySpec = new SecretKeySpec(arrKey, "AES");


      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
      cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

      byte[] buffer = new byte[65536];
      int n = -1;
      CipherInputStream cis = new CipherInputStream(in, cipher);
      while ((n = cis.read(buffer)) > 0)
        out.write(buffer, 0, n);
      out.close();
      cis.close();
    } catch (Exception ex) {
      ex.printStackTrace();
      throw ex;
    }
  }

  public String getCryptoKey() {
    log.debug("SecurityContextHolder.getContext().getAuthentication() : {}",
        SecurityContextHolder.getContext().getAuthentication());
    return (SecurityContextHolder.getContext().getAuthentication() != null)
        ? ((AgentUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
            .getPrincipal()).getAgent().getApiRawLicenceKey()
        : agentConfig.getAgent().getRawLicenceKey();
  }
}