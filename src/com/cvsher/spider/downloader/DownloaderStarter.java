package com.cvsher.spider.downloader;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cvsher.spider.queue.IQueue;

public class DownloaderStarter {
	
	private static final Logger logger = LoggerFactory.getLogger(DownloaderStarter.class);

	/**
	 * 下载线程数，默认是5
	 */
	@Autowired(required=false)
	private int threadCount = 5;
	@Autowired
	private IQueue<String> urlQueue;
	@Autowired
	private IQueue<String> contentQueue;
	@Autowired
	IDownLoader downloader;
	
	public DownloaderStarter(){
	}
	public DownloaderStarter(int threadCount){
		if(threadCount <= 0){
			this.threadCount = 5;
		}else{
			this.threadCount = threadCount;
		}
	}
	
	public void start(){
		logger.info("启动下载器。。。");
		ExecutorService pool = Executors.newFixedThreadPool(threadCount);
		for(int i=0; i<threadCount; i++){
			pool.execute(new Runnable() {
				
				@Override
				public void run() {
					while(true){
						logger.info("开始下载网页");
						String url = urlQueue.dequeue();
						logger.info("下载网页【{}】", url);
						byte[] pageContent = downloader.download(url);
						try {
							contentQueue.enqueue(new String(pageContent, "utf-8"));
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
							contentQueue.enqueue(new String(pageContent));
						}
					}
				}
			});
		}
	}

	public int getThreadCount() {
		return threadCount;
	}

	public void setThreadCount(int threadCount) {
		if(threadCount <= 0){
			this.threadCount = 5;
		}else{
			this.threadCount = threadCount;
		}
	}
	public IQueue<String> getUrlQueue() {
		return urlQueue;
	}
	public void setUrlQueue(IQueue<String> urlQueue) {
		this.urlQueue = urlQueue;
	}
	public IQueue<String> getContentQueue() {
		return contentQueue;
	}
	public void setContentQueue(IQueue<String> contentQueue) {
		this.contentQueue = contentQueue;
	}
	public IDownLoader getDownloader() {
		return downloader;
	}
	public void setDownloader(IDownLoader downloader) {
		this.downloader = downloader;
	}
	
}
