package dev.mvc.cafe;

import java.util.ArrayList;

public interface CafeProcInter {
  
  public int create(CafeVO cafeVO);
  
  public ArrayList list_all();
  
  public CafeVO read(int n);
  
  public int update(CafeVO cafeVO);
  
  public int delete(int cafeno);
  

}
