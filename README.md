【这仅仅是我个人为学习基于redis实现生产者消费者队列而写的小程序】

启动方法：
	1、启动redis服务
	2、修改config/applicationContext.xml配置文件，的如下配置
```xml
<bean id="jedisPool" class="redis.clients.jedis.JedisPool" >
	<constructor-arg index="0" ref="jedisPoolConfig" />
	<constructor-arg index="1" value="192.168.171.131" />		<!-- redis服务所在的ip -->
	<constructor-arg index="2" value="6379" />	<!-- redis服务的端口 -->
</bean>
```	
	3、启动SpiderStarter即可。

爬虫以SpiderStarter的main方法开始启动，启动后SpiderStarter会启动DownloaderStarter和ParserStarter两个组件；DownloaderStart会根据配置启动下载线程进行网页的爬取下载，ParserStarter同样会根据配置启动网页解析线程，对已下载的网页进行解析处理，并产生新的待爬url，解析线程会调用IUrlCrawledJudgment接口判断url是否已经爬取过。

使用redis实现生产者-消费者队列，

IQueue为队列接口，RedisProducerConsumerQueue为基于redis的生产者-消费者队列的具体实现【主要是使用了lpush和brpop命令】

两个队列：
	1、URL队列：DownLoader从URL队列中消费URL，Parser向URL队列中生产URL。
	2、Content队列：Parser从Content队列中消费Content，并分析处理。DownLoader从URL中下载Content，生产Content。