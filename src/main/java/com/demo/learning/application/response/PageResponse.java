package com.demo.learning.application.response;


import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Setter
@Getter
public class PageResponse<T> {

  List<T> data;
  int totalPage;
  long totalRecords;
  boolean first;
  boolean last;
  int pageSize = 5;

  public static <T> PageResponse<T> from(List<T> datas, Page page) {
    PageResponse<T> pageResp = new PageResponse<>();
    pageResp.setData(datas);
    pageResp.setTotalPage(page.getTotalPages());
    pageResp.setTotalRecords(page.getTotalElements());
    pageResp.setFirst(page.isFirst());
    pageResp.setLast(page.isLast());
    pageResp.setPageSize(datas.size());
    return pageResp;
  }
}
