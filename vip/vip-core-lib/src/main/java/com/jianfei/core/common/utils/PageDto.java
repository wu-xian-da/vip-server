package com.jianfei.core.common.utils;

/**
 * 分页
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/20 15:03
 */
public class PageDto {
    /**
     * 每页大小
     */
    private int  pageSize;
    /**
     *  页码
     */
    private int pageNo;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
}
