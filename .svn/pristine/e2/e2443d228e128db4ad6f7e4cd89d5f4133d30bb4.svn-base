package kr.co.inogard.enio.agent.web;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
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
import kr.co.inogard.enio.agent.domain.pr.Pr;
import kr.co.inogard.enio.agent.domain.pr.PrDto;
import kr.co.inogard.enio.agent.domain.pr.PrDum;
import kr.co.inogard.enio.agent.domain.pr.PrFileDto;
import kr.co.inogard.enio.agent.domain.pr.PrItemDto;
import kr.co.inogard.enio.agent.domain.pr.PrSrvDto;
import kr.co.inogard.enio.agent.service.pr.PrService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = {"/send/pr", "/agent/v1/send/pr"},
    produces = {"application/json;charset=UTF-8", "application/enio-json;charset=UTF-8"})
public class PrSendRestController {

  @Autowired
  private PrService prService;

  @Autowired
  private ModelMapper modelMapper;

  @RequestMapping(method = POST)
  @ResponseStatus(HttpStatus.CREATED)
  public PrDto.Response create(@RequestPart("data") @Valid PrDto.Create prDto,
      @RequestPart(name = "files", required = false) List<MultipartFile> files) {

    Pr pr = prService.createPrByPrebid(prDto, files);
    log.debug("Create PrByPreBid prNo : " + pr.getPrNo());

    PrDto.Response res = modelMapper.map(pr, PrDto.Response.class);
    res.setErpPrNo(pr.getPrNo());
    res.setPrNo(prDto.getPrNo());
    res.setRsltCd(RsltCd.SUC0000.name());
    res.setRsltMsg(RsltCd.SUC0000.getCodeNm());

    return res;
  }

  @RequestMapping(params = {"draw", "start", "length"}, method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  @JsonView(PrDto.Views.PublicView.class)
  public DataTablesOutput<PrDto.Response> getSendPr(@Valid PrDto.Search search,
      @Valid DataTablesInput input) {
    return prService.getAllSendPr(search, input);
  }

  @RequestMapping(method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  @JsonView(PrDto.Views.PublicView.class)
  public PageImpl<PrDto.Response> getSendPr(@Valid PrDto.Search search, Pageable pageable) {
    Page<PrDum> page = prService.getAllSendPr(search, pageable);
    List<PrDto.Response> content =
        modelMapper.map(page.getContent(), new TypeToken<List<PrDto.Response>>() {}.getType());
    return new PageImpl<PrDto.Response>(content, pageable, page.getTotalElements());
  }

  @RequestMapping(value = "/{erpPrNo}", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  @JsonView(PrDto.Views.PublicView.class)
  public PrDto.Response getSendPr(@PathVariable String erpPrNo) {
    return modelMapper.map(prService.getSendPr(erpPrNo), PrDto.Response.class);
  }

  @RequestMapping(value = "/{erpPrNo}/items", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public List<PrItemDto.Response> getSendPrItems(@PathVariable String erpPrNo) {
    return modelMapper.map(prService.getSendPrItems(erpPrNo),
        new TypeToken<List<PrItemDto.Response>>() {}.getType());
  }

  @RequestMapping(value = "/{erpPrNo}/items/{itemSeq}/services", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public List<PrSrvDto.Response> getSendPrServices(@PathVariable String erpPrNo,
      @PathVariable String itemSeq) {
    return modelMapper.map(prService.getSendPrServices(erpPrNo, itemSeq),
        new TypeToken<List<PrSrvDto.Response>>() {}.getType());
  }

  @RequestMapping(value = "/{erpPrNo}/files", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public List<PrFileDto.Response> getSendPrFiles(@PathVariable String erpPrNo) {
    return modelMapper.map(prService.getSendPrFiles(erpPrNo),
        new TypeToken<List<PrFileDto.Response>>() {}.getType());
  }

}
