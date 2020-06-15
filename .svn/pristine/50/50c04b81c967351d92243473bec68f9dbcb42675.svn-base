package kr.co.inogard.enio.agent.commons.filter.wrapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.io.IOUtils;

public class CaptureRequestWrapper extends HttpServletRequestWrapper {

	private byte[] bodyData;

	public CaptureRequestWrapper(HttpServletRequest request) throws IOException {
		super(request);
		InputStream is = super.getInputStream();
		bodyData = IOUtils.toByteArray(is);
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		final ByteArrayInputStream bis = new ByteArrayInputStream(bodyData);
		return new CaptureInputStream(bis);
	}

	private class CaptureInputStream extends ServletInputStream {

		private InputStream is;

		public CaptureInputStream(InputStream bis) {
			is = bis;
		}

		@Override
		public int read() throws IOException {
			return is.read();
		}

		@Override
		public int read(byte[] b) throws IOException {
			return is.read(b);
		}
	}
}
