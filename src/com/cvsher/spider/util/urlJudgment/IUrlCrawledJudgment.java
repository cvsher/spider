package com.cvsher.spider.util.urlJudgment;

public interface IUrlCrawledJudgment {
	/**
	 * 判断url是否已经爬取过
	* @author oujiah
	* @date 2016年2月19日上午10:45:40
	* @param url
	* @return 若url已经爬取过返回true, 否则返回false
	 */
	public boolean isUrlCrawled(String url);
	
	/**
	 * 将url设为已经爬取过
	* @author oujiah
	* @date 2016年2月19日下午2:07:13
	* @param url
	 */
	public void setUrlCrawled(String url);
	
}
