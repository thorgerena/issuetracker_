package com.fdmgroup.issuetracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for directing back to homepage
 *
 */
@Controller
public class HomeController {
	
	/**
	 * Goes to the Homepage
	 * @return the index.jsp page
	 */
	@RequestMapping(value="/")
	public String goHome(){
		return "index";
	}
	
}
