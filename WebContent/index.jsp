<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"
%><%@ include file="header.jsp"%>
      <div class="row">
        <div class="col-xs-12">
        <c:if test="${not empty requestScope['javax.servlet.error.status_code']}">
	      	<h2>Error ${requestScope['javax.servlet.error.status_code']}:${requestScope['javax.servlet.error.message']}</h2>
      	</c:if>	
        <p><fmt:message key="application.description"/>.</p>
        </div>
      </div>
      <div class="row">
        <div class="col-xs-12">
          <h2><fmt:message key="login.invitation"/></h2>
          <p><fmt:message key="login.explanation"/>.</p>
          <%--p><fmt:message key="login.help"/></p --%>
        </div>
      </div>
<%@ include file="footer.jsp"%>
