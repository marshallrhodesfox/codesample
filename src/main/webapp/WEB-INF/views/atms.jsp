<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Atms</title>
    <style>
      table {
        font-family: arial, sans-serif;
        border-collapse: collapse;
        width: 100%;
	  }

      td, th {
        border: 1px solid #dddddd;
        text-align: left;
        padding: 8px;
      }

      tr:nth-child(even) {
        background-color: #dddddd;
      }
    </style>
</head>
<body>
    <h1> <center> Atms </center> </h1>
    <a href="<c:url value="/logout" />">Logout</a>
    <table style="width:100%">
      <tr>
        <th>Type</th>
        <th>Distance</th>
        <th>Street</th>
        <th>House Number</th>
        <th>Postal Code</th>
        <th>City</th>
        <th>Lat</th>
        <th>Lng</th>
      </tr>
      <c:forEach items="${atms}" var="atm">
        <tr>
	      <td>${atm.type}</td>
		  <td>${atm.distance}</td>
		  <td>${atm.address.street}</td>
		  <td>${atm.address.housenumber}</td>
		  <td>${atm.address.postalcode}</td>
		  <td>${atm.address.city}</td>
		  <td>${atm.address.geoLocation.lat}</td>
		  <td>${atm.address.geoLocation.lng}</td>
        </tr>
	  </c:forEach>
	</table>
</body>
</html>