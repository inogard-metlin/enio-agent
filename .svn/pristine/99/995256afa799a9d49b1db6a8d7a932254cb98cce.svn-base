package kr.co.inogard.enio.agent.web;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonView;

import kr.co.inogard.enio.agent.commons.constant.RsltCd;
import kr.co.inogard.enio.agent.domain.po.Po;
import kr.co.inogard.enio.agent.domain.po.PoDto;
import kr.co.inogard.enio.agent.domain.po.PoFileDto;
import kr.co.inogard.enio.agent.domain.po.PoItemDto;
import kr.co.inogard.enio.agent.domain.po.PoSrvDto;
import kr.co.inogard.enio.agent.service.po.PoService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = { "/po", "/agent/v1/po" }, produces = { "application/json;charset=UTF-8", "application/enio-json;charset=UTF-8" })
public class PoRestController {

	@Autowired
	private PoService poService;

	@Autowired
	private ModelMapper modelMapper;

	@RequestMapping(method = POST)
	@ResponseStatus(HttpStatus.CREATED)
	@JsonView(PoDto.Views.ApiView.class)
	public PoDto.Response create(@RequestPart("data") @Valid PoDto.Create poDto,
												  @RequestPart(name = "files", required = false) List<MultipartFile> files) throws IOException, ParseException {
		Po po = poService.create(poDto, files);
		log.debug("========== CEATE PO : " + po.getPoNo() + "========== ");

		PoDto.Response res = new PoDto.Response();
		res.setRsltCd(RsltCd.SUC0000.name());
		res.setRsltMsg(RsltCd.SUC0000.getCodeNm());		
		
		return res;
	}

	@RequestMapping(params = { "draw", "start", "length" }, method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@JsonView(PoDto.Views.PublicView.class)
	public DataTablesOutput<PoDto.Response> getPo(@Valid PoDto.Search search, @Valid DataTablesInput input) {
		return poService.getAllPo(search, input);
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@JsonView(PoDto.Views.PublicView.class)
	public PageImpl<PoDto.Response> getPo(@Valid PoDto.Search search, Pageable pageable) {
		Page<Po> page = poService.getAllPo(search, pageable);
		List<PoDto.Response> content = modelMapper.map(page.getContent(), new TypeToken<List<PoDto.Response>>() {
		}.getType());
		return new PageImpl<PoDto.Response>(content, pageable, page.getTotalElements());
	}

	@RequestMapping(value = "/{poNo}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@JsonView(PoDto.Views.PublicView.class)
	public PoDto.Response getPo(@PathVariable String poNo) {
		return modelMapper.map(poService.getPo(poNo), PoDto.Response.class);
	}

	@RequestMapping(value = "/{poNo}/items", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List<PoItemDto.Response> getPoItems(@PathVariable String poNo) {
		return modelMapper.map(poService.getPoItems(poNo), new TypeToken<List<PoItemDto.Response>>() {
		}.getType());
	}

	@RequestMapping(value = "/{poNo}/items/{itemSeq}/services", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List<PoSrvDto.Response> getPoServices(@PathVariable String poNo, @PathVariable String itemSeq) {
		return modelMapper.map(poService.getPoServices(poNo, itemSeq), new TypeToken<List<PoSrvDto.Response>>() {
		}.getType());
	}

	@RequestMapping(value = "/{poNo}/files", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List<PoFileDto.Response> getPoFiles(@PathVariable String poNo) {
		return modelMapper.map(poService.getPoFiles(poNo), new TypeToken<List<PoFileDto.Response>>() {
		}.getType());
	}
}
