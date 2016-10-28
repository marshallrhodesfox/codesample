<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Atm Types</title>
</head>

<body>
	<a href="<c:url value="/logout" />">Logout</a>
	<h3>  Select an ATM type </h3>
	<c:forEach items="${types}" var="type">
		<a href="<c:url value="/type/${type}" />">${type}</a>
		<br>
	</c:forEach>
	
	<h3> Select a city </h3>
	<c:forEach items="${cities}" var="city">
		<a href="<c:url value="/city/${city}" />">${city}</a>
		<br>
	</c:forEach>
	
</body>
</html>