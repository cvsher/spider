package com.cvsher.spider.parser;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

/**
 * 解析淘宝网页，下载网页中的图片，返回网页中可用于继续爬取的url列表
* @author oujiah
* @date 2016年2月17日上午9:32:22
*
 */
@Component("taobaoImageParser")
public class TaoBaoImageParser implements IParser {

	@Override
	public List<String> parse(byte[] content) {
		List<String> urlList = new ArrayList<String>();
		String contentStr = "";
		try {
			contentStr = new String(content, "utf-8");
		} catch (UnsupportedEncodingException e) {
			contentStr = new String(content);
			e.printStackTrace();
		}
		Document doc = Jsoup.parse(contentStr);
		FileOutputStream out = null;
		try {
			out = new FileOutputStream("d:/spider/"+doc.title()+".html");
			out.write(content);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		//继续爬取的页面
		Elements urls = doc.select("a");
		for(Element elem : urls){
			urlList.add(elem.attr("href"));
		}
		return urlList;
	}
	/*public List<String> parse(byte[] content) {
		List<String> urlList = new ArrayList<String>();
		String contentStr = "";
		try {
			contentStr = new String(content, "utf-8");
		} catch (UnsupportedEncodingException e) {
			contentStr = new String(content);
			e.printStackTrace();
		}
		Document doc = Jsoup.parse(contentStr);
		
		Elements elems = doc.select(".J_TbcRate_ReviewItem tb-tbcr-review-item");
		String imgLink = "";
		for(Element elem : elems){
			
		}
		
		Elements urls = doc.select("a .side-item");
		String title ="";
		for(Element elem : urls){
			title = elem.select("img").attr("title");
			urlList.add(elem.attr("href"));
		}
		return urlList;
	}*/

}
