package kr.co.inogard.enio.agent.service.rfq;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import kr.co.inogard.enio.agent.commons.util.Utils;
import kr.co.inogard.enio.agent.domain.pr.Pr;
import kr.co.inogard.enio.agent.domain.pr.PrFile;
import kr.co.inogard.enio.agent.domain.quot.Quot;
import kr.co.inogard.enio.agent.domain.rfq.Rfq;
import kr.co.inogard.enio.agent.domain.rfq.RfqCus;
import kr.co.inogard.enio.agent.domain.rfq.RfqCusDto;
import kr.co.inogard.enio.agent.domain.rfq.RfqDegree;
import kr.co.inogard.enio.agent.domain.rfq.RfqDegreeDto;
import kr.co.inogard.enio.agent.domain.rfq.RfqDto;
import kr.co.inogard.enio.agent.domain.rfq.RfqItem;
import kr.co.inogard.enio.agent.domain.rfq.RfqItemDto;
import kr.co.inogard.enio.agent.domain.rfq.RfqSrv;
import kr.co.inogard.enio.agent.domain.rfq.RfqSrvDto;
import kr.co.inogard.enio.agent.mappers.pr.PrFileMapper;
import kr.co.inogard.enio.agent.mappers.pr.PrMapper;
import kr.co.inogard.enio.agent.mappers.quot.QuotFileMapper;
import kr.co.inogard.enio.agent.mappers.quot.QuotItemMapper;
import kr.co.inogard.enio.agent.mappers.quot.QuotMapper;
import kr.co.inogard.enio.agent.mappers.quot.QuotSrvMapper;
import kr.co.inogard.enio.agent.mappers.rfq.RfqCusMapper;
import kr.co.inogard.enio.agent.mappers.rfq.RfqDegreeMapper;
import kr.co.inogard.enio.agent.mappers.rfq.RfqItemMapper;
import kr.co.inogard.enio.agent.mappers.rfq.RfqMapper;
import kr.co.inogard.enio.agent.mappers.rfq.RfqSrvMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class RfqService {

	@Autowired
	private PrMapper prMapper;

	@Autowired
	private PrFileMapper prFileMapper;

	@Autowired
	private RfqMapper rfqMapper;

	@Autowired
	private RfqItemMapper rfqItemMapper;

	@Autowired
	private RfqSrvMapper rfqSrvMapper;

	@Autowired
	private RfqCusMapper rfqCusMapper;

	@Autowired
	private RfqDegreeMapper RfqDegreeMapper;

	@Autowired
	private QuotMapper quotMapper;

	@Autowired
	private QuotItemMapper quotItemMapper; 
	
	@Autowired
	private QuotSrvMapper quotSrvMapper;
	
	@Autowired
	private QuotFileMapper quotFileMapper;
	
	@Autowired
	private ModelMapper modelMapper;

	@Value("${enio.univ-cd}")
	private String univCd;

	public Rfq create(RfqDto.Create create) {
		RfqDto.Create rfqDto = modelMapper.map(create, RfqDto.Create.class);

		Rfq rfq = modelMapper.map(rfqDto, Rfq.class);
		
		//이미 입찰정보가 있는지 확인한 후 모두 삭제해 줌.
		if (getRfq(rfq.getRfqNo()) != null) {
			quotFileMapper.delQuotFile(rfq.getRfqNo());
			quotSrvMapper.delQuotService(rfq.getRfqNo());
			quotItemMapper.delQuotItem(rfq.getRfqNo());
			quotMapper.delQuot(rfq.getRfqNo());
			
			rfqSrvMapper.delRfqService(rfq.getRfqNo());
			rfqItemMapper.delRfqItem(rfq.getRfqNo());
			rfqCusMapper.delRfqcus(rfq.getRfqNo());
			RfqDegreeMapper.delRfqdegree(rfq.getRfqNo());
			rfqMapper.delRfq(rfq.getRfqNo());
		}
		
		rfqMapper.add(rfq);

		for (Iterator<RfqItemDto.Create> itemIter = rfqDto.getRfqItemList().iterator(); itemIter.hasNext();) {
			RfqItemDto.Create dto = itemIter.next();
			RfqItem rfqItem = modelMapper.map(dto, RfqItem.class);
			rfqItemMapper.add(rfqItem);

			for (RfqSrvDto.Create rfqSrvDto : dto.getRfqSrvList()) {
				RfqSrv rfqSrv = modelMapper.map(rfqSrvDto, RfqSrv.class);
				rfqSrvMapper.add(rfqSrv);
			}
		}

		for (Iterator<RfqCusDto.Create> cusIter = rfqDto.getRfqCusList().iterator(); cusIter.hasNext();) {
			RfqCusDto.Create dto = cusIter.next();
			RfqCus rfqCus = modelMapper.map(dto, RfqCus.class);
			rfqCusMapper.add(rfqCus);
		}

		for (Iterator<RfqDegreeDto.Create> degreeIter = rfqDto.getRfqDegreeList().iterator(); degreeIter.hasNext();) {
			RfqDegreeDto.Create dto = degreeIter.next();
			RfqDegree rfqDegree = modelMapper.map(dto, RfqDegree.class);
			RfqDegreeMapper.add(rfqDegree);
		}
		rfqMapper.updateRfqNoInPrItem(rfq.getRfqNo());
		return rfq;
	}

	public void notifyOnChannel(RfqDto.NotiCreate cre) {
		String rfqNo = cre.getRfqNo();
		String notiKind = cre.getNotiKind();
		if ("SEND_COMPLETE".equals(notiKind)) {
			String prNo = prMapper.findPrNoFromRfqNo(rfqNo);
			Pr pr = prMapper.findByPrNo(prNo);
			Rfq rfq = new Rfq();
			rfq.setRfqNo(rfqNo);
			rfq.setErpRfqNo(pr.getErpRfqNo());
			rfqMapper.updateErpRfqNo(rfq);

			if(!"S0001".equals(univCd)) {   // 울산 Legacy  동기화 제외
				try {
					Map<String, String> info = new HashMap<String, String>();
					info.put("erpRfqNo", rfq.getErpRfqNo());
					info.put("univCd", univCd);
					info.put("notiKind", "BID_END");
					info.put("outCd", "");
					info.put("outMsg", "");

					rfqMapper.updateCallSyncToErp(info);

					log.debug("outCd : {}", info.get("outCd"));
					log.debug("outMsg : {}", info.get("outMsg"));

					if (!"1".equals(info.get("outCd"))) {
						throw new RuntimeException(info.get("outMsg"));
					}
				} catch (Exception e) {
					log.error("ERP동기화(erpRfq_no=" + rfq.getErpRfqNo() + ",BID_END):작업오류발생", e);
				}
			}
		}
	}

	public DataTablesOutput<RfqDto.Response> getAllRfq(RfqDto.Search search, DataTablesInput input) {
		DataTablesOutput<RfqDto.Response> output = new DataTablesOutput<RfqDto.Response>();
		output.setDraw(input.getDraw());

		try {
			search.setValue(input.getSearch().getValue());
			search.setAgtCd(univCd);
			List<Rfq> rfqList = rfqMapper.findAll(search, Utils.dataTablesInputToPageable(input));
			List<RfqDto.Response> data = modelMapper.map(rfqList, new TypeToken<List<RfqDto.Response>>() {
			}.getType());

			output.setData(data);
			output.setRecordsFiltered(CollectionUtils.isEmpty(rfqList) ? 0 : rfqList.get(0).getFilteredCount());
			output.setRecordsTotal(rfqMapper.count(search.getAgtCd()));
		} catch (Exception e) {
			output.setError(e.getMessage());
		}
		return output;
	}

	public Page<Rfq> getAllRfq(RfqDto.Search search, Pageable pageable) {
		search.setAgtCd(univCd);
		List<Rfq> rfqList = rfqMapper.findAll(search, pageable);
		return new PageImpl<Rfq>(rfqList, pageable,
				CollectionUtils.isEmpty(rfqList) ? 0 : rfqList.get(0).getFilteredCount());
	}

	public Rfq getRfq(String rfqNo) {
		return rfqMapper.findByRfqNo(rfqNo);
	}

	public List<RfqItem> getRfqItems(String rfqNo) {
		return rfqItemMapper.findAllByRfqNo(rfqNo);
	}

	public List<RfqSrv> getRfqServices(String rfqNo, String itemSeq) {
		return rfqSrvMapper.findAllByRfqNoAndItemSeq(rfqNo, itemSeq);
	}

	public List<PrFile> getRfqFiles(String rfqNo) {
		String prNo = prMapper.findPrNoFromRfqNo(rfqNo);
		return prFileMapper.findAllByPrNo(prNo);
	}

	public List<Quot> getQuotes(String rfqNo, String quotRev) {
		return quotMapper.findAllByRfqNoAndQuotRev(rfqNo, quotRev);
	}

}
