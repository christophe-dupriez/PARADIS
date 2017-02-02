<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %><html><body>!;<%--Indicates data has been processed --%>
<h3>${error}</h3>
<c:forEach var="conf" items="${encodedConfirmations}">!=${conf}<%--Indicates a specific record is newly recorded --%>
</c:forEach></body></html>
