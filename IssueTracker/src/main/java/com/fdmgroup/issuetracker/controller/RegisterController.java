package com.fdmgroup.issuetracker.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fdmgroup.issuetracker.model.impl.Department;
import com.fdmgroup.issuetracker.model.impl.Role;
import com.fdmgroup.issuetracker.model.impl.User;
import com.fdmgroup.issuetracker.model.impl.UserDAO;
import com.fdmgroup.issuetracker.utils.Validation;

/**
 * RegisterController for registering and related operations
 *
 */
@Controller
public class RegisterController {
	
	/**
	 * Method that directs to register page. 
	 * It checks first if the parameters are legal for each field.
	 * @return - return register page
	 */
	@RequestMapping(value="/register")
	public String goToRegister()	{
		return "register";
	}

	/**
	 * Method that register a user by adding them to the database. It 
	 * checks if fields are empty, if some fields already exists and if so then
	 * throws RegisterException
	 * 
	 * @param request allows us to set and retrieve attributes for the session
	 * @param model - contains users attribute
	 * @param username - registered user's username
	 * @param password - registered user's password
	 * @param email - registered user's email
	 * @param confirmedPassword - another field of registered user's password to match with the first password. To ensure there isn't a typo in the password
	 * @param userType - set if user is a user or has admin rights, if so if the admin has just a department or the whole system
	 * @param department - department with the system
	 * @return a string that indicate page based on success of register
	 */
	@RequestMapping(value = "/registerUser", method = RequestMethod.POST)
	public String register(HttpServletRequest request, Model model, 
			@RequestParam String username, @RequestParam String password, 
			@RequestParam String email, @RequestParam String confirmedPassword, 
			@RequestParam String userType, @RequestParam String department) {
		
		ApplicationContext ac = (ApplicationContext) request.getSession().getServletContext().getAttribute("ctx");
		
		UserDAO dao = (UserDAO) ac.getBean("UserDAO");

		if (username.trim().equals("")
				|| email.trim().equals("")
				|| password.trim().equals("")
				|| confirmedPassword.trim().equals(""))
		{
			model.addAttribute("emptyField", true);
			return "register";
		}
		
		if (!Validation.checkUsername(username)){
			model.addAttribute("invalidUsername", true);
			return "register";
		}
		
		if(userType.equals("") && department.equals("")){
			model.addAttribute("selectUserTypeDepartment",true);
			return("register");
		}
		
		if(department.equals("")){
			model.addAttribute("selectDepartment",true);
			return("register");
		}
		
		if(userType.equals("")){
			model.addAttribute("selectUserType",true);
			return("register");
		}
		
		User user = dao.getUser(username.toLowerCase());
		
		if (user != null) {
			model.addAttribute("userexist", true);
			return "register";
		} 
		
		if (!password.equals(confirmedPassword)) {
			model.addAttribute("passwordNoMatch", true);
			return "register";
		}
		
		User newUser = (User) ac.getBean("newUser");
		Role newRole = (Role) ac.getBean("newRole");
		Department newDept = (Department) ac.getBean("newDept");
		
		if (userType.equals("user")) {
			newRole.setUser();
		} else if (userType.equals("admin")) {
			newRole.setAdmin();
			
		} else if (userType.equals("departmentAdmin")) {
			newRole.setDeptAdmin();
		}
		
		newDept.setDepartmentName(department);
		
		newUser.setUsername(username.toLowerCase());
		newUser.setEmail(email);
		newUser.setPassword(password);
		newUser.setRole(newRole);
		newUser.setDepartment(newDept);
		
		dao.addUser(newUser);
		model.addAttribute("registered", true);
		model.addAttribute("user", newUser);
		return ("register");	
		
	}
}
