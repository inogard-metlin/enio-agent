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
import kr.co.inogard.enio.agent.domain.pr.PrFileDto;
import kr.co.inogard.enio.agent.domain.pr.PrItemDto;
import kr.co.inogard.enio.agent.domain.pr.PrSrvDto;
import kr.co.inogard.enio.agent.service.pr.PrService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = {"/pr", "/agent/v1/pr"},
    produces = {"application/json;charset=UTF-8", "application/enio-json;charset=UTF-8"})
public class PrRestController {

  @Autowired
  private PrService prService;

  @Autowired
  private ModelMapper modelMapper;

  @RequestMapping(method = POST)
  @ResponseStatus(HttpStatus.CREATED)
  @JsonView(PrDto.Views.ApiView.class)
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
  public DataTablesOutput<PrDto.Response> getPr(@Valid PrDto.Search search,
      @Valid DataTablesInput input) {
    return prService.getAllPr(search, input);
  }

  @RequestMapping(method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  @JsonView(PrDto.Views.PublicView.class)
  public PageImpl<PrDto.Response> getPr(@Valid PrDto.Search search, Pageable pageable) {
    Page<Pr> page = prService.getAllPr(search, pageable);
    List<PrDto.Response> content =
        modelMapper.map(page.getContent(), new TypeToken<List<PrDto.Response>>() {}.getType());
    return new PageImpl<PrDto.Response>(content, pageable, page.getTotalElements());
  }

  @RequestMapping(value = "/{prNo}", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  @JsonView(PrDto.Views.PublicView.class)
  public PrDto.Response getPr(@PathVariable String prNo) {
    return modelMapper.map(prService.getPr(prNo), PrDto.Response.class);
  }

  @RequestMapping(value = "/{prNo}/items", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public List<PrItemDto.Response> getPrItems(@PathVariable String prNo) {
    return modelMapper.map(prService.getPrItems(prNo),
        new TypeToken<List<PrItemDto.Response>>() {}.getType());
  }

  @RequestMapping(value = "/{prNo}/items/{itemSeq}/services", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public List<PrSrvDto.Response> getPrServices(@PathVariable String prNo,
      @PathVariable String itemSeq) {
    return modelMapper.map(prService.getPrServices(prNo, itemSeq),
        new TypeToken<List<PrSrvDto.Response>>() {}.getType());
  }

  @RequestMapping(value = "/{prNo}/files", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public List<PrFileDto.Response> getPrFiles(@PathVariable String prNo) {
    return modelMapper.map(prService.getPrFiles(prNo),
        new TypeToken<List<PrFileDto.Response>>() {}.getType());
  }

}
