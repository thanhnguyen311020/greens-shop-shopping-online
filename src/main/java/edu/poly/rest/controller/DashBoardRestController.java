package edu.poly.rest.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import edu.poly.report.AmountReport;
import edu.poly.report.CategoryReport;
import edu.poly.report.ReportOderDate;
import edu.poly.report.ReportProduct;
import edu.poly.service.AccountService;
import edu.poly.service.OrderDetailService;
import edu.poly.service.OrderService;

@CrossOrigin("*")
@RestController
@RequestMapping("greens/rest/dashboard")
public class DashBoardRestController {

	
	@Autowired
	AccountService accountService;
	@Autowired
	OrderService orderService;
	@Autowired
	OrderDetailService orderDetailService;
	
	@GetMapping("countAccount")
	public Integer getAccount() {
		return accountService.findAll().size();
	}
	
	@GetMapping("amountOfMonth")
	public Double getAmountOfMonth() {
		return orderService.sumAmount();
	}
	
	@GetMapping("topProduct")
	public List<ReportProduct> topProduct(){
		return orderDetailService.findAllByTop();
	}
	
	@RequestMapping(value="findAmountMonth",method= RequestMethod.GET, produces = {MimeTypeUtils.APPLICATION_JSON_VALUE})
	public ResponseEntity<Iterable<AmountReport>> findAll(){
		try {
			return new ResponseEntity<Iterable<AmountReport>>(orderService.amountMonth(),HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Iterable<AmountReport>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("{start}/{end}")
	public List<ReportOderDate> getDetailBetweenDate(@PathVariable("start")String start, @PathVariable("end")String end) throws ParseException{
		Date st = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(start+" 00:00:00");
		Date en = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(end+" 23:00:00");
		System.out.println("Start" + st);
		return orderDetailService.findOrderDetailBetweenDate(st, en);
	}
	
	@GetMapping("sumAmount/{start}/{end}")
	public Double getSumAmountOrderBetweenDate(@PathVariable("start")String start,@PathVariable("end")String end) throws ParseException {
		Date st = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(start+" 00:00:00");
		Date en = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(end+" 23:00:00");
		return orderService.sumAmountOrderBetweenDate(st, en);
	}
	
	
	
	
	
	@RequestMapping(value="revenueByCategory",method= RequestMethod.GET, produces = {MimeTypeUtils.APPLICATION_JSON_VALUE})
	public ResponseEntity<Iterable<CategoryReport>> revenueByCategory(){
		try {
			
			return new ResponseEntity<Iterable<CategoryReport>>(orderDetailService.revenueByCategory(),HttpStatus.OK);
			//revenueByCategory() => báo cáo doanh thu lượt bán theo Loại
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Iterable<CategoryReport>>(HttpStatus.BAD_REQUEST);
		}
	}
}
