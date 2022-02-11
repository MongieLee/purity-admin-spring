package com.example.demo.entity;

public class BaseListResult extends Result {
    private Integer page;
    private Integer total;
    private Long totalPage;

    protected BaseListResult(ResultEnum status, String msg, Object data) {
        super(status, msg, data);
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Long totalPage) {
        this.totalPage = totalPage;
    }
}
