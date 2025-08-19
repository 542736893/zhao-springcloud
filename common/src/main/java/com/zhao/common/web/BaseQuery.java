package com.zhao.common.web;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * 查询基类
 */
public class BaseQuery {
    
    /**
     * 页码，从1开始
     */
    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码必须大于0")
    private Integer pageNum = 1;
    
    /**
     * 每页大小
     */
    @NotNull(message = "每页大小不能为空")
    @Min(value = 1, message = "每页大小必须大于0")
    @Max(value = 1000, message = "每页大小不能超过1000")
    private Integer pageSize = 20;
    
    /**
     * 排序字段
     */
    private String sortBy;
    
    /**
     * 排序方向：asc, desc
     */
    private String sortDirection = "desc";
    
    /**
     * 获取偏移量
     */
    public int getOffset() {
        return (pageNum - 1) * pageSize;
    }
    
    /**
     * 检查是否需要分页
     */
    public boolean needPagination() {
        return pageSize > 0 && pageSize < Integer.MAX_VALUE;
    }
    
    /**
     * 获取排序SQL片段
     */
    public String getOrderByClause() {
        if (sortBy == null || sortBy.trim().isEmpty()) {
            return "";
        }
        String direction = "asc".equalsIgnoreCase(sortDirection) ? "ASC" : "DESC";
        return String.format(" ORDER BY %s %s", sortBy, direction);
    }

    // Getter and Setter methods
    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }
}