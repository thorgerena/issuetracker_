<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<c:import url="/WEB-INF/views/head.jsp"></c:import>
<c:import url="/WEB-INF/views/navbar.jsp"></c:import>
<body>
	<c:choose>
		<c:when test="${ empty sessionScope.user}">
			<h1 class="noauthority">Restricted Access</h1>
			<p>You are either not logged in or you do not have permission to
				view this page</p>
		</c:when>
		<c:otherwise>
			<c:if test="${ sessionScope.user.role.roleName == 'user' }">
				<div class="login-button">
					<form method="POST" action="addIssue">
						<button type="submit" class="btn btn-success btn-pill">Add
							an issue</button>
					</form>
				</div>
			</c:if>
			<c:if test="${ requestScope.issueAdded }">
				<br>
				<h4>Successfully added your issue!</h4>
			</c:if>
			<c:if test="${ sessionScope.user.role.roleName eq 'admin' }">
				<div class="user-card assign-card">
					<h3>Assign Issues</h3>
					<form method="POST" action="assign">
						<div class="form-group row">
							<label class="col-sm-4 col-form-label">Issue ID:</label>
							<div class="col-sm-8">
								<select class="form-control" name="issueId" size="1" required>
									<c:forEach var='issue' items='${ requestScope.issues }'>
										<option value="${issue.issueId}">${issue.issueId}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group row">
							<label class="col-sm-4 col-form-label">Assign to:</label>
							<div class="col-sm-8">
								<select class="form-control" name="deptId" size="1" required>
									<c:forEach var='dept' items='${ requestScope.depts}'>
										<option value="${dept.departmentId}">${
									dept.departmentName }</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="login-button">
							<button type="submit" class="btn btn-primary btn-pill">Assign</button>
						</div>
					</form>
				</div>
			</c:if>

			<c:choose>
				<c:when test="${ empty requestScope.issues }">
					<p>No issues.</p>
				</c:when>
				<c:otherwise>
					<div class="scrollbar scrollbar-morpheus-den">
						<div class="force-overflow">

							<table id="issue-updates">
								<tr>
									<th>IssueID</th>
									<c:if test='${ user.role.roleName == "admin" }'>
										<th>Assigned To</th>
									</c:if>
									<th>Status</th>
								</tr>

								<c:forEach var='issue' items='${ requestScope.issues }'>

									<tr>
										<td id="issueLink"
											onclick="window.location='viewissue?issueId=${issue.issueId}';"><c:out
												value='${ issue.issueId}' /></td>
										<c:if test='${ user.role.roleName == "admin" }'>
											<td>
												<div class="input-group">
													<form method="POST" action="assign">
														<input type="hidden" name="issueId"
															value='${ issue.issueId}' /> <select name="deptId"
															size="1" required>
															<option value=''>--</option>
															<c:forEach var='dept' items='${ requestScope.depts}'>
																<option value="${dept.departmentId}"
																	<c:if test = '${ issue.assignedTo == dept.departmentId}'>selected</c:if>>${
									dept.departmentName }
																</option>
															</c:forEach>
														</select> <span class="input-group-addon"> 
														<c:choose>
														<c:when test="${ not empty issue.priority }">
														<c:if test="${ issue.priority eq 1 }">
																<button type="submit" class="btn btn-info btn-pill">Assign</button>
															</c:if> 
															<c:if test="${ issue.priority eq 2 }">
																<button type="submit" class="btn btn-warning btn-pill">Assign</button>
															</c:if> 
															<c:if test="${ issue.priority eq 3 }">
																<button type="submit" class="btn btn-danger btn-pill">Assign</button>
															</c:if>
															</c:when>
															<c:otherwise>
															<button type="submit" class="btn btn-primary btn-pill">Assign</button>
															</c:otherwise>
															</c:choose>
														</span>
													</form>
												</div>
											</td>
										</c:if>
										<td><c:out value='${ issue.status}' /></td>
									</tr>

								</c:forEach>

							</table>
						</div>
					</div>
					<c:if test="${requestScope.notfound}">
						<label> Issue not found!</label>
					</c:if>

				</c:otherwise>
			</c:choose>
		</c:otherwise>
	</c:choose>

</body>
<c:import url="/WEB-INF/views/footer.jsp"></c:import>
</html>


