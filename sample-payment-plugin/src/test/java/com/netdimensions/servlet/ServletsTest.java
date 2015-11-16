package com.netdimensions.servlet;

import org.junit.Assert;
import org.junit.Test;

public class ServletsTest {

	@Test
	public void testFixUrlFromPathInfo() {
		Assert.assertEquals("http://localhost:8080/ekp/", Servlets.fixUrlFromPathInfo("http:/localhost:8080/ekp/"));
	}

	@Test
	public void testFixUrlFromPathInfoHttps() {
		Assert.assertEquals("https://localhost:8080/ekp/", Servlets.fixUrlFromPathInfo("https:/localhost:8080/ekp/"));
	}

	@Test
	public void testFixUrlFromPathInfoNoFixRequired() {
		Assert.assertEquals("http://localhost:8080/ekp/", Servlets.fixUrlFromPathInfo("http://localhost:8080/ekp/"));
	}
}
