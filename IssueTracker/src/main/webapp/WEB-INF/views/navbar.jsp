<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<header>
	<nav class="navbar navbar-expand-md navbar-dark bg-dark fixed-top">
		<a class="navbar-brand" href="./">Issue Tracker</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarsExampleDefault"
			aria-controls="navbarsExampleDefault" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<c:choose>
			<c:when test="${ not empty sessionScope.user }">
				<div class="collapse navbar-collapse" id="navbarsExampleDefault">
					<ul class="navbar-nav mr-auto">

						<li class="nav-item"><a class="nav-link" href="issues">Issues</a></li>
						<c:if test="${ sessionScope.user.role.roleName == 'admin' }">
							<li class="nav-item"><a class="nav-link" href="register">Register</a></li>
							<li class="nav-item"><a class="nav-link" href="listusers">Users</a></li>
							<li class="nav-item"><a class="nav-link" href="./">Admin:${sessionScope.user.username}</a></li>
						</c:if>
						<c:if
							test="${ sessionScope.user.role.roleName == 'department_admin' }">
							<li class="nav-item"><a class="nav-link" href="./">Department_Admin:${sessionScope.user.username}</a></li>
						</c:if>
						<c:if test="${ sessionScope.user.role.roleName == 'user' }">
							<li class="nav-item"><a class="nav-link" href="./">User:${sessionScope.user.username}</a></li>
						</c:if>
						<li class="nav-item"><a class="nav-link" href="logout">Logout</a></li>
					</ul>
					<form class="input-group" action="viewissue">
						<input type="number" class="form-control" name="issueId" style="margin-left: 30%;"> <span
							class="input-group-addon"><button type="submit"
								class="btn btn-default">Search</button></span>
					</form>
				</div>
			</c:when>
			<c:otherwise>

			</c:otherwise>
		</c:choose>



	</nav>
</header>