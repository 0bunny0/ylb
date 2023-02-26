package com.powernode.api.pojo;

/**
 * 分页信息
 */
public class PageInfo {
    /*页码*/
    private Integer pageNo;
    /*一页几个数据*/
    private Integer pageSize;
    /*总行数*/
    private Integer totalRows;
    /*总页数*/
    private Integer totalPages;

    public PageInfo() {
    }

    public PageInfo(Integer pageNo, Integer pageSize, Integer totalRows, Integer totalPages) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalRows = totalRows;
        this.totalPages = totalPages;
    }

    @Override
    public String toString() {
        return "PageInfo{" +
                "pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", totalRows=" + totalRows +
                ", totalPages=" + totalPages +
                '}';
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(Integer totalRows) {
        this.totalRows = totalRows;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }
}
