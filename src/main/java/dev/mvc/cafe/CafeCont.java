package dev.mvc.cafe;

import java.net.URLEncoder;
import java.nio.channels.IllegalBlockingModeException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dev.mvc.cafe.CafeVO;
import dev.mvc.tool.Tool;
import jakarta.validation.Valid;

@RequestMapping("/cafe")
@Controller
public class CafeCont {

	@Autowired
	@Qualifier("dev.mvc.cafe.CafeProc")
	private CafeProcInter cafeProc;
	
	  /** 페이지당 출력할 레코드 갯수, nowPage는 1부터 시작 */
	  public int record_per_page = 3;

	  /** 블럭당 페이지 수, 하나의 블럭은 10개의 페이지로 구성됨 */
	  public int page_per_block = 10;

	public CafeCont() {
		System.out.println("-> CafeCont created");
	}

//  //폼 출력
//  @GetMapping(value="/create") // http://localhost:9092/cafe/list_all
//  public String create(Model model,CafeVO cafeVO) { 
//	  ArrayList<CafeVO> list = cafeProc.list_all();
//	  model.addAttribute("list", list);
//    return "/cafe/create";
//  }
	
	@PostMapping(value = "/create") // 프론트단에서 해결하면 네트워크 트래픽이 많이 감소됨.(하지만 타임리프는 서버단 검증)
	public String create(Model model, @Valid CafeVO cafeVO, BindingResult bindingResult,
											@RequestParam(name="word", defaultValue = "") String word,
											@RequestParam(name="now_page", defaultValue = "1") int now_page) { // Valid 의 값을 BiningResult에 저장	
		
		
	   ArrayList<CafeVOMenu> menu = this.cafeProc.menu();
	   model.addAttribute("menu",menu);
		   
	    if (bindingResult.hasErrors()) {
	        // 페이징 목록
	        ArrayList<CafeVO> list = this.cafeProc.list_search_paging(word, now_page, this.record_per_page);    
	        model.addAttribute("list", list);
	        
	        // 페이징 버튼 목록
	        int search_count = this.cafeProc.list_search_count(word);
	        String paging = this.cafeProc.pagingBox(now_page, 
	            word, "cate/list_search", search_count, this.record_per_page, this.page_per_block);
	        model.addAttribute("paging", paging);
	        model.addAttribute("now_page", now_page);
	        
	        return "cate/list_search";  // /templates/cate/list_search.html
	      }

		int cnt = this.cafeProc.create(cafeVO);

		model.addAttribute("cnt", cnt);
		model.addAttribute("word",word);

		if (cnt == 1) { // Controller에서 View로 데이터를 전달할때 사용하는 도구
			return "redirect:/cafe/list_search";
		} else {
			model.addAttribute("code", "create_fail");
			return "cafe/msg";
		}

	}

//	@GetMapping(value = "/list_all") // http://localhost:9092/cafe/list_search
//	public String list_all(Model model, CafeVO cafeVO) {
//	   ArrayList<CafeVOMenu> menu = this.cafeProc.menu();
//	   model.addAttribute("menu",menu);
//	   
//		ArrayList<CafeVO> list = this.cafeProc.list_all();
//		model.addAttribute("list", list);
//
//		return "/cafe/list_search";
//	}
	
//	@GetMapping(value="/list")
//	public String list(Model model,CafeVO cafeVO) {
//	   ArrayList<CafeVOMenu> menu = this.cafeProc.menu();
//	   model.addAttribute("menu",menu);
//		ArrayList<CafeVO> list = this.cafeProc.list_all();
//		model.addAttribute("list", list);
//		
//		return"/cafe/list";
//	}

	@GetMapping(value = "/read/{cafeno}")
	public String read(Model model, @PathVariable("cafeno") Integer cafeno, 
												@RequestParam(name="word", defaultValue = "") String word,
												@RequestParam(name="now_page",defaultValue="1") int now_page) {
		
	   ArrayList<CafeVOMenu> menu = this.cafeProc.menu();
	   model.addAttribute("menu",menu);
	   
		CafeVO cafeVO = this.cafeProc.read(cafeno);
		model.addAttribute("cafeVO", cafeVO);
		
		ArrayList<CafeVO> list = this.cafeProc.list_search_paging(word,now_page,this.record_per_page);
		model.addAttribute("list", list);
		
		int search_count = this.cafeProc.list_search_count(word);
	    String paging = this.cafeProc.pagingBox(now_page, 
	            word, "/cafe/list_search", search_count, this.record_per_page, this.page_per_block);
	    model.addAttribute("paging", paging);
	    model.addAttribute("now_page", now_page);
	    
	    int no = search_count-((now_page-1) * this.record_per_page);
	    model.addAttribute("no",no);
	    
	    model.addAttribute("word", word);

		return "cafe/read";
	}

