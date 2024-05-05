package dev.mvc.cafe_v1sbm3c;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import dev.mvc.cafe.CafeProcInter;
import dev.mvc.cafe.CafeVOMenu;

@Controller
public class HomeCont {
	@Autowired
	@Qualifier("dev.mvc.cafe.CafeProc")
	private CafeProcInter cafeProc;

	public HomeCont() {
		System.out.println("-> HomeCont created.");
	}

	@GetMapping(value="/") // http://localhost:9092
    public String home(Model model) { // 파일명 return
	  
	   ArrayList<CafeVOMenu> menu = this.cafeProc.menu();
	   model.addAttribute("menu",menu);
       return "index"; // /templates/index.html  
  }

}
