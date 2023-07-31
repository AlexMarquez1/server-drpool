package com.isae.drpool.drpool.utils;

import java.util.List;
import java.util.stream.Collectors;

public class PagingList<T> {
	/**
     * paginas totales 
*/
private int totalPage = 0;

/**
     * Página actual
*/
private int curPageNo = 0;

/**
     * El tamaño de cada página
*/
private int pageSize = 0;

/**
     * Tamaño predeterminado por página
*/
private static final int DEFAULT_PAGE_SIZE = 500;


private List<T> pageData = null;

public PagingList(List<T> pageResult, int pageSize) {
  this.pageSize = pageSize;
  this.pageData = pageResult;
  init(pageResult, pageSize);
}


public PagingList(List<T> pageResult) {
  this(pageResult, DEFAULT_PAGE_SIZE);
}


private void init(List<T> pageResult, int pageSize) {
  if (pageSize <= 0) {
      throw new IllegalArgumentException("Paging size must be greater than zero.");
  }
  if (null == pageResult) {
      throw new NullPointerException("Paging resource list must be not null.");
  }
  if (pageResult.size() % pageSize > 0) {
      this.totalPage = (pageResult.size() / pageSize) + 1;
  } else {
      this.totalPage = pageResult.size() / pageSize;
  }
}

/**
     * Devuelve las páginas restantes actuales
*
* @return
*/
private int getSurplusPage() {
  if (pageData.size() % pageSize > 0) {
      return (pageData.size() / pageSize) + 1;
  } else {
      return pageData.size() / pageSize;
  }

}

/**
     * Devuelve si hay datos en la página siguiente
*
* @return
*/
public boolean hasNext() {
  return pageData.size() > 0;
}

/**
     * Después de obtener la paginación, el número total de páginas
*
* @return
*/
public int getTotalPage() {
  return totalPage;
}

public List<T> next() {
  List<T> pagingData = pageData.stream().limit(pageSize).collect(Collectors.toList());
  pageData = pageData.stream().skip(pageSize).collect(Collectors.toList());
  return pagingData;
}

/**
     * Devuelve el número de página actual
*
* @return
*/
public int getCurPageNo() {
  return totalPage - getSurplusPage();
}

}
