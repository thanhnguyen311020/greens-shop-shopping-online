package edu.poly.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import edu.poly.service.CategoryService;

@Component
public class GlobalInterceptor implements HandlerInterceptor{
	
	@Autowired
	CategoryService categoryService;

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// post handler thuc thien sau khi thuc hiencac phuong thuc CTrl
		request.setAttribute("categories", categoryService.findAll());
	}
	
	
}
