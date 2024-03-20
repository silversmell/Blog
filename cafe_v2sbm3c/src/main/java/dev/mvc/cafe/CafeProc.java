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

}