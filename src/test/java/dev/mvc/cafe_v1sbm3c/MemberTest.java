package dev.mvc.cafe_v1sbm3c;

import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import dev.mvc.member.MemberProc;
import dev.mvc.member.MemberProcInter;

@SpringBootTest
public class MemberTest {
	
  @Autowired
  @Qualifier("dev.mvc.member.MemberProc")  // @Component("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc;
  
  @Test
  public void checkId() {
	  int cnt = this.memberProc.checkID("admin");
	  System.out.println("cnt ->" + cnt);
  }
  

}