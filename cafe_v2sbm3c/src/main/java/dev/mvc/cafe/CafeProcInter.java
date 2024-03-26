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
  
  /**
   * 중분류 가져오기
   * select id="list_all_y"
   * @return ArrayList<CafeVO>
   */
  public ArrayList<CafeVO> list_all_name_y();
  
  /**
   * 중분류를 이용해서 소분류 가져오기
   * select id="list_all_namesub_y"
   * @return ArrayList<CafeVO>
   */
  public ArrayList<CafeVO>list_all_namesub_y(String name);
  
  /**
   * 
   * @return
   */
  public ArrayList<CafeVOMenu> menu();
  
  /**
   * 관리자용 검색 목록
   * select id="list_search" resultType="dev.mvc.cate.CateVO" parameterType="String"
   * @param map
   * @return 조회한 레코드 목록
   */
  public ArrayList<CafeVO> list_search(String word);  
  

}
