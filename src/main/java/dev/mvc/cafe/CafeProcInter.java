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
  
  /**
   * 검색 + 페이징 목록
   * @param word 검색어
   * @param now_page 현재 페이지
   * @param record_per_page 페이지당 레코드수
   * @return
   */
  public ArrayList<CafeVO> list_search_paging(String word, int now_page, int record_per_page);
  
  /**
   * 검색된 레코드 수
   * @param word
   * @return
   */
  public int list_search_count(String word);
  /** 
   * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 
   * 현재 페이지: 11 / 22   [이전] 11 12 13 14 15 16 17 18 19 20 [다음] 
   *
   * @param now_page  현재 페이지
   * @param word 검색어
   * @param list_file 목록 파일명
   * @param search_count 검색 레코드수   
   * @param record_per_page 페이지당 레코드 수
   * @param page_per_block 블럭당 페이지 수
   * @return 페이징 생성 문자열
   */ 
  
  public String pagingBox(int now_page,String word, String list_file, int search_count, int record_per_page,int page_per_block);

}
