package com.jianfei.core.dto;

public class CharData {
	private String cacheKey;
	private String pcount;
	private String avgNum;
	private String bsum;
	private String bid;
	/**
	 * @return the cacheKey
	 */
	public String getCacheKey() {
		return cacheKey;
	}
	/**
	 * @param cacheKey the cacheKey to set
	 */
	public void setCacheKey(String cacheKey) {
		this.cacheKey = cacheKey;
	}
	/**
	 * @return the pcount
	 */
	public String getPcount() {
		return pcount;
	}
	/**
	 * @param pcount the pcount to set
	 */
	public void setPcount(String pcount) {
		this.pcount = pcount;
	}
	/**
	 * @return the avgNum
	 */
	public String getAvgNum() {
		return avgNum;
	}
	/**
	 * @param avgNum the avgNum to set
	 */
	public void setAvgNum(String avgNum) {
		this.avgNum = avgNum;
	}
	/**
	 * @return the bsum
	 */
	public String getBsum() {
		return bsum;
	}
	/**
	 * @param bsum the bsum to set
	 */
	public void setBsum(String bsum) {
		this.bsum = bsum;
	}
	/**
	 * @return the bid
	 */
	public String getBid() {
		return bid;
	}
	/**
	 * @param bid the bid to set
	 */
	public void setBid(String bid) {
		this.bid = bid;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CharData [cacheKey=" + cacheKey + ", pcount=" + pcount + ", avgNum=" + avgNum + ", bsum=" + bsum
				+ ", bid=" + bid + "]";
	}
	
}
