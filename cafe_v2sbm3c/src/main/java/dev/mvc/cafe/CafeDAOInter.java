package dev.mvc.cafe;

import java.util.ArrayList;

import dev.mvc.cafe.CafeVO;

public interface CafeDAOInter {
  
  /**
   * 등록
   * @param cateVO
   * @return 등록한 레코드 갯수
   */
  
  public int create(CafeVO cafeVO);
  
  public ArrayList list_all();

  public CafeVO read(int n);
  
  public int delete(int cafeno);
  
  public int update(CafeVO cafeVO);
  
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
  
  /**
   * 카테고리 공개 설정
   * update id="update_visible_y" parameterType="int"
   * @param cafeno
   * @return
   */
  
  public int update_visible_y(int cafeno);
    
  
  /**
   * 카테고리 비공개 설정
   * update id="update_visible_y" parameterType="int"
   * @param cafeno
   * @return
   */
  
  public int update_visible_n(int cafeno);
  
  
  
  
}