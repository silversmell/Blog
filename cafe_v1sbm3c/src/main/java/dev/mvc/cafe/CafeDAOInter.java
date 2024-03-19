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
  
  public int delete(CafeVO cafeVO);
  
  public ArrayList list_all();

  public CafeVO read(int n);
  
  public int update(CafeVO cafeVO);
  
  /**
   * delete
   * delete id="delete" parameterType="Integer"
   * @param cateno
   * @return
   */
  public int delete(int cafeno);
}