package kr.co.inogard.enio.agent.web;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.co.inogard.enio.agent.commons.constant.RsltCd;
import kr.co.inogard.enio.agent.domain.quot.Quot;
import kr.co.inogard.enio.agent.domain.quot.QuotDto;
import kr.co.inogard.enio.agent.service.quot.QuotService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/agent/v1/bid/quot")
@Slf4j
public class QuotRestController {

	@Autowired
	private QuotService quotService;

	@RequestMapping(method = POST)
	@ResponseStatus(HttpStatus.CREATED)
	public QuotDto.Response createQuot(@RequestPart("data") @Valid QuotDto.Create quotDto,
															 @RequestPart(name = "files", required = false) List<MultipartFile> files) throws IOException, ParseException {

		Quot quot = quotService.create(quotDto, files);
		log.debug("==> CREATE QUOT : " + quot.getRfqNo());

		QuotDto.Response res = new QuotDto.Response();
		res.setRsltCd(RsltCd.SUC0000.name());
		res.setRsltMsg(RsltCd.SUC0000.getCodeNm());

		return res;
	}

}
