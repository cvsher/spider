package com.cvsher.spider.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.cvsher.spider.downloader.DownloaderStarter;
import com.cvsher.spider.parser.ParserStarter;

@Component("spiderStarter")
public class SpiderStarter {
	
	private static final Logger logger = LoggerFactory.getLogger(SpiderStarter.class);
	
	@Autowired
	private DownloaderStarter downloadStarter;
	@Autowired
	private ParserStarter parserStarter;
	
	public static void main(String[] args){
		logger.info("启动爬虫。。。");
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		SpiderStarter starter = context.getBean(SpiderStarter.class);
		starter.getDownloadStarter().start();
		starter.getParserStarter().start();
	}

	public DownloaderStarter getDownloadStarter() {
		return downloadStarter;
	}

	public void setDownloadStarter(DownloaderStarter downloadStarter) {
		this.downloadStarter = downloadStarter;
	}

	public ParserStarter getParserStarter() {
		return parserStarter;
	}

	public void setParserStarter(ParserStarter parserStarter) {
		this.parserStarter = parserStarter;
	}
	
}
