package com.cvsher.spider.parser;

import java.util.List;

/**
 * 解析爬取回来的内容
* @author oujiah
* @date 2016年2月16日下午5:18:32
*
 */
public interface IParser {

	/**
	 * 对网页内容进行解析处理
	* @author oujiah
	* @date 2016年2月17日上午9:29:55
	* @param content
	* @return content中可用于下次爬取的url列表【对此方法的调用是非线程安全的，要在此方法里保证线程安全】
	 */
	public List<String> parse(byte[] content);
	
}
