package edu.poly;

import javax.servlet.http.HttpServletRequest;

public class Utility {
	public static String getGreensURL(HttpServletRequest request) {
		String siteUrl = request.getRequestURL().toString();
		return siteUrl.replace(request.getServletPath(), "");
	}
}
