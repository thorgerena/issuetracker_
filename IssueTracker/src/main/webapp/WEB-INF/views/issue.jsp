<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<c:import url="/WEB-INF/views/head.jsp"></c:import>
<c:import url="/WEB-INF/views/navbar.jsp"></c:import>
<body>
	<c:choose>
		<c:when test="${ empty issue }">
			<p>No issue associated with that ID.
			<p>
		</c:when>
		<c:otherwise>
			<h1 id="issue">Issue</h1>
			<div class="row">
				<div class="issue col-md-4">
					<h3>Details</h3>
					<p>
						<b>Issue ID:</b>
						<c:out value='${ issue.issueId }' />
					</p>
					<p>
						<b>Title:</b>
						<c:out value='${ issue.title }' />
					</p>
					<p>
						<b>Description:</b>
						<c:out value='${ issue.userDescription }' />
					</p>
					<p>
						<b>Assigned to </b>
						<c:out value='${ requestScope.deptName }' />
					</p>
					<p>
						<b>Date Submitted:</b>
						<c:out value='${ issue.dateSubmitted }' />
					</p>
					<p>
						<b>Date Resolved:</b>
						<c:out value='${ issue.dateResolved }' />
					</p>
					<c:choose>
						<c:when test='${ user.role.roleName != "admin"}'>
							<c:if test='${ not empty issue.adminComment }'>
								<p>
									<b>Admin Comment:</b>
									<c:out value='${ issue.adminComment }' />
								</p>
							</c:if>
						</c:when>
						<c:otherwise>
							<b>Admin Comment:</b>
							<form method="POST" action="updateIssueComment">
								<input type="hidden" name="issueId" value="${ issue.issueId }" />
								<div class="form-group center">
									<textarea name="adminComment">${issue.adminComment}</textarea>
								</div>
								<div class="login-button">
									<button type="submit" class="btn btn-primary btn-pill">Comment</button>
								</div>
							</form>
						</c:otherwise>
					</c:choose>
					<c:if
						test="${ sessionScope.user.role.roleName eq 'department_admin' or sessionScope.user.role.roleName eq 'user' }">
						<c:if
							test="${ requestScope.issue.assignedTo == user.department.departmentId or requestScope.issue.submittedBy == sessionScope.user.userId }">
							<div class="issue-update-form">
								<form method="POST" action="addIssueUpdate">
									<input type="hidden" name="issueId" value="${ issue.issueId }" />
									<div class="form-group">
										<span class="input-group-addon"> <b> Update on
												Issue: </b>
										</span>
										<textarea class="form-control" name="issueComment"
											placeholder="Enter any updates on the issue" maxlength="300"
											required></textarea>
									</div>
									<div class="login-button">
										<button type="submit" class="btn btn-primary btn-pill">Submit
											Update</button>
									</div>
								</form>
							</div>
						</c:if>
					</c:if>
					<c:if
						test="${ requestScope.issue.assignedTo == user.department.departmentId && user.role.roleName == 'department_admin' }">
						<h4>Update Issue Status</h4>
						<div>
							<form method="POST" action="updateIssueStatus">
								<div class="form-group select" style="text-align: center;">
									<input type="hidden" name="issueId" value="${ issue.issueId }" />
									<select name="status" size="1" required>
										<option value=''>Please select a status</option>
										<option value="ASSIGNED">Assigned</option>
										<option value="IN_PROCESS">In Process</option>
										<option value="RESOLVED">Resolved</option>
									</select>
								</div>
								<div class="login-button">
									<button type="submit" class="btn btn-primary btn-pill">Update
										Status</button>
								</div>
							</form>
						</div>
						<div class="login-button">
							<form method="POST" action="rejectIssue">
								<div class="form-group">
									<textarea class="form-control" name="rejectIssueReason"
										placeholder="Enter reject issue reason" maxlength="300"
										required>Please re-assign issue because..</textarea>
								</div>
								<input type="hidden" name="issueId" value="${ issue.issueId }" />
								<button type="submit" class="btn btn-danger btn-pill">Reject
									issue</button>
							</form>
						</div>
					</c:if>
					<c:if
						test="${ issue.submittedBy == sessionScope.user.userId && issue.status == 'RESOLVED' }">
						<div class="login-button">
							<form method="POST" action="approveIssue">
								<input type="hidden" name="issueId" value="${ issue.issueId }" />
								<button type="submit" class="btn btn-success btn-pill">Approve
									issue</button>
							</form>
						</div>
					</c:if>
				</div>
				<c:if test="${ not empty requestScope.issue.issueUpdates }">
					<div class="issue-update-cards col-md-8">
						<h3>Issue updates</h3>
						<div class="scrollbar scrollbar-morpheus-den"
							id="issue-update-table">
							<div class="force-overflow">
								<table id="issue-updates">
									<tr>
										<th>User</th>
										<th>Update</th>
										<th>Date</th>
									</tr>

									<c:forEach var='issueUpdate'
										items='${ requestScope.issue.issueUpdates }'>

										<tr>
											<td><b>${ issueUpdate.submittedBy.username } </b></td>
											<td><c:out value='${ issueUpdate.updateComment}' /></td>
											<td><c:out value='${ issueUpdate.updateDate}' /></td>
										</tr>

									</c:forEach>

								</table>
							</div>
						</div>
					</div>
				</c:if>
			</div>
		</c:otherwise>
	</c:choose>
</body>
<c:import url="/WEB-INF/views/footer.jsp"></c:import>
</html>