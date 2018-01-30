package com.fdmgroup.issuetracker.model.impl;

import static org.junit.Assert.*;

import javax.persistence.Column;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

import java.util.List;

public class IssueDAOTest {
	private Issue mockIssue;
	private int issueId;
	private String title;
	private String userDescription;
	private String adminComment;
	private int assignedTo;
	private int submittedBy;
	private IssueDAO classUnderTest;
	
	@Before
	public void setUp() throws Exception {
		mockIssue = mock(Issue.class);
		issueId = 10;
		title = "mockIssue";
		userDescription = "This is a mock issue";
		adminComment = "This is where admins comment";
		assignedTo = 10;
		submittedBy = 10;
		classUnderTest = new IssueDAO();
		when(mockIssue.getIssueId()).thenReturn(issueId);
		when(mockIssue.getTitle()).thenReturn(title);
		when(mockIssue.getUserDescription()).thenReturn(userDescription);
		when(mockIssue.getAdminComment()).thenReturn(adminComment);
		when(mockIssue.getAssignedTo()).thenReturn(assignedTo);
		when(mockIssue.getSubmittedBy()).thenReturn(submittedBy);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testIfIssueDAOaddedIssue() {
	Issue expectedIssue;
		
	classUnderTest.addIssue(mockIssue);
	expectedIssue = classUnderTest.getIssue(issueId);
	
	int expectedIssueId = expectedIssue.getIssueId();
	assertEquals(expectedIssueId, issueId);
	}
	
	
	@Test
	public void testIfIssueDAOupdatesIssue(){
		Issue expectedIssue = new Issue();
		Issue issueUnderTest;
		String newTitle = "issueUpdate";
		
		expectedIssue.setIssueId(issueId);
		expectedIssue.setTitle(title);
		
		classUnderTest.addIssue(expectedIssue);

		expectedIssue.setTitle(newTitle);
		
		classUnderTest.updateIssue(expectedIssue);
		
		issueUnderTest = classUnderTest.getIssue(issueId);
		
		String titleUnderTest = issueUnderTest.getTitle();
		assertEquals(titleUnderTest, newTitle);
	}


	
	
		

}
