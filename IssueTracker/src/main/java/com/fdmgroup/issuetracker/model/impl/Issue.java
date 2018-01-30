package com.fdmgroup.issuetracker.model.impl;


import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "Issue")
@NamedQueries({
//@NamedQuery(name = "Issue.findIssue", query = "Select i from Issue i where i.issueId = :issueId"),
@NamedQuery(name = "Issue.findAll", query = "Select i from Issue i order by i.priority DESC, i.dateSubmitted DESC"),
@NamedQuery(name = "Issue.listDepts", query = "Select i from Issue i where i.assignedTo = :assignedTo order by i.dateSubmitted DESC"),
@NamedQuery(name = "Issue.listUserIssues", query = "Select i from Issue i where i.submittedBy = :submittedBy order by i.dateSubmitted DESC")
})
public class Issue {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name ="issue_id")
	private int issueId;
	private String title;
	@Column(name ="user_description")
	private String userDescription;
	@Column(name = "admin_comment")
	private String adminComment;
	
	@Column(name = "assigned_to")
	private int assignedTo;

	@Column(name = "submitted_by")
	private int submittedBy;
	
	@Column(name = "Priority")
	private int priority;

	@Enumerated(EnumType.STRING)
	private Status status = Status.UNASSIGNED;
	
	@OneToMany(mappedBy="issue", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private List<IssueUpdate> issueUpdates;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "Date_Submitted")
	private Date dateSubmitted;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "Date_Resolved")
	private Date dateResolved;

	public int getIssueId() {
		return issueId;
	}

	public void setIssueId(int issueId) {
		this.issueId = issueId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUserDescription() {
		return userDescription;
	}

	public void setUserDescription(String userDescription) {
		this.userDescription = userDescription;
	}

	public String getAdminComment() {
		return adminComment;
	}

	public void setAdminComment(String adminComment) {
		this.adminComment = adminComment;
	}

	public int getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(int assignedTo) {
		this.assignedTo = assignedTo;
	}

	public int getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(int submittedBy) {
		this.submittedBy = submittedBy;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getDateSubmitted() {
		return dateSubmitted;
	}

	public void setDateSubmitted(Date dateSubmitted) {
		this.dateSubmitted = dateSubmitted; 
	}

	public Date getDateResolved() {
		return dateResolved;
	}

	public void setDateResolved(Date dateResolved) {
		this.dateResolved = dateResolved;
	}

	public List<IssueUpdate> getIssueUpdates() {
		return issueUpdates;
	}

	public void setIssueUpdates(List<IssueUpdate> issueUpdates) {
		this.issueUpdates = issueUpdates;
	}
	
	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	@Override
	public String toString() {
		return "Issue ID: " + this.issueId + 
				" Issue Status: " + this.status +
				" Issue title: " + this.title +
				" Issue User Description: " + this.userDescription;
	}
}
