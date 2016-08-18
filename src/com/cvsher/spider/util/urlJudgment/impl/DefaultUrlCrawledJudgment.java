package com.cvsher.spider.util.urlJudgment.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.cvsher.spider.util.MessageDigestUtil;
import com.cvsher.spider.util.urlJudgment.IUrlCrawledJudgment;

/**
 * 默认的判断器：
 * 		使用MD5算法对url进行压缩，然后判断压缩后的MD5是否存储在redis中，
 * 	若在redis中有存储则url已经爬取过，否则将MD5结果存储到redis中
* @author oujiah
* @date 2016年2月19日上午10:51:16
*
 */
@Component("defaultUrlCrawledJudgment")
public class DefaultUrlCrawledJudgment implements IUrlCrawledJudgment{

	private static final Logger logger = LoggerFactory.getLogger(DefaultUrlCrawledJudgment.class);
	private static final String CRAWLED_URL_SET_KEY = "spider:crawledUrl:set";
	@Autowired
	private JedisPool jedisPool;
	
	@Override
	public synchronized boolean isUrlCrawled(String url) {
		try {
			Jedis jedis = jedisPool.getResource();
			boolean tag = jedis.sismember(CRAWLED_URL_SET_KEY, MessageDigestUtil.md5(url));
			jedis.close();
			return tag;
		} catch (Exception e) {
			logger.warn("判断url是否已爬取出错。");
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public synchronized void setUrlCrawled(String url) {
		try {
			Jedis jedis = jedisPool.getResource();
			jedis.sadd(CRAWLED_URL_SET_KEY, MessageDigestUtil.md5(url));
			jedis.close();
		} catch (Exception e) {
			logger.warn("设置已爬取url出错。");
			e.printStackTrace();
		}
	}

	public JedisPool getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

	public static void main(String[] args){
		DefaultUrlCrawledJudgment j = new DefaultUrlCrawledJudgment();
		j.isUrlCrawled("www.baidu.com");
	}
}
