package dev.mvc.cafe_v1sbm3c;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import dev.mvc.cafe.CafeDAOInter;
import dev.mvc.cafe.CafeVO;

@SpringBootTest
class CafeV1sbm3cApplicationTests {
  
  @Autowired
  private CafeDAOInter cafeDAO;

	@Test
	void contextLoads() {
	}
	
	@Test
	public void testCreate() {
	  CafeVO cafeVO = new CafeVO();
	  cafeVO.setName("청학");
	  
	  int cnt = this.cafeDAO.create(cafeVO);
	  System.out.println("-> cnt: " + cnt);
	}
	

}
