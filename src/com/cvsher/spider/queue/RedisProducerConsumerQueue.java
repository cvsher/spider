package com.cvsher.spider.queue;

import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 基于redis实现的生产者消费者队列
* @author oujiah
* @date 2016年8月18日上午9:15:47
*
 */
public class RedisProducerConsumerQueue implements IQueue<String>{

	@Autowired
	private JedisPool jedisPool;
	private String queueName;
	
	@Override
	public synchronized boolean enqueue(String t) {
		Jedis jedis = jedisPool.getResource();
		long result = jedis.lpush(queueName, t.toString());
		jedis.close();
		return 0 == result ? false:true;
	}

	@Override
	public synchronized String dequeue() {
		Jedis jedis = jedisPool.getResource();
		String result = jedis.brpop(0, queueName).get(1);
		jedis.close();
		return result;
	}
	
	public JedisPool getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	/*public static void main(String[] args){
		//1、创建JedisPoolConfig
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(4096);
		jedisPoolConfig.setMaxIdle(200);
		jedisPoolConfig.setMaxWaitMillis(5000);
		jedisPoolConfig.setTestOnBorrow(true);
		jedisPoolConfig.setTestOnReturn(true);
		
		//2、创建JedisPool
		JedisPool jedisPool = new JedisPool(jedisPoolConfig, "192.168.171.131", 6379);
		
		final RedisProducerConsumerQueue queue = new RedisProducerConsumerQueue();
		queue.setJedisPool(jedisPool);
		queue.setQueueName("urlQueue");
		
		System.out.println("key == "+PREFIX+queue.queueName);
		
		//producer thread
		new Thread(){
			@Override
			public void run(){
				int i=0;
				while(true){
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					queue.enqueue((i++)+"");
				}
			}
		}.start();
		
		//consumer thread
		new Thread(){
			@Override
			public void run(){
				while(true){
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(queue.dequeue());
				}
			}
		}.start();
		new Thread(){
			@Override
			public void run(){
				while(true){
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(queue.dequeue());
				}
			}
		}.start();
		new Thread(){
			@Override
			public void run(){
				while(true){
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(queue.dequeue());
				}
			}
		}.start();
		new Thread(){
			@Override
			public void run(){
				while(true){
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(queue.dequeue());
				}
			}
		}.start();
	}*/

}