	/**
	 * 수정폼
	 * 
	 * @param model
	 * @param cafeno 조회할 카테고리 번호
	 * @return
	 */
	@GetMapping(value = "/update/{cafeno}")
	public String update(Model model, @PathVariable("cafeno") Integer cafeno,
													@RequestParam(name="word",defaultValue="") String word,
													@RequestParam(name="now_page",defaultValue="1") int now_page) {
		
	   ArrayList<CafeVOMenu> menu = this.cafeProc.menu();
	   model.addAttribute("menu",menu);
	   
		CafeVO cafeVO = this.cafeProc.read(cafeno);
		model.addAttribute("cafeVO", cafeVO);
		
		ArrayList<CafeVO> list = this.cafeProc.list_search_paging(word,now_page,this.record_per_page);
		model.addAttribute("list", list);
		
		int search_count = this.cafeProc.list_search_count(word);
	    String paging = this.cafeProc.pagingBox(now_page, 
	            word, "/cafe/list_search", search_count, this.record_per_page, this.page_per_block);
	    model.addAttribute("paging", paging);
	    model.addAttribute("now_page", now_page);
	    
	    model.addAttribute("word", word);
	    
	    int no = search_count-((now_page-1) * this.record_per_page);
	    model.addAttribute("no",no);
	  
		return "cafe/update"; //

	}

	/**
	 * 수정 처리
	 * 
	 * @param model
	 * @param cafeVO
	 * @param bindingResult
	 * @return
	 */
	@PostMapping(value = "/update") // http://localhost:9091/cafe/update
	public String update(Model model, @Valid CafeVO cafeVO, BindingResult bindingResult, 
			@RequestParam(name="word", defaultValue = "") String word,
			@RequestParam(name="now_page",defaultValue="1") int now_page) {
		
	   ArrayList<CafeVOMenu> menu = this.cafeProc.menu();
	   model.addAttribute("menu",menu);
	   
		model.addAttribute("word", word);

		if (bindingResult.hasErrors()) {
			//페이징  목록
			ArrayList<CafeVO> list = this.cafeProc.list_search_paging(word,now_page,this.record_per_page);
			model.addAttribute("list", list);
			
			//페이징 버튼 목록
		int search_count = this.cafeProc.list_search_count(word);
	      String paging = this.cafeProc.pagingBox(now_page, 
	              word, "/cafe/list_search", search_count, this.record_per_page, this.page_per_block);
          model.addAttribute("paging", paging);
          model.addAttribute("now_page", now_page);
	          
			return "cafe/update"; // /templates/cafe/update.html
		}
		int cnt = this.cafeProc.update(cafeVO);
		
		model.addAttribute("cnt", cnt);

		if (cnt == 1) {
			return "redirect:/cafe/update/" + cafeVO.getCafeno() +"?word=" + Tool.encode(word)+"&now_page=" + now_page;

		} else {
			model.addAttribute("code", "update_fail");
			return "cafe/msg"; //
		}
	}

	/**
	 * Delete form http://localhost:9091/cafe/delete/1
	 * 
	 * @param model
	 * @param .
	 * @return
	 */
	@GetMapping("/delete_whole")
	public String delete_whole(Model model,
			@RequestParam(name="cafeno",defaultValue="0") Integer cafeno,
			@RequestParam(name="word",defaultValue="") String word,
			@RequestParam(name="now_page",defaultValue="1") int now_page) {
		
			ArrayList<CafeVOMenu> menu = this.cafeProc.menu();
		   model.addAttribute("menu",menu);
		   
		   CafeVO cafeVO = this.cafeProc.read(cafeno);
			model.addAttribute("cafeVO", cafeVO);
		   
		   // 페이징 목록
		    ArrayList<CafeVO> list = this.cafeProc.list_search_paging(word, now_page, this.record_per_page);    
		    model.addAttribute("list", list);
		   
		    //페이징 버튼 목록
		    int search_count = this.cafeProc.list_search_count(word);
		    String paging = this.cafeProc.pagingBox(now_page, word, "/cafe/list_search", search_count, 
		    		this.record_per_page, this.page_per_block);
		    model.addAttribute("paging", paging);
		    model.addAttribute("now_page", now_page);
		    
		    model.addAttribute("word",word);
		    
		    int no = search_count-((now_page-1) * this.record_per_page);
		    model.addAttribute("no",no);
		   
		   
		return "cafe/delete_whole";
	}
	@GetMapping(value = "/delete/{cafeno}")
	public String delete(Model model, 
								@PathVariable("cafeno") Integer cafeno,
								@RequestParam(name="word",defaultValue="") String word,
								@RequestParam(name="now_page",defaultValue="1") int now_page) {
		
	   ArrayList<CafeVOMenu> menu = this.cafeProc.menu();
	   model.addAttribute("menu",menu);
	   
	   CafeVO cafeVO = this.cafeProc.read(cafeno);
		model.addAttribute("cafeVO", cafeVO);
		
	    // 페이징 목록
	    ArrayList<CafeVO> list = this.cafeProc.list_search_paging(word, now_page, this.record_per_page);    
	    model.addAttribute("list", list);
	    
	    //페이징 버튼 목록
	    int search_count = this.cafeProc.list_search_count(word);
	    String paging = this.cafeProc.pagingBox(now_page, word, "/cafe/list_search", search_count, 
	    		this.record_per_page, this.page_per_block);
	    model.addAttribute("paging", paging);
	    model.addAttribute("now_page", now_page);
	    
	    model.addAttribute("word",word);
	    
	    int no = search_count-((now_page-1) * this.record_per_page);
	    model.addAttribute("no",no);

		return "cafe/delete";
	}

