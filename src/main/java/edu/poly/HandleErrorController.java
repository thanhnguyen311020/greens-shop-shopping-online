package edu.poly;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HandleErrorController implements ErrorController {


    @GetMapping("/error")
    public String handleError(HttpServletRequest request) {
        String errorPage = "error"; // default
         
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
         
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
             
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                // handle HTTP 404 Not Found error
                errorPage = "site/error/404";
                 
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                // handle HTTP 500 Internal Server error
                errorPage = "site/error/500";
                 
            }
        }
         
        return errorPage;
    }
     
    @Override
    public String getErrorPath() {
        return "/error";
    }
//	@Override
//	public String getErrorPath() {
//		// TODO Auto-generated method stub
//		return "/greens/error";
//	}

//	@Override
//	public String getErrorPath() {
//		return "/error";
//	}
}
