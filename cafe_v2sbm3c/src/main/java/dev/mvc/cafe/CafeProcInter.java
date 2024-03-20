package dev.mvc.cafe;

import java.util.ArrayList;

public interface CafeProcInter {
  
  public int create(CafeVO cafeVO);
  
  public ArrayList list_all();
  
  public CafeVO read(int n);
  
  public int update(CafeVO cafeVO);
  
  public int delete(int cafeno);
  
  /**
   * 출력 순위 높임
   * delete id="update_seqno_forward" parameterType="Integer"
   * @param cateno
   * @return
   */  
  public int update_seqno_forward(int cafeno);
 
 /**
  * 출력 순위 낮춤
  * delete id="update_seqno_backward" parameterType="Integer"
  * @param cateno
  * @return
  */
  public int update_seqno_backward(int cafeno);
  
  public int update_visible_y(int cafeno);
  
  public int update_visible_n(int cafeno);
  

}
