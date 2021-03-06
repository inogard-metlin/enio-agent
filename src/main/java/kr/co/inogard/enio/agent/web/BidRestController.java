package kr.co.inogard.enio.agent.web;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.inogard.enio.agent.commons.constant.RsltCd;
import kr.co.inogard.enio.agent.domain.pr.PrFileDto;
import kr.co.inogard.enio.agent.domain.quot.QuotDto;
import kr.co.inogard.enio.agent.domain.rfq.Rfq;
import kr.co.inogard.enio.agent.domain.rfq.RfqDto;
import kr.co.inogard.enio.agent.domain.rfq.RfqItemDto;
import kr.co.inogard.enio.agent.domain.rfq.RfqSrvDto;
import kr.co.inogard.enio.agent.service.quot.QuotService;
import kr.co.inogard.enio.agent.service.rfq.RfqService;

@RestController
@RequestMapping(value = { "/bid", "/agent/v1/bid" }, produces = { "application/json;charset=UTF-8", "application/enio-json;charset=UTF-8" })
public class BidRestController {

	@Autowired
	RfqService rfqService;

	@Autowired
	QuotService quotService;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	private ModelMapper modelMapper;

	@RequestMapping(value = "rfq", method = POST)
	@ResponseStatus(HttpStatus.CREATED)
	@JsonView(RfqDto.Views.ApiView.class)
	public RfqDto.Response create(@RequestBody RfqDto.Create create) throws IOException {
		Rfq rfq = rfqService.create(create);
		RfqDto.Response res = new RfqDto.Response();
		res.setRsltCd(RsltCd.SUC0000.name());
		res.setRsltMsg(RsltCd.SUC0000.getCodeNm());
		res.setErpRfqNo(rfq.getRfqNo());
		return res;
	}

	@RequestMapping(value = "/notify-on-channel", method = POST)
	@ResponseStatus(HttpStatus.CREATED)
	public RfqDto.NotiResponse notifyOnChannel(@RequestBody RfqDto.NotiCreate create) throws IOException {
		rfqService.notifyOnChannel(create);
		RfqDto.NotiResponse res = new RfqDto.NotiResponse();
		res.setRsltCd(RsltCd.SUC0000.name());
		res.setRsltMsg(RsltCd.SUC0000.getCodeNm());
		return res;
	}

	@RequestMapping(value = "/rfq", params = { "draw", "start", "length" }, method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@JsonView(RfqDto.Views.PublicView.class)
	public DataTablesOutput<RfqDto.Response> getAllRfq(@Valid RfqDto.Search search, @Valid DataTablesInput input) {
		return rfqService.getAllRfq(search, input);
	}

	@RequestMapping(value = "/rfq", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@JsonView(RfqDto.Views.PublicView.class)
	public PageImpl<RfqDto.Response> getAllRfq(@Valid RfqDto.Search search, Pageable pageable) {
		Page<Rfq> page = rfqService.getAllRfq(search, pageable);
		List<RfqDto.Response> content = modelMapper.map(page.getContent(), new TypeToken<List<RfqDto.Response>>() {
		}.getType());
		return new PageImpl<RfqDto.Response>(content, pageable, page.getTotalElements());
	}

	@RequestMapping(value = "/rfq/{rfqNo}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@JsonView(RfqDto.Views.PublicView.class)
	public RfqDto.Response getRfq(@PathVariable String rfqNo) {
		return modelMapper.map(rfqService.getRfq(rfqNo), RfqDto.Response.class);
	}

	@RequestMapping(value = "/rfq/{rfqNo}/items", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List<RfqItemDto.Response> getRfqItems(@PathVariable String rfqNo) {
		return modelMapper.map(rfqService.getRfqItems(rfqNo), new TypeToken<List<RfqItemDto.Response>>() {
		}.getType());
	}

	@RequestMapping(value = "/rfq/{rfqNo}/items/{itemSeq}/services", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List<RfqSrvDto.Response> getRfqServices(@PathVariable String rfqNo, @PathVariable String itemSeq) {
		return modelMapper.map(rfqService.getRfqServices(rfqNo, itemSeq), new TypeToken<List<RfqSrvDto.Response>>() {
		}.getType());
	}

	@RequestMapping(value = "/rfq/{rfqNo}/files", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List<PrFileDto.Response> getRfqFiles(@PathVariable String rfqNo) {
		return modelMapper.map(rfqService.getRfqFiles(rfqNo), new TypeToken<List<PrFileDto.Response>>() {
		}.getType());
	}

	@RequestMapping(value = "/rfq/{rfqNo}/quotes/{quotRev}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List<QuotDto.Response> getQuotes(@PathVariable String rfqNo, @PathVariable String quotRev) {
		return modelMapper.map(rfqService.getQuotes(rfqNo, quotRev), new TypeToken<List<QuotDto.Response>>() {
		}.getType());
	}
}
