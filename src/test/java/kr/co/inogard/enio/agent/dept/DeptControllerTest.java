package kr.co.inogard.enio.agent.dept;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.inogard.enio.agent.Application;
import kr.co.inogard.enio.agent.commons.EnioContext;
import kr.co.inogard.enio.agent.commons.constant.EnioMediaType;
import kr.co.inogard.enio.agent.commons.domain.ErrorResponse;
import kr.co.inogard.enio.agent.commons.util.CryptoUtil;
import kr.co.inogard.enio.agent.domain.dept.Dept;
import kr.co.inogard.enio.agent.domain.dept.DeptDto;
import kr.co.inogard.enio.agent.service.dept.DeptService;
import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
@Slf4j
public class DeptControllerTest {

	@Autowired
	WebApplicationContext wac;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	DeptService deptService;

	MockMvc mockMvc;

	@Autowired
	@Qualifier("restTemplate")
	RestTemplate restTemplate;

	@Autowired
	CryptoUtil cryptoUtil;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void getDept() throws Exception {
		String uri = "http://localhost:8080/api/dept/A0002102007052200002";
		ResponseEntity<Dept> entity = restTemplate.exchange(uri, HttpMethod.GET, null, Dept.class);
		Dept dept = entity.getBody();
		System.out.println("=====>" + dept.getDeptNm());
	}

	private DeptDto.CreateEntry deptCreateDto() {
		DeptDto.CreateEntry createDto = new DeptDto.CreateEntry();
		createDto.setDeptCd("abcde12345");
		createDto.setDeptNm("제목입니다");
		return createDto;
	}

	@Test
	public void createDept() throws Exception {
		DeptDto.CreateEntry createDto = deptCreateDto();
		createDto.setDeptCd("abcde12346");
		createDto.setDeptNm("제목입니다3");

		EnioContext.local.set(new EnioContext("S0037", "ENIOS0037-201708"));
		HttpHeaders jsonPartHeader = new HttpHeaders();
		jsonPartHeader.setContentType(EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType());
		HttpEntity<DeptDto.CreateEntry> jsonPart = new HttpEntity<DeptDto.CreateEntry>(createDto, jsonPartHeader);

		MockMultipartFile file = new MockMultipartFile("file1", "filename1.txt", "text/plain", "my-file1".getBytes());
		MockMultipartFile file2 = new MockMultipartFile("file2", "filename2.txt", "text/plain", "my-file1".getBytes());

		MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<String, Object>();
		multiValueMap.add("data", jsonPart);
		multiValueMap.add("files", new MultipartFileResource(file.getBytes(), "filename1.txt"));
		multiValueMap.add("files", new MultipartFileResource(file2.getBytes(), "filename2.txt"));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType());
		headers.setAccept(acceptableMediaTypes);
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(multiValueMap, headers);

		ErrorResponse res = null;
		try {
			res = restTemplate.postForObject("http://localhost:8080/api/pr", requestEntity, ErrorResponse.class);
		} catch (HttpClientErrorException e) {
			e.getResponseBodyAsString();
			log.debug("error : {}", e.getResponseBodyAsString());
		}
		log.debug("res : {}", res);
	}

	@Test
	public void createDeptMockMvc() throws Exception {
		DeptDto.CreateEntry createDto = deptCreateDto();
		createDto.setDeptCd("123456abcd");
		createDto.setDeptNm("제목입니다.");
		MockMultipartFile firstFile = new MockMultipartFile("files", "filename.txt", "text/plain", "some xml".getBytes());
		MockMultipartFile secondFile = new MockMultipartFile("files", "other-file-name.data", "text/plain", "some other type".getBytes());
		MockMultipartFile jsonFile = new MockMultipartFile("data", "", "application/json", objectMapper.writeValueAsBytes(createDto));

		ResultActions result = mockMvc.perform(fileUpload("/api/dept").file(firstFile).file(secondFile).file(jsonFile));

		result.andDo(print());
	}

	@Test
	public void createPrTestMockMvc() throws Exception {
		DeptDto.CreateEntry createDto = deptCreateDto();
		String a = cryptoUtil.encryptString("ENIOS0037-201708", objectMapper.writeValueAsString(createDto));
		ResultActions result = null;
		result = mockMvc.perform(fileUpload("/api/pr/test").contentType(EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType()).content(a)
				.accept(EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType()));

		result.andDo(print());
	}

	static class MultipartFileResource extends ByteArrayResource {
		private final String filename;

		public MultipartFileResource(byte[] payload, String originalFileName) throws IOException {
			super(payload);
			this.filename = originalFileName;
		}

		@Override
		public String getFilename() {
			return this.filename;
		}
	}

}