	/**
	 * Delete process
	 * 
	 * @param model
	 * @param cafeno        삭제할 레코드 번호
	 * @param bindingResult
	 * @return
	 */
	@PostMapping(value = "/delete")
	public String delete_process(Model model,
			@RequestParam(name="cafeno",defaultValue="0") Integer cafeno,
			@RequestParam(name="word",defaultValue="") String word,
			@RequestParam(name="now_page", defaultValue = "1") int now_page) {
		
		int cnt = this.cafeProc.delete(cafeno); // 삭제
		
		if(cafeno == 0) {
			return "redirect:/cafe/list_search?word=" + Tool.encode(word) +"&now_page=" + now_page; 
		}
		
		model.addAttribute("cnt", cnt);
		
		//마지막 페이지에서 모든 레코드가 삭제되면 페이지 수를 1감소 시켜야함.
		int search_cnt = this.cafeProc.list_search_count(word);
		if(search_cnt % this.record_per_page==0) {
			now_page=now_page-1;
			if(now_page<1) {
				now_page=1;
		}
		}

		if (cnt == 1) {
			return "redirect:/cafe/list_search?word=" + Tool.encode(word) +"&now_page=" + now_page;
		} else {
			model.addAttribute("code", "delete_fail");
			return "cafe/msg"; //
		}
	}
	
	@GetMapping(value="/update_seqno_forward/{cafeno}")
	public String update_seqno_forward(Model model, 
													@PathVariable("cafeno") Integer cafeno,
													@RequestParam(name="word",defaultValue="") String word,
													@RequestParam(name="now_page", defaultValue = "1") int now_page) {
		
		this.cafeProc.update_seqno_forward(cafeno);
		model.addAttribute("now_page",now_page);
		
		return "redirect:/cafe/list_search?word=" + Tool.encode(word)+"&now_page=" + now_page;
		
		
	}
	
	@GetMapping(value="/update_seqno_backward/{cafeno}")
	public String update_seqno_backward(Model model, @PathVariable("cafeno") Integer cafeno,
																			@RequestParam(name="word",defaultValue="") String word,
																			@RequestParam(name="now_page", defaultValue = "1") int now_page) {
		
	   
		this.cafeProc.update_seqno_backward(cafeno);
		
		return "redirect:/cafe/list_search?word=" + Tool.encode(word)+"&now_page=" + now_page;
		
	}
	
	@GetMapping(value="/update_visible_y/{cafeno}")
	public String update_visible_y(Model model, 
											@PathVariable("cafeno") Integer cafeno,
											@RequestParam(name="word",defaultValue="") String word,
											@RequestParam(name="now_page", defaultValue = "1") int now_page) {
	   
		this.cafeProc.update_visible_y(cafeno);

		
		return "redirect:/cafe/list_search?word=" + Tool.encode(word)+"&now_page=" + now_page;
	}
	
	@GetMapping(value="/update_visible_n/{cafeno}")
	public String update_visible_n(Model model, 
											@PathVariable("cafeno") Integer cafeno,
											@RequestParam(name="word",defaultValue="") String word,
											@RequestParam(name="now_page", defaultValue = "1") int now_page) {
		
		this.cafeProc.update_visible_n(cafeno);
		
		return "redirect:/cafe/list_search?word=" + Tool.encode(word)+"&now_page=" + now_page;
		
	}
	
	@GetMapping(value="/list_search")
	public String list_search(Model model, CafeVO cafeVO,
									@RequestParam(name="word",defaultValue="") String word,
									 @RequestParam(name="now_page",defaultValue="1") int now_page) {
		
		
		word=Tool.checkNull(word).trim();
		System.out.println("--> word" + word);
		
		ArrayList<CafeVOMenu> menu = this.cafeProc.menu();
		model.addAttribute("menu",menu);
		
	    ArrayList<CafeVO> list = this.cafeProc.list_search_paging(word,now_page,this.record_per_page);
	    model.addAttribute("list", list);
	    
	    int search_count = this.cafeProc.list_search_count(word);
	    String paging = this.cafeProc.pagingBox(now_page,
	    		word,"/cafe/list_search",search_count, this.record_per_page, this.page_per_block);
	    
	    model.addAttribute("count",search_count);
	    model.addAttribute("paging", paging);
	    model.addAttribute("now_page", now_page);
	    
	    model.addAttribute("word", word);
	    
	    int no = search_count-((now_page-1) * this.record_per_page);
	    model.addAttribute("no",no);
	    
	    
	    return "cafe/list_search";
	}

}