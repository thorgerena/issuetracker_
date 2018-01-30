package com.fdmgroup.issuetracker.model.impl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Issue_Updates")
public class IssueUpdate {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="Issue_Update_Id")
	private int issueUpdateId;
	
	@ManyToOne
	@JoinColumn(name="issue_id")
	private Issue issue;
	
	@ManyToOne
	@JoinColumn(name = "submitted_by")
	private User submittedBy;

	@Column(name="update_date")
	private Date updateDate;
	
	@Column(name="Update_Comment")
	private String updateComment;

	public String getUpdateComment() {
		return updateComment;
	}

	public void setUpdateComment(String updateComment) {
		this.updateComment = updateComment;
	}

	public int getIssueUpdateId() {
		return issueUpdateId;
	}

	public void setIssueUpdateId(int issueUpdateId) {
		this.issueUpdateId = issueUpdateId;
	}

	public Issue getIssue() {
		return issue;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}

	public User getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(User submittedBy) {
		this.submittedBy = submittedBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}


}
