package dev.mvc.member;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dev.mvc.cafe.CafeProcInter;
import dev.mvc.cafe.CafeVOMenu;
import dev.mvc.tool.Security;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/member")
@Controller
public class MemberCont {
	@Autowired
	private Security security;
	@Autowired
	@Qualifier("dev.mvc.member.MemberProc")
	private MemberProcInter MemberProc;
	
	@Autowired
	@Qualifier("dev.mvc.cafe.CafeProc")
	private CafeProcInter cafeProc;
	
	 public MemberCont() {
		 System.out.println("-> MemberCont created");
	 }
	
	@GetMapping("/checkID") //http://localhost:9092/member/checkId?id=admin
	@ResponseBody
	public String checkID(String id) {
		System.out.println("-> id:" + id);
		int cnt = this.MemberProc.checkID(id);
		JSONObject jsn = new JSONObject();
		jsn.put("cnt", cnt);
		
		return jsn.toString();
			
	}
	
	@GetMapping("/create") //http://localhost:9092/member/create
	public String create(Model model,MemberVO memberVO) {
		
		ArrayList<CafeVOMenu> menu = this.cafeProc.menu();
		model.addAttribute("menu",menu);
		
		return "member/create";
	}
	
	@PostMapping("/create")
	public String create_proc(Model model, MemberVO memberVO) {
		memberVO.setPasswd(this.security.aesEncode(memberVO.getPasswd()));
		int checkID_cnt = this.MemberProc.checkID(memberVO.getId());
		if(checkID_cnt==0) {
			memberVO.setGrade(15);
			int cnt = this.MemberProc.create(memberVO);
			System.out.println(cnt);
			
			if(cnt==1) {
				model.addAttribute("code" ,"create_success");
				model.addAttribute("mname",memberVO.getMname());
				model.addAttribute("id",memberVO.getId());
			}
			else {
				model.addAttribute("code" ,"create_fail");
			}	
			model.addAttribute("cnt", cnt);
		}
		else { // id 중복
		      model.addAttribute("code", "duplicte_fail");
		      model.addAttribute("cnt", 0);
		}
		return "/member/msg";
	}
	
	@GetMapping("/list")
	public String list(Model model,HttpSession session) {
    System.out.println("session.id: "+session.getAttribute("id"));
    
		ArrayList<CafeVOMenu> menu = this.cafeProc.menu();
		model.addAttribute("menu",menu);
		
		if(this.MemberProc.isMemberAdmin(session)) {
		ArrayList<MemberVO> list = this.MemberProc.list();
		model.addAttribute("list",list);

		return "member/list";
		}else {
			return"redirect:/member/login_form_need";
		}
	}
	
	@GetMapping("/read")
		public String update(Model model, int cafe_mno) {
		
		ArrayList<CafeVOMenu> menu = this.cafeProc.menu();
		model.addAttribute("menu",menu);
		
		MemberVO memberVO = this.MemberProc.read(cafe_mno);
		model.addAttribute("memberVO",memberVO);
		return "/member/read";
			
	}
	@PostMapping("/update")
	public String update_proc(Model model,MemberVO memberVO) {
		int cnt = this.MemberProc.update(memberVO);
		
	    if (cnt == 1) {
	        model.addAttribute("code", "update_success");
	        model.addAttribute("mname", memberVO.getMname());
	        model.addAttribute("id", memberVO.getId());
	      } else {
	        model.addAttribute("code", "update_fail");
	      }
	      
	      model.addAttribute("cnt", cnt);
	      return "member/msg"; // /templates/member/msg.html
	}
	
	@GetMapping("/delete")
	public String delete(Model model, Integer cafe_mno) {
		
		ArrayList<CafeVOMenu> menu = this.cafeProc.menu();
		model.addAttribute("menu",menu);
		
		MemberVO memberVO = this.MemberProc.read(cafe_mno);
		model.addAttribute("memberVO",memberVO);
		return "/member/delete";
		
	}
	
	@PostMapping("/delete")
	public String delete_proc(Model model, int cafe_mno) {
		
		
		int cnt = this.MemberProc.delete(cafe_mno);
		if(cnt==1) {
			return "redirect:/member/list";
		}
		else {
			model.addAttribute("code","delete_fail");
			return "/memeber/msg";
		}
	}
	
	@GetMapping("/logout")
	public String login_form(HttpSession session,Model model) {
		session.invalidate();
		return "redirect:/";
	}
	
	@GetMapping("/login")
	public String login_cookie(Model model, HttpServletRequest request) {
		
		Cookie[] cookies = request.getCookies();
		Cookie cookie = null;
		
		 String ck_id = ""; // id 저장
	    String ck_id_save = ""; // id 저장 여부를 체크
	    String ck_passwd = ""; // passwd 저장
	    String ck_passwd_save = ""; // passwd 저장 여부를 체크
	    
	    if(cookies!=null) {
	    	for(int i = 0;i<cookies.length;i++) {
	    		cookie=cookies[i];
	    		
	    		if(cookie.getName().equals("ck_id")) {
	    			ck_id=cookie.getValue();
	    		}else if(cookie.getName().equals("ck_id_save")) {
	    			ck_id_save = cookie.getValue();
	    		}else if(cookie.getName().equals("ck_passwd")) {
	    			ck_passwd = cookie.getValue();
	    		}else if(cookie.getName().equals("ck_passwd_save")) {
	    			ck_passwd_save = cookie.getValue();
	    		}
	    	}
	    }
	    model.addAttribute("ck_id",ck_id);
	    model.addAttribute("ck_passwd",ck_passwd);
	    model.addAttribute("ck_id_save",ck_id_save);
	    model.addAttribute("ck_passwd_save",ck_passwd_save);
	    
	    return "member/login_cookie";
	    	
	}
	
