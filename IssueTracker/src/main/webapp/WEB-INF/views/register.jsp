<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<c:import url="/WEB-INF/views/head.jsp"></c:import>
<c:import url="/WEB-INF/views/navbar.jsp"></c:import>
<body>
	<c:choose>
		<c:when
			test="${ empty sessionScope.user  || sessionScope.user.role.roleName != 'admin'}">
			<h1 class="noauthority">Restricted Access</h1>
			<p>You are either not logged in or you do not have permission to view this page</p>
		</c:when>
		<c:otherwise>

			<c:if test="${ requestScope.registered }">
				<h4>Successfully registered: ${ requestScope.user.username }</h4>
			</c:if>
			<c:if test="${ requestScope.invalidUsername }">
				<p>Username can only consist of characters from A-Za-z and
					numbers 0-9.</p>
			</c:if>
			<c:if test="${ requestScope.selectUserTypeDepartment }">
				<p>Please select a User Type & Department in Dropdowns</p>
			</c:if>

			<c:if test="${ requestScope.selectDepartment }">
				<p>Please select a Department</p>
			</c:if>

			<c:if test="${ requestScope.selectUserType }">
				<p>Please select a User Type</p>
			</c:if>

			<span id="error-messages">${errormessage}</span>
			<div class="login-div">
				<h3>Register</h3>
				<form method="POST" action="registerUser">
					<c:if test="${requestScope.userexist}">
						<br>
						<p>Sorry, but that username is not available.</p>
					</c:if>

					<c:if test="${requestScope.passwordNoMatch}">
						<br>
						<p>Passwords DO NOT Match!</p>
					</c:if>
					<c:if test="${requestScope.emptyField}">
						<br>
						<p>Please fill in all the fields</p>
					</c:if>
					
					<div class="form-group">
						<div class="input-group">
								<span class="input-group-text"><i class="fa fa-user fa-fw"></i></span>
							<input class="form-control" name="username"
								placeholder="Username" type="text" required>
						</div>
					</div>
					<div class="form-group">
						<div class="input-group">
								<span class="input-group-text"><i class="fa fa-lock fa-fw"></i></span>
							<input class="form-control" name="password"
								placeholder="Password" type="password" required />
						</div>
					</div>
					<div class="form-group">
						<div class="input-group">
								<span class="input-group-text"><i class="fa fa-lock fa-fw"></i></span>
							<input class="form-control" name="confirmedPassword"
								placeholder="Confirm password" type="password" required />
						</div>
					</div>
					<div class="form-group">
						<div class="input-group">

								<span class="input-group-text"><i class="fa fa-envelope fa-fw"></i></span>
							<input class="form-control" name="email" placeholder="Email"
								type="email" required />
						</div>
					</div>
					<div class="form-group select">
						<select class="form-control" name="userType" size="1" required>
							<option value=''>Please select a User Type</option>
							<option value="user">User</option>
							<option value="departmentAdmin">Department Admin</option>
							<option value="admin">General Admin</option>
						</select>
					</div>
					<div class="form-group select">
						<select class="form-control" name="department" size="1" required>
							<option value=''>Please select a Department</option>
							<option value="HR">Human Resources</option>
							<option value="IT">IT</option>
							<option value="Sales">Sales</option>
						</select>
					</div>
					<div class="login-button">
						<button type="submit" class="btn btn-primary btn-pill">Register</button>
					</div>
				</form>
			</div>
		</c:otherwise>
	</c:choose>
</body>
<c:import url="/WEB-INF/views/footer.jsp"></c:import>
</html>