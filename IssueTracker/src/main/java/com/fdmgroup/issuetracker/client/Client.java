package com.fdmgroup.issuetracker.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.fdmgroup.issuetracker.model.impl.Department;
import com.fdmgroup.issuetracker.model.impl.Role;
import com.fdmgroup.issuetracker.model.impl.User;
import com.fdmgroup.issuetracker.model.impl.UserDAO;

public class Client {
	
	public static void main(String[] args) {
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");
		UserDAO userDAO = (UserDAO) ctx.getBean("UserDAO");
		User trainee = new User();
		Department java = new Department();
		java.setDepartmentName("IT");
		trainee.setDepartment(java);
		trainee.setEmail("haha@haha");
		trainee.setUsername("admin1");
		trainee.setPassword("adminpw");
		Role admin = new Role();
		admin.setAdmin();
		trainee.setRole(admin);
		userDAO.addUser(trainee);
		System.out.println("Done adding User");
		((ConfigurableApplicationContext)ctx).close();
	}

}