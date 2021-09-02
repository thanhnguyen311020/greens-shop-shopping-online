package edu.poly.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/greens/admin/dashboard")
public class ReportContoller {
	
	@RequestMapping()
	public String dashboard() { 
		return "admin/report/report";
	}
	
	@RequestMapping("/chart")
	public String showChart() {
		return "site/chartDemo/chart";
	}
}
