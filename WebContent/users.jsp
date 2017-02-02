<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %><%@ include file="header.jsp"%>
      <div class="row">
<div class="table-responsive">
  <table class="table">
<c:forEach var="user" items="${users}">
<tr><td>${user.admin!=0?'Admin':''}</td><td>${user.name}</td><td>${user.email}</td><td>${user.language}</td><td>
	<c:if test="${isadmin or (currentUser.PK==user.PK)}">
       <a href="UserUpdate?id=${user.PK}&breadcrumb=${breadcrumb}"><button type="button" class="btn btn-default btn-sm">Mise à jour</button></a>
  </c:if></td>
  </tr>
</c:forEach><c:if test="${userIsAdmin}">
<tr><td></td><td colspan="4"><form action="UserUpdate" method="POST">
	<input type="hidden" name="breadcrumb" value="${breadcrumb}"/>
	<input type="text" name="useremail" value=""/>
	<input type="button" class="btn btn-default" value="Ajouter un utilisateur"/>
	<button type="submit" class="btn btn-default" name="action" value="create"><fmt:message key="action.create"/> <fmt:message key="obj.user"/></button>
</form></td></tr>
</c:if>
</table>
</div>
      </div>
<%@ include file="footer.jsp"%>
