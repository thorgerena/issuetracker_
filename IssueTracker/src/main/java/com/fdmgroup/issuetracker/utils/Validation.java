package com.fdmgroup.issuetracker.utils;

import com.fdmgroup.issuetracker.model.impl.Issue;
import com.fdmgroup.issuetracker.model.impl.IssueDAO;
import com.fdmgroup.issuetracker.model.impl.User;
import com.fdmgroup.issuetracker.model.impl.UserDAO;

public final class Validation {

	private Validation(){}
	private static final String validUsernameRegex = "[A-Za-z0-9_]+";
	
	/**
	 * Login
	 * @param dao
	 * @param username
	 * @param password
	 * @return
	 */
	public static boolean compare(UserDAO dao, String username, String password){
		User user = dao.getUser(username);
		if(user == null || !checkUsername(username))
		{
			return false;
		}
		else if(user.getPassword().equals(password))
		{
			return true;
		}
		return false;
	}
	
	public static boolean checkUsername(String username){
		return username.matches(validUsernameRegex);
	}
	
	public static boolean compare(IssueDAO dao, int issueId){
		Issue issue = dao.getIssue(issueId);
		if(issue == null){
			return false;
		}
		else if(issue != null){
			return true;
		}
		
		return false;
	}
	
}
