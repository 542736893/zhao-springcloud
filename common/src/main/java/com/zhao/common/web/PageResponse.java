package com.zhao.common.web;

import java.util.List;

/**
 * 分页响应类
 */
public class PageResponse<T> {
    
    private List<T> records;
    private long total;
    private int pageNum;
    private int pageSize;
    private int pages;
    
    public PageResponse() {
    }
    
    public PageResponse(List<T> records, long total, int pageNum, int pageSize) {
        this.records = records;
        this.total = total;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.pages = (int) Math.ceil((double) total / pageSize);
    }

    /**
     * 获取记录列表
     */
    public List<T> getRecords() {
        return records;
    }

    /**
     * 设置记录列表
     */
    public void setRecords(List<T> records) {
        this.records = records;
    }

    /**
     * 获取总记录数
     */
    public long getTotal() {
        return total;
    }

    /**
     * 设置总记录数
     */
    public void setTotal(long total) {
        this.total = total;
    }

    /**
     * 获取当前页码
     */
    public int getPageNum() {
        return pageNum;
    }

    /**
     * 设置当前页码
     */
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    /**
     * 获取每页记录数
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 设置每页记录数
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 获取总页数
     */
    public int getPages() {
        return pages;
    }

    /**
     * 设置总页数
     */
    public void setPages(int pages) {
        this.pages = pages;
    }
    
    /**
     * 创建分页响应
     */
    public static <T> PageResponse<T> of(List<T> records, long total, int pageNum, int pageSize) {
        return new PageResponse<>(records, total, pageNum, pageSize);
    }
    
    /**
     * 创建空分页响应
     */
    public static <T> PageResponse<T> empty(int pageNum, int pageSize) {
        return new PageResponse<>(List.of(), 0, pageNum, pageSize);
    }
    
    /**
     * 检查是否有数据
     */
    public boolean hasData() {
        return records != null && !records.isEmpty();
    }
    
    /**
     * 检查是否是第一页
     */
    public boolean isFirstPage() {
        return pageNum == 1;
    }
    
    /**
     * 检查是否是最后一页
     */
    public boolean isLastPage() {
        return pageNum >= pages;
    }
    
    /**
     * 检查是否有下一页
     */
    public boolean hasNextPage() {
        return pageNum < pages;
    }
    
    /**
     * 检查是否有上一页
     */
    public boolean hasPreviousPage() {
        return pageNum > 1;
    }
}
