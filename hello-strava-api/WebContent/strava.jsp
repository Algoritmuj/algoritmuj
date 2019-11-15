<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<title>Strava API</title>
	</head>
	<body>
		<c:if test="${not empty activity}">
			Name: ${activity.name}<br />
			Distance: ${activity.distance}
		</c:if>
		<c:if test="${not empty activities}">
			<ul>
				<c:forEach var="activity" items="${activities}">
					<li><a href="?id=${activity.id}">${activity.name}</a></li>
				</c:forEach>
			</ul>
		</c:if>
	</body>
</html>