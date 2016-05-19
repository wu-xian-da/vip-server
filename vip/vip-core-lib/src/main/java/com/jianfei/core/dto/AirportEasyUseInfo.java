package com.jianfei.core.dto;

/**
 * 空港易行VIp卡使用记录Info
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/18 17:56
 */
public class AirportEasyUseInfo {
    //// TODO: 2016/5/18 返回信息序列化

    /**
     * 批次号
     *
     * "20150114010201",  nvarchar(18)
     */
    private String BatchNo;
    /**
     *  返回已核销的总记录数
     */
    private int CountNo;

    /**
     * 返回新核销的记录数
     */
    private  int  RecordNo;

    public String getBatchNo() {
        return BatchNo;
    }

    public void setBatchNo(String batchNo) {
        BatchNo = batchNo;
    }

    public int getCountNo() {
        return CountNo;
    }

    public void setCountNo(int countNo) {
        CountNo = countNo;
    }

    public int getRecordNo() {
        return RecordNo;
    }

    public void setRecordNo(int recordNo) {
        RecordNo = recordNo;
    }
}
