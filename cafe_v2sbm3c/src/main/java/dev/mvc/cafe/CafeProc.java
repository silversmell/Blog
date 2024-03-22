package dev.mvc.cafe;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import dev.mvc.cafe.CafeVO;

@Service("dev.mvc.cafe.CafeProc")
public class CafeProc implements CafeProcInter {
  
  @Autowired  //CafeDAOInter 의존성 주입
  private CafeDAOInter cafeDAO;
  
  public CafeProc() {
    System.out.println("CafeProc created.");
  };

  @Override
  public int create(CafeVO cafeVO) {
    int cnt = cafeDAO.create(cafeVO);
    return cnt;
  }

  @Override
  public ArrayList list_all() {
    ArrayList<CafeVO> list=this.cafeDAO.list_all();
    return list;
  }

  @Override
  public CafeVO read(int n) {
    CafeVO cafeVO = this.cafeDAO.read(n);
    return cafeVO;
  }
  @Override
  public int update(CafeVO cafeVO) {
    int cnt = this.cafeDAO.update(cafeVO);
    return cnt;
  }

  @Override
  public int delete(int cafeno) {
    int cnt = this.cafeDAO.delete(cafeno);
    return cnt;
  }

@Override
public int update_seqno_forward(int cafeno) {
	int cnt = this.cafeDAO.update_seqno_forward(cafeno);
	return cnt;
}

@Override
public int update_seqno_backward(int cafeno) {
	int cnt = this.cafeDAO.update_seqno_backward(cafeno);
	return cnt;
}

@Override
public int update_visible_y(int cafeno) {
	int cnt = this.cafeDAO.update_visible_y(cafeno);
	return cnt;
}

@Override
public int update_visible_n(int cafeno) {
	int cnt = this.cafeDAO.update_visible_n(cafeno);
	return cnt;
}

@Override
public ArrayList<CafeVO> list_all_name_y() {
	ArrayList<CafeVO> list = this.cafeDAO.list_all_name_y();
	return list;
}

@Override
public ArrayList<CafeVO> list_all_namesub_y(String name) {
	ArrayList<CafeVO> list = this.cafeDAO.list_all_namesub_y(name);
	return list;
}

@Override
public ArrayList<CafeVOMenu> menu() {
	//중분류 목록 저장할 객체 선언
	ArrayList<CafeVOMenu> menu = new ArrayList<CafeVOMenu>();
	
	//중분류 목록 로딩
	ArrayList<CafeVO> list = this.cafeDAO.list_all_name_y();
	
	//중분류+소분류 집합
	for(CafeVO cafeVO:list) {
		CafeVOMenu cafeVOMenu = new CafeVOMenu(); //하나의 중분류 메뉴 그룹
		cafeVOMenu.setName(cafeVO.getName()); //cafeVO에 있던 name을 CafeVOMenu에 name 세팅
		
		//중분류를 이용하여 소분류를 가져옴.
		ArrayList<CafeVO> list_namesub = this.cafeDAO.list_all_namesub_y(cafeVOMenu.getName());
		cafeVOMenu.setList_namesub(list_namesub);
		
		if(list_namesub.size()>0) {
			menu.add(cafeVOMenu);
		}
	}
	return menu;
}
}