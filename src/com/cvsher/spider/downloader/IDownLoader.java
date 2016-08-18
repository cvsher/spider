package com.cvsher.spider.downloader;

/**
 * 下载器接口，请求特定的url，下载网页
* @author oujiah
* @date 2016年2月16日下午5:08:53
*
 */
public interface IDownLoader {

	/**
	 * 
	* @author oujiah
	* @date 2016年2月16日下午5:12:32
	* @param url
	* @return 访问url的返回内容【对此方法的调用是非线程安全的，要在此方法里保证线程安全】
	 */
	public byte[] download(String url);
	
}
