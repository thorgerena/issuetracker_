package com.fdmgroup.issuetracker.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fdmgroup.issuetracker.model.impl.User;
import com.fdmgroup.issuetracker.model.impl.UserDAO;
import com.fdmgroup.issuetracker.utils.Validation;

@Controller
public class LoginController {

	private ApplicationContext ctx;
	private UserDAO userDAO;

	/**
	 * Deal with login requests Need to get role of every user
	 * 
	 * @param request is the servlet request from the webcontainer.
	 * @param model object to be populated with attributes to be used by frontend.
	 * @param username string containing the username
	 * @param password string that contains the password of the user
	 * @return path returns the index view page.
	 */
	@RequestMapping(value = "/LoginProc", method = RequestMethod.POST)
	public String login(HttpServletRequest request, Model model, 
			@RequestParam String username,
			@RequestParam String password) {
		HttpSession session = request.getSession();
		ctx = (ApplicationContext) session.getServletContext().getAttribute("ctx");
		userDAO = (UserDAO) ctx.getBean("UserDAO");
		String compare = username.toLowerCase();
		User user = userDAO.getUser(compare);
		if (!Validation.checkUsername(compare)) {
			model.addAttribute("invalidUser", true);
		} else if (user == null) {
			model.addAttribute("notfound", true);
		} else if (Validation.compare(userDAO, compare, password)) {
			request.getSession().setAttribute("user", user);
		} else {
			model.addAttribute("notmatch", true);
		}
		return "index";
	}
	/**
	 * Logs out a user
	 * @param request is the servlet request from the webcontainer.
	 * @return String returns the index view page.
	 */
	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request) {
		HttpSession userSesh = request.getSession();
		userSesh.removeAttribute("user");
		userSesh.invalidate();
		return "index";
	}

}