	@PostMapping("/login")
	public String login_process(HttpSession session,
										  HttpServletRequest request,
										   HttpServletResponse response,
											Model model,
											String id, 
											String passwd,
											@RequestParam(value="id_save",defaultValue="")String id_save,
			  								@RequestParam(value="passwd_save",defaultValue="")String passwd_save) {
		
		HashMap<String,Object> map = new HashMap<>();
		map.put("id", id);
		map.put("passwd", this.security.aesEncode(passwd));
		System.out.println("passwd:" +passwd);
		
		int cnt = this.MemberProc.login(map);
		System.out.println("->login_proc cnt:" + cnt);
		  
		model.addAttribute("cnt",cnt);
		
		if(cnt==1) {
			MemberVO memberVO = this.MemberProc.readById(id);
			  session.setAttribute("cafe_mno", memberVO.getCafe_mno()); //model.addAttribute 안해도 뷰에서 바로  사용 가능
			  session.setAttribute("id", memberVO.getId());
			  session.setAttribute("mname", memberVO.getMname());
			  
	      if (memberVO.getGrade() >= 1 && memberVO.getGrade() <= 10) {
	        session.setAttribute("grade", "admin");
	      } else if (memberVO.getGrade() >= 11 && memberVO.getGrade() <= 20) {
	        session.setAttribute("grade", "member");
	      } else if (memberVO.getGrade() >= 21) {
	        session.setAttribute("grade", "guest");
	      }

			  if(id_save.equals("Y")) {
				  Cookie ck_id = new Cookie("ck_id",id);
			      ck_id.setPath("/");  // root 폴더에 쿠키를 기록함으로 모든 경로에서 쿠기 접근 가능
			      ck_id.setMaxAge(60 * 60 * 24 * 30); // 30 day, 초단위
			      response.addCookie(ck_id); // id 저장 
			  }else {
				  Cookie ck_id = new Cookie("ck_id","");
				  ck_id.setPath("/"); 
			      ck_id.setMaxAge(0);
			      response.addCookie(ck_id); // id 저장
			  }
		      Cookie ck_id_save = new Cookie("ck_id_save", id_save);
		      ck_id_save.setPath("/");
		      ck_id_save.setMaxAge(60 * 60 * 24 * 30); // 30 day
		      response.addCookie(ck_id_save);
		      if (passwd_save.equals("Y")) { // 패스워드 저장할 경우
			        Cookie ck_passwd = new Cookie("ck_passwd", passwd);
			        ck_passwd.setPath("/");
			        ck_passwd.setMaxAge(60 * 60 * 24 * 30); // 30 day
			        response.addCookie(ck_passwd);
			      } else { // N, 패스워드를 저장하지 않을 경우
			        Cookie ck_passwd = new Cookie("ck_passwd", "");
			        ck_passwd.setPath("/");
			        ck_passwd.setMaxAge(0);
			        response.addCookie(ck_passwd);
			      }
			      // passwd를 저장할지 선택하는  CheckBox 체크 여부
			      Cookie ck_passwd_save = new Cookie("ck_passwd_save", passwd_save);
			      ck_passwd_save.setPath("/");
			      ck_passwd_save.setMaxAge(60 * 60 * 24 * 30); // 30 day
			      response.addCookie(ck_passwd_save);
			      
			      return "redirect:/";
			  
		}else {
			 model.addAttribute("code", "login_fail");
			 return "member/msg";
		  }
	}
	@GetMapping("/passwd_update")
	public String update_passwd(Model model, HttpSession session) {
		int cafe_mno = (Integer)session.getAttribute("cafe_mno");
		MemberVO memberVO=this.MemberProc.read(cafe_mno);
		
		model.addAttribute("memberVO", memberVO);
		return "member/passwd_update";
		
	}
	
	@PostMapping("/passwd_check")
	@ResponseBody
	public String passwd_check(HttpSession session, @RequestBody String src) {
		JSONObject json = new JSONObject(src);
		String current_passwd = json.getString("current_passwd");
		System.out.println(current_passwd);
		int cafe_mno = (int)session.getAttribute("cafe_mno");
		HashMap<String, Object>map = new HashMap<String, Object>();
		map.put("passwd", this.security.aesEncode(current_passwd));
		map.put("cafe_mno", cafe_mno);
		
		int cnt = this.MemberProc.passwd_check(map);
		JSONObject src1 = new JSONObject();
		src1.put("cnt",cnt);
		
		System.out.println(json.toString());
		return json.toString();
		
	}
	
	@PostMapping("/passwd_update")
	public String passwd_update(Model model, int cafe_mno, String passwd) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("cafe_mno", cafe_mno);
		map.put("passwd", this.security.aesEncode(passwd));
		
		int cnt = this.MemberProc.passwd_update(map);
		if(cnt==1) {
			return "redirect:/";
		}else {
			model.addAttribute("code","passwd_fail");
			model.addAttribute("cnt",cnt);
			return "member/msg";
		}
	}
	
	@GetMapping("/login_form_need")
	public String login_form_need(Model model, MemberVO memberVO) {
		model.addAttribute("memberVO",memberVO);
		return "member/login_cookie_need";
	}


	
	
}
	

