package dev.mvc.cafe_v1sbm3c;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeCont {
  public HomeCont() {
    System.out.println("-> HomeCont created.");
  }
  
  @GetMapping(value="/") // http://localhost:9091
  public String home() { // 파일명 return
    return "index"; // /templates/index.html  
  }
  
}
