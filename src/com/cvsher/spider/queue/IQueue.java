package com.cvsher.spider.queue;

public interface IQueue<T> {

	/**
	 * 入队
	* @author oujiah
	* @date 2016年2月17日上午8:44:27
	* @param t
	* @return 返回是否入队成功
	 */
	boolean enqueue(T t);
	
	/**
	 * 出队
	* @author oujiah
	* @date 2016年2月17日上午8:45:24
	* @return 返回出队元素
	 */
	T dequeue();
	
}
