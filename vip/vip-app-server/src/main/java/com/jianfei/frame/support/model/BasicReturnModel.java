
package com.jianfei.frame.support.model;

import java.util.List;

/**
 * 基本返回值类型
 * @param <T>
 */
public class BasicReturnModel<T> {
    /**
     * 结果总数
     */
    private long total;

    /**
     * 结果集
     */
    private List<T> data;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
