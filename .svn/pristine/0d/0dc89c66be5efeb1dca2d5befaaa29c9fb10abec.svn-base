package kr.co.inogard.enio.agent.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import kr.co.inogard.enio.agent.service.file.FileService;

@Controller
public class AttachController {
	
	@Autowired
	FileService fileService;
	
	@RequestMapping(value="/fileup/prfile")	
	public ModelAndView uploadPage() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("upload/uploadagtdumprfile");
		
		return mv;
	}
	
//	@RequestMapping(value="/fileup/prfile", method=RequestMethod.POST)
//	public @ResponseBody String writeFileUpload(@RequestParam(value="files") List<MultipartFile> files) {
//		//model.addAttribute("returnStr", fileService.uploadAgtDumPrFile(files));
//		
//		//return fileService.uploadAgtDumPrFile(files);
//	}  
	
}
