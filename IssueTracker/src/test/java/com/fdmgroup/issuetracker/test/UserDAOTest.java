package com.fdmgroup.issuetracker.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fdmgroup.issuetracker.model.impl.Department;
import com.fdmgroup.issuetracker.model.impl.Role;
import com.fdmgroup.issuetracker.model.impl.User;
import com.fdmgroup.issuetracker.model.impl.UserDAO;

public class UserDAOTest {

	User user;
	UserDAO dao;
	Role role;
	Department department;
	ApplicationContext ctx;
	
	@Before
	public void before(){
		department = new Department();
		department.setDepartmentName("IT");
		role = new Role();
		role.setAdmin();
		user = new User(department, "sal","78y","salhenson@gmail.com", role);
		ctx = new ClassPathXmlApplicationContext("context.xml");
		dao = (UserDAO) ctx.getBean("UserDAO");
	}
	@Test
	public void test_addUser_UserDAO() {
		String expectedUserName = "sal";
		dao.addUser(user);
		User u = dao.getUser("sal");
		assertEquals(expectedUserName,u.getUsername());
	}
	@Test
	public void test_addDuplicateUser_UserDAO(){
		boolean expectedValue = false;
		boolean addDupUser = dao.addUser(user);
		assertEquals(expectedValue, addDupUser);
	}
	@Test(expected = NullPointerException.class)
	public void test_addUserNullParameter_UserDAO(){
		dao.addUser(null);
	}
	@Test
	public void test_getExistingUser_UserDAO(){
		String expectedUserName = "sal";
		User u = dao.getUser("sal");
		assertEquals(expectedUserName, u.getUsername());
	}
	@Test
	public void test_GetUserNameNotExist_UserDAO(){
		Object expectedValue = null;
		User u = dao.getUser("lsdfsdrfsd");
		assertEquals(expectedValue, u);
	}
	@Test
	public void test_GetUserNullParameter_UserDAO(){
		Object expectedValue = null;
		User u = dao.getUser(null);
		assertEquals(expectedValue, u);
	}
	@Test
	public void test_UpdateExistingUser_UserDAO(){
		String expectedValue = "78tyk";
		User us = dao.getUser("sal");
		us.setPassword(expectedValue);
		dao.updateUser(us);
		assertEquals(expectedValue, us.getPassword());
	}
	@Test(expected = NullPointerException.class)
	public void test_UpdateNotExistingUser_UserDAO(){
		Object expectedValue = null;
		User us = dao.getUser("lsdfsdf");
		dao.updateUser(us);
	}
	@Test
	public void test_ListUsers_UsersDAO(){
		List<User> l = dao.listUsers();
		assertNotNull(l);
	}
	@Test
	public void test_ListDeptAdmin_UsersDAO(){
		List<User> l = dao.listDeptAdmin();
		assertNotNull(l.size());
	}
	@Test
	public void test_GetDepartmentExists_UsersDAO(){
		String expectedUserName = "IT";
		Department d = dao.getDepartment("IT");
		assertEquals(expectedUserName, d.getDepartmentName());
	}
	@Test
	public void test_GetDepartmentNotExists_UsersDAO(){
		Object expectedValue = null;
		Department d = dao.getDepartment("lsls");
		assertEquals(expectedValue,d);
	}
	@Test
	public void test_GetRoleExists_UsersDAO(){
		String expectedRoleName = "admin";
		Role r = dao.getRole("admin");
		assertEquals(expectedRoleName, r.getRoleName());
	}
	@Test
	public void test_GetRoleNotExists_UsersDAO(){
		Object expectedValue=null;
		Role r = dao.getRole("lsdrfdsf");
		assertEquals(expectedValue, r);
	}
	@Test
	public void test_AddRole_UsersDAO(){
		String expectedRoleName="user";
		Role roleToAdd = new Role();
		roleToAdd.setRoleName("user");
		dao.addRole(roleToAdd);
		Role gRole = dao.getRole("user");
		assertEquals(expectedRoleName, gRole.getRoleName());
	}

}
