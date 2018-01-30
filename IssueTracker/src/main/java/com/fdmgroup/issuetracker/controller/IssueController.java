package com.fdmgroup.issuetracker.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fdmgroup.issuetracker.model.impl.Department;
import com.fdmgroup.issuetracker.model.impl.Issue;
import com.fdmgroup.issuetracker.model.impl.IssueDAO;
import com.fdmgroup.issuetracker.model.impl.IssueUpdate;
import com.fdmgroup.issuetracker.model.impl.Role;
import com.fdmgroup.issuetracker.model.impl.Status;
import com.fdmgroup.issuetracker.model.impl.User;
import com.fdmgroup.issuetracker.model.impl.UserDAO;
import com.fdmgroup.issuetracker.utils.Validation;

@Controller
public class IssueController {

	private ApplicationContext ctx;
	private IssueDAO issueDAO;

	

	/**
	 * This method lists issues by users role 
	 * @param model contains users attribute for departments and issues
	 * @param req allows us to access the application context
	 * @return String issues
	 */
	@RequestMapping(value = "/issues")
	public String listIssues(Model model, HttpServletRequest req) {
		HttpSession session = req.getSession();
		ctx = (ApplicationContext) session.getServletContext().getAttribute("ctx");
		issueDAO = (IssueDAO) ctx.getBean("IssueDAO");
		User user = (User) session.getAttribute("user");
		if(user == null){
			return "index";
	
		} else {
			if (user.getRole().getRoleName().equals(Role.ADMIN)) {
				List<Issue> issues = issueDAO.listAll();
				List<Department> depts = issueDAO.listDepts();
				model.addAttribute("depts", depts);
				model.addAttribute("issues", issues);
			} else if (user.getRole().getRoleName().equals(Role.DEPT_ADMIN)) {
				List<Issue> issues = issueDAO.listByDept(user.getDepartment().getDepartmentId());
				model.addAttribute("issues", issues);
			} else {
				List<Issue> issues = issueDAO.listByUser(user.getUserId());
				model.addAttribute("issues", issues);
			}
		}
		return "issues";
	}
	/**
	 * This method proceeds to the addIssue page
	 * @return to addIssue.jsp
	 */
	@RequestMapping(value = "/addIssue")
	public String addIssue() {
		return "addIssue";
	}
	/**
	 * This adds an issue by a user
	 * @param model contains users attribute for issueAdded
	 * @param req allows us to access the application context
	 * @param title allows us to set a name to an issue
	 * @param userDescription allows us to set a Description to an issue
	 * @return to addIssue.jsp
	 */
	@RequestMapping(value = "/addIssueProc", method = RequestMethod.POST)
	public String addIssueMethod(Model model, HttpServletRequest req, @RequestParam(value = "title") String title,
			@RequestParam(value = "userDescription") String userDescription, @RequestParam int priority) {
		HttpSession session = req.getSession();
		ctx = (ApplicationContext) session.getServletContext().getAttribute("ctx");
		issueDAO = (IssueDAO) ctx.getBean("IssueDAO");
		User sessionUser = (User) session.getAttribute("user");
		Issue issue = (Issue) ctx.getBean("newIssue");
		issue.setTitle(title);
		issue.setUserDescription(userDescription);
		issue.setSubmittedBy(sessionUser.getUserId());
		issue.setPriority(priority);
		issue.setDateSubmitted(new Date());
		boolean addIssueBool = issueDAO.addIssue(issue);
		if (addIssueBool) {
			model.addAttribute("issueAdded", true);
			return listIssues(model, req);
		} else {
			model.addAttribute("addIssueFailed", true);
			return "addIssue";
		}
	}
	/**
	 * This method lists users in that department
	 * @param issueId allows for retrieval of issueId from issueDAO
	 * @param model contains users attribute for department and issue
	 * @param req allows us to access the application context
	 * @return to issue.jsp
	 */
	@RequestMapping(value = "/viewissue")
	public String listUsers(@RequestParam int issueId, Model model, HttpServletRequest req) {
		HttpSession session = req.getSession();
		ctx = (ApplicationContext) session.getServletContext().getAttribute("ctx");
		issueDAO = (IssueDAO) ctx.getBean("IssueDAO");
		Issue issue = issueDAO.getIssue(issueId);
		if (issue != null) {
			if (issueDAO.getDepartmentById(issue.getAssignedTo()) != null) {
			String deptName = issueDAO.getDepartmentById(issue.getAssignedTo()).getDepartmentName();
			model.addAttribute("deptName", deptName);
			} else {
				String noDept = "Not assigned";
				model.addAttribute("deptName", noDept);
			}
		}
		
		model.addAttribute("issue", issue);
		return "issue";
	}
	/**
	 * This method assigns an issue
	 * @param req allows us to access the application context
	 * @param model model contains users attribute for notFound
	 * @param issueId allows for retrieval of issueId from issueDAO
	 * @param deptId allows for Assigning of deptId
	 * @return listIssues(model, req);
	 * @see listIssues(Model model, HttpServletRequest req)
	 */
	@RequestMapping(value = "/assign", method = RequestMethod.POST)
	public String assignIssue(HttpServletRequest req, Model model, @RequestParam int issueId,
			@RequestParam int deptId) {

		HttpSession session = req.getSession();
		ctx = (ApplicationContext) session.getServletContext().getAttribute("ctx");
		issueDAO = (IssueDAO) ctx.getBean("IssueDAO");

		if (Validation.compare(issueDAO, issueId)) {
			Issue issue = issueDAO.getIssue(issueId);
			issue.setAssignedTo(deptId);
			issue.setStatus(Status.ASSIGNED);
			issueDAO.updateIssue(issue);

		} else {
			model.addAttribute("notfound", true);
			return listIssues(model, req);
		}
		return listIssues(model, req);
	}
	/**
	 * This lists issues by a user
	 * @param req allows us to access the application context
	 * @param model contains users attribute for departments and issues
	 * @param userId used to list issues by userId
	 * @return to issues.jsp
	 */
	@RequestMapping(value = "/viewUserIssues")
	public String viewUserIssues(HttpServletRequest req, Model model, @RequestParam int userId) {
		HttpSession session = req.getSession();
		ctx = (ApplicationContext) session.getServletContext().getAttribute("ctx");
		issueDAO = (IssueDAO) ctx.getBean("IssueDAO");
		List<Department> depts = issueDAO.listDepts();
		List<Issue> issues = issueDAO.listByUser(userId);
		model.addAttribute("depts", depts);
		model.addAttribute("issues", issues);
		return "issues";
	}
	/**
	 * This is to view a issue by department
	 * @param req allows us to access the application context
	 * @param model contains users attribute for departments and issues
	 * @param deptId allows for Assigning of deptId
	 * @return to issues.jsp
	 */
	@RequestMapping(value = "/viewDeptIssues") 
	public String viewAssignedIssues(HttpServletRequest req, Model model, @RequestParam int deptId) {
		HttpSession session = req.getSession();
		ctx = (ApplicationContext) session.getServletContext().getAttribute("ctx");
		issueDAO = (IssueDAO) ctx.getBean("IssueDAO");
		List<Department> depts = issueDAO.listDepts();
		List<Issue> issues = issueDAO.listByDept(deptId);
		model.addAttribute("depts", depts);
		model.addAttribute("issues", issues);
		return "issues";
	}
	/**
	 * This updates an issue
	 * @param issueId used to retrieve a specific issue
	 * @param issueComment used to set a comment in issueUpdate
	 * @param req allows us to access the application context
	 * @param model used as a param for return
	 * @return listUsers(issueId, model, req)
	 * @see listUsers(@RequestParam int issueId, Model model, HttpServletRequest req)
	 */
	@RequestMapping(value = "addIssueUpdate", method = RequestMethod.POST)
	public String addIssueUpdate(@RequestParam int issueId, @RequestParam String issueComment, HttpServletRequest req,
			Model model) {
		HttpSession session = req.getSession();
		ctx = (ApplicationContext) session.getServletContext().getAttribute("ctx");
		issueDAO = (IssueDAO) ctx.getBean("IssueDAO");
		Issue issue = issueDAO.getIssue(issueId);
		List<IssueUpdate> issueUpdates = issue.getIssueUpdates();
		IssueUpdate issueUpdate = (IssueUpdate) ctx.getBean("newIssueUpdate");
		issueUpdate.setIssue(issue);
		issueUpdate.setUpdateComment(issueComment);
		issueUpdate.setUpdateDate(new Date());
		issueUpdate.setSubmittedBy((User) session.getAttribute("user"));
		issueUpdates.add(issueUpdate);
		issueDAO.updateIssue(issue);
		return listUsers(issueId, model, req);
	}
	/**
	 * This approves an issue
	 * @param req allows us to access the application context
	 * @param model contains users attribute for notFound and is used as param in return
	 * @param issueId used to retrieve a specific issue
	 * @return listUsers(issueId, model, req)
	 * @see listUsers(@RequestParam int issueId, Model model, HttpServletRequest req)
	 */
	@RequestMapping(value = "approveIssue", method=RequestMethod.POST)
	public String approveIssueProc(HttpServletRequest req, Model model, 
			@RequestParam int issueId) {

		HttpSession session = req.getSession();
		ctx = (ApplicationContext) session.getServletContext().getAttribute("ctx");
		issueDAO = (IssueDAO) ctx.getBean("IssueDAO");

		if (Validation.compare(issueDAO, issueId)) {
			Issue issue = issueDAO.getIssue(issueId);
			issue.setStatus(Status.APPROVED);
			issue.setDateResolved(new Date());
			issueDAO.updateIssue(issue);
		} else {
			model.addAttribute("notfound", true);
			return listIssues(model, req);
		}
		return listIssues(model, req);
	}
	/**
	 * This rejects an issue
	 * @param req allows us to access the application context
	 * @param model contains users attribute for notFound and is used as param in return
	 * @param issueId used to retrieve a specific issue
	 * @return return listIssues(model, req)
	 * @see listIssues(Model model, HttpServletRequest req)
	 */
	@RequestMapping(value = "rejectIssue", method=RequestMethod.POST)
	public String rejectIssueProc(HttpServletRequest req, Model model, 
			@RequestParam int issueId, @RequestParam String rejectIssueReason) {

		HttpSession session = req.getSession();
		ctx = (ApplicationContext) session.getServletContext().getAttribute("ctx");
		issueDAO = (IssueDAO) ctx.getBean("IssueDAO");
		if (Validation.compare(issueDAO, issueId)) {
			Issue issue = issueDAO.getIssue(issueId);
			List<IssueUpdate> issueUpdates = issue.getIssueUpdates();
			IssueUpdate issueUpdate = (IssueUpdate) ctx.getBean("newIssueUpdate");
			issue.setStatus(Status.REJECTED);
			issueUpdate.setIssue(issue);
			issueUpdate.setUpdateComment(rejectIssueReason);
			issueUpdate.setUpdateDate(new Date());
			issueUpdate.setSubmittedBy((User) session.getAttribute("user"));
			issueUpdates.add(issueUpdate);
			issue.setAssignedTo(0);
			issueDAO.updateIssue(issue);
		} else {
			model.addAttribute("notfound", true);
			return listIssues(model, req);
		}
		return listIssues(model, req);
	}
	/**
	 * This updates an issue status
	 * @param req allows us to access the application context
	 * @param model contains users attribute for notFound and is used as param in return
	 * @param issueId used to retrieve a specific issue
	 * @param status used to set a specific status 
	 * @return listIssues(model, req);
	 * @see listIssues(Model model, HttpServletRequest req)
	 */
	@RequestMapping(value = "updateIssueStatus", method=RequestMethod.POST)
	public String updateIssueStatusProc(HttpServletRequest req, Model model, 
			@RequestParam int issueId, @RequestParam String status) {

		HttpSession session = req.getSession();
		ctx = (ApplicationContext) session.getServletContext().getAttribute("ctx");
		issueDAO = (IssueDAO) ctx.getBean("IssueDAO");

		if (Validation.compare(issueDAO, issueId)) {
			Issue issue = issueDAO.getIssue(issueId);
			issue.setStatus(Status.valueOf(status));
			issueDAO.updateIssue(issue);
		} else {
			model.addAttribute("notfound", true);
			return listIssues(model, req);
		}
		return listIssues(model, req);
	}
	/**
	 * 
	 * @param req allows us to access the application context
	 * @param model contains users attribute for notFound and is used as param in return
	 * @param issueId used to retrieve a specific issue
	 * @param adminComment used to set an adminComment to issue
	 * @return listIssues(model, req);
	 * @see listIssues(Model model, HttpServletRequest req)
	 */
	@RequestMapping(value = "updateIssueComment", method=RequestMethod.POST)
	public String updateIssueAdminComment(HttpServletRequest req, Model model, 
			@RequestParam int issueId, @RequestParam String adminComment) {
		HttpSession session = req.getSession();
		ctx = (ApplicationContext) session.getServletContext().getAttribute("ctx");
		issueDAO = (IssueDAO) ctx.getBean("IssueDAO");
		if (Validation.compare(issueDAO, issueId)) {
			Issue issue = issueDAO.getIssue(issueId);
	
			issue.setAdminComment(adminComment);
			issueDAO.updateIssue(issue);
		} else {
			model.addAttribute("notfound", true);
			return listIssues(model, req);
		}
		
		return listIssues(model, req);
	}
}
