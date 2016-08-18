package com.cvsher.spider.parser;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cvsher.spider.queue.IQueue;
import com.cvsher.spider.util.urlJudgment.IUrlCrawledJudgment;

public class ParserStarter {
	
	private static final Logger logger = LoggerFactory.getLogger(ParserStarter.class);

	@Autowired(required=false)
	private int threadCount = 5;
	@Autowired
	private IQueue<String> urlQueue;
	@Autowired
	private IQueue<String> contentQueue;
	@Autowired
	private IParser parser;
	@Autowired
	private IUrlCrawledJudgment urlCrawledJudgment;
	
	public ParserStarter(){
	}
	public ParserStarter(int threadCount){
		if(threadCount <= 0){
			this.threadCount = 5;
		}else{
			this.threadCount = threadCount;
		}
	}
	
	public void start(){
		ExecutorService pool = Executors.newFixedThreadPool(threadCount);
		for(int i=0; i<threadCount; i++){
			pool.execute(new Runnable() {
				
				@Override
				public void run() {
					while(true){
						logger.info("开始分析网页");
						String content = contentQueue.dequeue();
						logger.info("网页内容：【{}】", content);
						List<String> urls = parser.parse(content.getBytes());
						if(urls != null){
							for(String url : urls){
								if(!urlCrawledJudgment.isUrlCrawled(url)){
									urlQueue.enqueue(url);
									urlCrawledJudgment.setUrlCrawled(url);
								}
								
							}
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
	public IUrlCrawledJudgment getUrlCrawledJudgment() {
		return urlCrawledJudgment;
	}
	public void setUrlCrawledJudgment(IUrlCrawledJudgment urlCrawledJudgment) {
		this.urlCrawledJudgment = urlCrawledJudgment;
	}
	
}
