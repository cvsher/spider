package com.cvsher.spider.downloader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
* @author oujiah
* @date 2016年2月17日上午8:47:16
*
 */
@Component("pageDownloader")
public class PageDownloader implements IDownLoader {
	Logger logger = LoggerFactory.getLogger(PageDownloader.class);
	
	@Override
	public byte[] download(String url) {
		HttpClient client = new HttpClient();
		HttpMethod getMethod = new GetMethod(url);
		try {
			int resultCode = client.executeMethod(getMethod);
			if(200 != resultCode){
				logger.warn("请求【{}】失败，返回结果【{}】", url, resultCode);
			}else{
				return getMethod.getResponseBody();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
