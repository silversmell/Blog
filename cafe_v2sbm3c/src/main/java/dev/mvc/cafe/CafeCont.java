package dev.mvc.cafe;

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

import dev.mvc.cafe.CafeVO;
import jakarta.validation.Valid;

@RequestMapping("/cafe")
@Controller
public class CafeCont {

	@Autowired
	@Qualifier("dev.mvc.cafe.CafeProc")
	private CafeProcInter cafeProc;

	public CafeCont() {
		System.out.println("-> CafeCont created");
	}

//  //폼 출력
//  @GetMapping(value="/create") // http://localhost:9092/cafe/create
//  public String create(Model model,CafeVO cafeVO) { 
//	  ArrayList<CafeVO> list = cafeProc.list_all();
//	  model.addAttribute("list", list);
//    return "/cafe/create";
//  }
	

	@PostMapping(value = "/create") // 프론트단에서 해결하면 네트워크 트래픽이 많이 감소됨.(하지만 타임리프는 서버단 검증)
	public String create(Model model, @Valid CafeVO cafeVO, BindingResult bindingResult) { // Valid 의 값을 BiningResult에 저장																							// 저장
		ArrayList<CafeVO> list = this.cafeProc.list_all();
		model.addAttribute("list", list);

		if (bindingResult.hasErrors()) {
			return "/cafe/create";
		}

		int cnt = this.cafeProc.create(cafeVO);
		System.out.println("->cnt: " + cnt);

		model.addAttribute("cnt", cnt);

		if (cnt == 1) { // Controller에서 View로 데이터를 전달할때 사용하는 도구
			return "redirect:/cafe/list_all";
		} else {
			model.addAttribute("code", "create_fail");
			return "/cafe/msg";
		}

	}

	@GetMapping(value = "/list_all") // http://localhost:9092/cafe/list_all
	public String list_all(Model model, CafeVO cafeVO) {
		ArrayList<CafeVO> list = this.cafeProc.list_all();
		model.addAttribute("list", list);

		return "/cafe/list_all";
	}
	
	@GetMapping(value="/list")
	public String list(Model model,CafeVO cafeVO) {
		ArrayList<CafeVO> list = this.cafeProc.list_all();
		model.addAttribute("list", list);
		
		return"/cafe/list";
	}

	@GetMapping(value = "/read/{cafeno}")
	public String read(Model model, @PathVariable("cafeno") Integer cafeno) {
		CafeVO cafeVO = this.cafeProc.read(cafeno);
		model.addAttribute("cafeVO", cafeVO);
		
		ArrayList<CafeVO> list = this.cafeProc.list_all();
		model.addAttribute("list", list);

		return "/cafe/read";
	}

	/**
	 * 수정폼
	 * 
	 * @param model
	 * @param cafeno 조회할 카테고리 번호
	 * @return
	 */
	@GetMapping(value = "/update/{cafeno}")
	public String update(Model model, @PathVariable("cafeno") Integer cafeno) {
		ArrayList<CafeVO> list = this.cafeProc.list_all();
		model.addAttribute("list", list);

		CafeVO cafeVO = this.cafeProc.read(cafeno);
		model.addAttribute("cafeVO", cafeVO);

		return "/cafe/update"; //

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
	public String update(Model model, @Valid CafeVO cafeVO, BindingResult bindingResult) {

		ArrayList<CafeVO> list = this.cafeProc.list_all();
		model.addAttribute("list", list);

		if (bindingResult.hasErrors()) {
			return "/cafe/update"; // /templates/cafe/update.html
		}
		int cnt = this.cafeProc.update(cafeVO);
		System.out.println("-> cnt: " + cnt);
		model.addAttribute("cnt", cnt);

		if (cnt == 1) {
			return "redirect:/cafe/update/" + cafeVO.getCafeno();

		} else {
			model.addAttribute("code", "update_fail");
			return "/cafe/msg"; //
		}
	}

	/**
	 * Delete form http://localhost:9091/cafe/delete/1
	 * 
	 * @param model
	 * @param .
	 * @return
	 */
	@GetMapping(value = "/delete/{cafeno}")
	public String delete(Model model, @PathVariable("cafeno") Integer cafeno) {

		ArrayList<CafeVO> list = this.cafeProc.list_all();
		model.addAttribute("list", list);

		CafeVO cafeVO = this.cafeProc.read(cafeno);
		model.addAttribute("cafeVO", cafeVO);

		return "/cafe/delete";

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
	public String delete_process(Model model, Integer cafeno) {
		CafeVO cafeVO = this.cafeProc.read(cafeno); // 삭제 정보 출력용 객체

		ArrayList<CafeVO> list = this.cafeProc.list_all();
		model.addAttribute("list", list);

		int cnt = this.cafeProc.delete(cafeno); // 삭제
		System.out.println("-> cnt: " + cnt);
		model.addAttribute("cnt", cnt);

		if (cnt == 1) {
//      model.addAttribute("code", "delete_success");
//      model.addAttribute("name", cafeVO.getName());
//      model.addAttribute("namesub", cafeVO.getNamesub());
			return "redirect:/cafe/list_all";
		} else {
			model.addAttribute("code", "delete_fail");
			return "/cafe/msg"; //
		}
	}

}