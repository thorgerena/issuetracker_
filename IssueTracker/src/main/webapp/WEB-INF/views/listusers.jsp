<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<c:import url="/WEB-INF/views/head.jsp"></c:import>
<c:import url="/WEB-INF/views/navbar.jsp"></c:import>
<body>
	<c:choose>
		<c:when test="${ sessionScope.user.role.roleName != 'admin' }">
			<h1 class="noauthority">Restricted Access</h1>
			<p>You are either not logged in or you do not have permission to
				view this page</p>
		</c:when>
		<c:otherwise>
			<div class="row">
				<div class="iter-cards">
					<h1>User List</h1>
					<table id="issue-updates" class="user-row">
						<tr>
							<th>User ID</th>
							<th>Username</th>
							<th>Department</th>
							<th>Email</th>
							<th>Role</th>
						</tr>

						<c:forEach var='user' items='${ requestScope.users }'>

						<c:if test="${ user.role.roleName == 'user' }"> 
							<tr onclick="window.location='viewUserIssues?userId=${ user.userId }';">
							</c:if>
							<c:if test="${ user.role.roleName == 'department_admin' }">
							<tr onclick="window.location='viewDeptIssues?deptId=${ user.department.departmentId }';">
							</c:if>
							<c:if test="${ user.role.roleName == 'admin' }">
							<tr onclick="window.location='issues';">
							</c:if>
								<td><c:out value='${ user.userId }' /></td>
								<td><c:out value='${ user.username }' /></td>
								<td><c:out value='${ user.department.departmentName }' /></td>
								<td><c:out value='${ user.email }' /></td>
								<c:choose>
								<c:when test='${ user.role.roleName == "department_admin" }'>
								<td><c:out value="dept admin" /></td>
								</c:when>
								<c:otherwise>
								<td><c:out value='${ user.role.roleName }' /></td>
								</c:otherwise>
								</c:choose>
							</tr>

						</c:forEach>

					</table>
				</div>
			</div>
		</c:otherwise>
	</c:choose>
</body>
<c:import url="/WEB-INF/views/footer.jsp"></c:import>
</html>