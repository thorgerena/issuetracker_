package com.fdmgroup.issuetracker.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fdmgroup.issuetracker.model.impl.User;
import com.fdmgroup.issuetracker.model.impl.UserDAO;

/**
 * Handles any user functionalities
 */
@Controller
public class UsersController {
	
	private ApplicationContext ctx;
	private UserDAO userDAO;
	
	/**
	 * Method that returns list of users
	 * @param model - model contains users attributes
	 * @param req - allows us to access the application context
	 * @return - returns list of users
	 */
	@RequestMapping(value = "/listusers")
	public String listUsers(Model model, HttpServletRequest req){
		HttpSession session = req.getSession();
		ctx = (ApplicationContext) session.getServletContext().getAttribute("ctx");
		userDAO = (UserDAO) ctx.getBean("UserDAO");
		
		List<User> userList = userDAO.listUsers();
		
		model.addAttribute("users",userList );
		
		return "listusers";
	}
}
