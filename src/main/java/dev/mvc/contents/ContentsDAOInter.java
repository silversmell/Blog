package dev.mvc.contents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Spring Boot가 자동 구현
 * @author soldesk
 *
 */
public interface ContentsDAOInter {
  /**
   * 등록, 추상 메소드
   * @param contentsVO
   * @return
   */
  public int create(ContentsVO contentsVO);

  /**
   * 모든 카테고리의 등록된 글목록
   * @return
   */
  public ArrayList<ContentsVO> list_all();
  
  /**
   * 카테고리별 등록된 글 목록
   * @param cafeno
   * @return
   */
  public ArrayList<ContentsVO> list_by_cafeno(int cafeno);
  
  /**
   * 조회
   * @param cafe_contentsno
   * @return
   */
  public ContentsVO read(int cafe_contentsno);
  
  /**
   * map 등록, 수정, 삭제
   * @param map
   * @return 수정된 레코드 갯수
   */
  public int map(HashMap<String, Object> map);

  /**
   * youtube 등록, 수정, 삭제
   * @param youtube
   * @return 수정된 레코드 갯수
   */
  public int youtube(HashMap<String, Object> map);
  
  /**
   * 카테고리별 검색 목록
   * @param map
   * @return
   */
  public ArrayList<ContentsVO> list_by_cafeno_search(HashMap<String, Object> hashMap);
  
  /**
   * 카테고리별 검색된 레코드 갯수
   * @param map
   * @return
   */
  public int search_count(HashMap<String, Object> hashMap);
  
  /**
   * 카테고리별 검색 목록 + 페이징
   * @param map
   * @return
   */
  public ArrayList<ContentsVO> list_by_cafeno_search_paging(HashMap<String, Object> map);
  
  /**
   * 패스워드 검사
   * @param hashMap
   * @return
   */
  public int password_check(HashMap<String, Object> hashMap);
  
  /**
   * 글 정보 수정
   * @param contentsVO
   * @return 처리된 레코드 갯수
   */
  public int update_text(ContentsVO contentsVO);

  /**
   * 파일 정보 수정
   * @param contentsVO
   * @return 처리된 레코드 갯수
   */
  public int update_file(ContentsVO contentsVO);
 
  /**
   * 삭제
   * @param cafe_contentsno
   * @return 삭제된 레코드 갯수
   */
  public int delete(int cafe_contentsno);
  
  /**
   * FK cafeno 값이 같은 레코드 갯수 산출
   * @param cafeno
   * @return
   */
  public int count_by_cafeno(int cafeno);
 
  /**
   * 특정 카테고리에 속한 모든 레코드 삭제
   * @param cafeno
   * @return 삭제된 레코드 갯수
   */
  public int delete_by_cafeno(int cafeno);

  /**
   * FK cafe_mno 값이 같은 레코드 갯수 산출
   * @param cafe_mno
   * @return
   */
  public int count_by_cafe_mno(int cafe_mno);
  
  public int list_by_cafeno_search_count(HashMap<String, Object> hashMap);
 
  /**
   * 특정 카테고리에 속한 모든 레코드 삭제
   * @param cafe_mno
   * @return 삭제된 레코드 갯수
   */
  public int delete_by_cafe_mno(int cafe_mno);
  
  
}
 
 

 

