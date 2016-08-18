package com.cvsher.spider.util;

import org.junit.Assert;
import org.junit.Test;

public class MessageDigestUtilTest {

	@Test
	public void testMd5(){
		String source = "aaa";
		String target = "47bce5c74f589f4867dbd57e9ca9f808";
		Assert.assertEquals(MessageDigestUtil.md5(source), target);
	}
}
