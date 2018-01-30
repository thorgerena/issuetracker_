<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<c:import url="head.jsp"></c:import>
<c:import url="navbar.jsp"></c:import>
<body class="homepage">
	<h1>Welcome to Issue Tracker System!</h1>
	<div class="mainpic">
		<img src="https://i.imgur.com/RCr0CMy.png" />
	</div>
	<div class="login-div ">

		<c:choose>
			<c:when test="${ not empty sessionScope.user }">
				<h3>Welcome back, ${
				sessionScope.user.username }</h3>
				<h3>Access level: ${
				sessionScope.user.role.roleName }</h3>
			</c:when>
			<c:otherwise>
				<c:if test="${requestScope.notfound}">
					<p class="showerror">
						<strong>User not found</strong>
					</p>
				</c:if>
				<c:if test="${requestScope.invalidUser}">
					<p class="showerror">
						<strong>Invalid username</strong>
					</p>
				</c:if>
				<c:if test="${requestScope.notmatch}">
					<p class="showerror">
						<strong>Incorrect Password</strong>
					</p>
				</c:if>
				<h3>Login</h3>
				<form method="POST" action="LoginProc">
					<div class="form-group">
						<div class="input-group">
							<div class="input-group-prepend">
								<span class="input-group-text"> <i class="fa fa-user"></i></span>
							</div>
							<input class="form-control" name="username"
								placeholder="Username" type="text" required />
						</div>
					</div>
					<div class="form-group">
						<div class="input-group">
							<div class="input-group-prepend">
								<span class="input-group-text"><i class="fa fa-lock"></i></span>
							</div>
							<input class="form-control" name="password"
								placeholder="Password" type="password" required />
						</div>
					</div>
					<div class="login-button">
						<button type="submit" class="btn btn-primary btn-pill">Login</button>
					</div>
				</form>
			</c:otherwise>
		</c:choose>
	</div>


</body>
<c:import url="footer.jsp"></c:import>
</html>