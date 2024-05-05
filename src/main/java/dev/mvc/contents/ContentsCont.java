package dev.mvc.contents;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.mvc.cafe.CafeProcInter;
import dev.mvc.cafe.CafeVO;
import dev.mvc.cafe.CafeVOMenu;
import dev.mvc.member.MemberProcInter;
import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/contents")
public class ContentsCont {
  
  public ContentsCont() {
    System.out.println("-> ContentCont created");
  }
  
  @Autowired
  @Qualifier("dev.mvc.cafe.CafeProc")
  private CafeProcInter cafeProc;
  
  @Autowired
  @Qualifier("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc;
  
  @Autowired
  @Qualifier("dev.mvc.contents.ContentsProc")
  private ContentsProcInter contentsProc;
  
  @GetMapping("/msg")
  public String msg(String url) {
    return url;
  }
  
  @GetMapping("/create")
  public String create(Model model,ContentsVO contentsVO,HttpSession session,int cafeno) {
    
    ArrayList<CafeVOMenu> menu = this.cafeProc.menu();
    model.addAttribute("menu",menu);
    
      CafeVO cafeVO = cafeProc.read(cafeno);
      model.addAttribute("cafeVO",cafeVO);
      
      System.out.println();
      model.addAttribute("contentsVO",contentsVO);
      return "contents/create";
    }

  
  @PostMapping("/create")
  public String create_form(HttpServletRequest request,Model model, int cafeno, ContentsVO contentsVO,HttpSession session,RedirectAttributes ra) {
    
    String file1="";
    String file1saved="";
    String thumb1="";
    
    String upDir = Contents.getUploadDir();
    System.out.println("-> upDir:" + upDir);
    
    MultipartFile mf = contentsVO.getFile1MF();
    
    file1 = mf.getOriginalFilename(); // 원본 파일명 산출, 01.jpg
    System.out.println("-> 원본 파일명 산출 file1: " + file1);
    
    long size1 = mf.getSize();
    if(size1>0) {
      if(Tool.checkUploadFile(file1) == true) {
        file1saved = Upload.saveFileSpring(mf, upDir);
        if(Tool.isImage(file1saved)) {
          thumb1 = Tool.preview(upDir, file1saved, 200, 150);
        }
        contentsVO.setFile1(file1); // 순수 원본 파일명
        contentsVO.setFile1saved(file1saved); // 저장된 파일명(파일명 중복 처리)
        contentsVO.setThumb1(thumb1); // 원본이미지 축소판
        contentsVO.setSize1(size1); // 파일 크기

      }
    }
    int cafe_mno=(int)session.getAttribute("cafe_mno");
    contentsVO.setCafe_mno(cafe_mno);
    int cnt = this.contentsProc.create(contentsVO);
    if(cnt==1) {
//      ra.addFlashAttribute("code", "create_success");
//      ra.addFlashAttribute("url", "/contents/msg"); 
      ra.addAttribute("cafeno",cafeno);
      return "redirect:/contents/list_by_cafeno_search_paging";
    }
    else {
      ra.addFlashAttribute("code", "create_fail");
      ra.addFlashAttribute("url", "/contents/msg"); 
      return "redirect:/contents/msg";
    }

  }
  @GetMapping("/list_by_cafeno_search_paging")
  public String list_by_cafeno(Model model, int cafeno,
      @RequestParam(name = "now_page", defaultValue = "1") int now_page,
      @RequestParam(name = "word", defaultValue = "") String word) {
    
    ArrayList<CafeVOMenu> menu = this.cafeProc.menu();
    model.addAttribute("menu",menu);
    
    CafeVO cafeVO = this.cafeProc.read(cafeno);
    model.addAttribute("cafeVO",cafeVO);
    
    HashMap<String, Object> map  = new HashMap<>();
    map.put("now_page", now_page);
    map.put("word", word);
    map.put("cafeno", cafeno);
    
    ArrayList<ContentsVO> list = this.contentsProc.list_by_cafeno_search_paging(map);
    model.addAttribute("list",list);
    
    
    int search_count=this.contentsProc.list_by_cafeno_search_count(map);
    String paging=this.contentsProc.pagingBox(cafeno, now_page, word, "./list_by_cafeno_search_paging", search_count, Contents.RECORD_PER_PAGE, Contents.PAGE_PER_BLOCK);
    model.addAttribute("paging",paging);
    model.addAttribute("word",word);
    model.addAttribute("now_page",now_page);
    model.addAttribute("search_count",search_count);

   return "contents/list_by_cafeno_search_paging";
  }
  
  @GetMapping("/read")
  public String read(Model model,HttpSession session,String word,int cafe_contentsno,
      @RequestParam(name = "now_page", defaultValue = "1") int now_page) {
    
    ArrayList<CafeVOMenu> menu = this.cafeProc.menu();
    model.addAttribute("menu",menu);
    System.out.println("cafe_contentsno->" + cafe_contentsno);
    ContentsVO contentsVO = this.contentsProc.read(cafe_contentsno);
    
    long size1 = contentsVO.getSize1();
    String size_label = Tool.unit(size1);
    contentsVO.setSize1(size1);
    model.addAttribute("contentsVO",contentsVO);
    
    CafeVO cafeVO = this.cafeProc.read(contentsVO.getCafeno());
    model.addAttribute("cafeVO",cafeVO);
    model.addAttribute("session",session);
    model.addAttribute("word",word);
    model.addAttribute("cafe_contentsno",cafe_contentsno);
    model.addAttribute("now_page",now_page);
    return "contents/read";
  }
  
  @GetMapping("/delete")
    public String delete_form(int cafe_contentsno,
        Model model,
        @RequestParam(name = "now_page", defaultValue = "1") int now_page,
        @RequestParam(name = "word", defaultValue = " ") String word ) {
      ContentsVO contentsVO = this.contentsProc.read(cafe_contentsno);
      model.addAttribute("contentsVO",contentsVO);
      model.addAttribute("now_page",now_page);
      model.addAttribute("word",word);
      CafeVO cafeVO = this.cafeProc.read(contentsVO.getCafeno());
      model.addAttribute("cafeVO",cafeVO);
      
      return "contents/delete";
    }
  @PostMapping("/delete")
  public String delete(int cafe_contentsno,int cafeno,
        Model model,
        @RequestParam(name = "now_page", defaultValue = "1") int now_page,
        @RequestParam(name = "word", defaultValue = " ") String word,RedirectAttributes ra ) {
    
      ContentsVO contentsVO = this.contentsProc.read(cafe_contentsno);
      String file1saved = contentsVO.getFile1saved(); /** 실제 저장된 메인 이미지 */
      String thumb1 = contentsVO.getThumb1(); /** 메인 이미지 preview */

      String uploadDir = Contents.getUploadDir();
      Tool.deleteFile(uploadDir, thumb1);     // preview 이미지 삭제
      Tool.deleteFile(uploadDir, file1saved); 
      this.contentsProc.delete(cafe_contentsno);

      CafeVO cafeVO = this.cafeProc.read(cafeno);
      ArrayList<ContentsVO> list = this.contentsProc.list_all();
      ra.addAttribute("cafeno",cafeno);
      ra.addAttribute("word", word);
      ra.addAttribute("now_page",now_page);
      return "redirect:/contents/list_by_cafeno_search_paging";
  }
  @GetMapping("/update_text")
  public String update_text_form(Model model,int cafe_contentsno,
      @RequestParam(name = "now_page", defaultValue = "1") int now_page,
      @RequestParam(name = "word", defaultValue = " ") String word,HttpSession session ) {
    if(this.memberProc.isMember(session)) {
     ContentsVO contentsVO = this.contentsProc.read(cafe_contentsno);
     model.addAttribute("contentsVO", contentsVO);
     CafeVO cafeVO = this.cafeProc.read(contentsVO.getCafeno());
     model.addAttribute("cafeVO",cafeVO);
     model.addAttribute("now_page",now_page);
     model.addAttribute("word",word);
     return "contents/update_text";
     }else {
       return "redirect:/member/login_form_need";
     }
  }
  @PostMapping("/update_text")
  public String update_text(RedirectAttributes ra,ContentsVO contentsVO,
      @RequestParam(name = "now_page", defaultValue = "1") int now_page,
      @RequestParam(name = "word", defaultValue = " ") String word) {
    
    this.contentsProc.update_text(contentsVO);
    ra.addAttribute("cafe_contentsno", contentsVO.getCafe_contentsno());
    ra.addAttribute("word", word);
    ra.addAttribute("now_page", now_page);

    return "redirect:/contents/read";
    
  }
  @GetMapping("/update_file")
  public String update_file_form(Model model,int cafe_contentsno,
      @RequestParam(name = "now_page", defaultValue = "1") int now_page,
      @RequestParam(name = "word", defaultValue = " ") String word) {
    ContentsVO contentsVO = this.contentsProc.read(cafe_contentsno);
    model.addAttribute("contentsVO", contentsVO);
    CafeVO cafeVO = this.cafeProc.read(contentsVO.getCafeno());
    model.addAttribute("cafeVO",cafeVO);
    model.addAttribute("now_page",now_page);
    model.addAttribute("word",word);
    
    return "contents/update_file";
    
  }
  
  @PostMapping("/update_file")
  public String update_file(RedirectAttributes ra,ContentsVO contentsVO,
      @RequestParam(name = "now_page", defaultValue = "1") int now_page,
      @RequestParam(name = "word", defaultValue = " ") String word) {
    ContentsVO contentsVO_old = this.contentsProc.read(contentsVO.getCafe_contentsno());
    String file1saved = contentsVO_old.getFile1saved(); 
    String thumb1 = contentsVO_old.getThumb1(); 
    long size1=0;
    
    String upDir = Contents.getUploadDir(); 

    Tool.deleteFile(upDir, file1saved); 
    Tool.deleteFile(upDir, thumb1); 
    
    String file1="";
    MultipartFile mf = contentsVO.getFile1MF();
    file1 = mf.getOriginalFilename();
    size1 = mf.getSize();
    
    if(size1>0) {
      file1saved = Upload.saveFileSpring(mf, upDir);
      if(Tool.isImage(file1saved)) {
        thumb1 = Tool.preview(upDir, file1, 250,200);
      }
    }else {
        file1="";
        file1saved="";
        thumb1="";
        size1=0;
      }
      contentsVO.setFile1(file1);
      contentsVO.setFile1saved(file1saved);
      contentsVO.setThumb1(thumb1);
      contentsVO.setSize1(size1);
      
      this.contentsProc.update_file(contentsVO); // Oracle 처리
      ra.addAttribute ("cafe_contentsno", contentsVO.getCafe_contentsno());
      ra.addAttribute("cafeno", contentsVO.getCafeno());
      ra.addAttribute("word", word);
      ra.addAttribute("now_page", now_page);
      
      return "redirect:/contents/read";
      
    }
  }

  

